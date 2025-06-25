package io.github.joshua_data;


import com.google.gson.JsonParser;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.opensearch.action.bulk.BulkRequest;
import org.opensearch.action.bulk.BulkResponse;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class OpenSearchConsumer {

    public static RestHighLevelClient createOpenSearchClient() {

        String url = "http://localhost:9200";

        RestHighLevelClient client;
        URI uri = URI.create(url);
        String user_info = uri.getUserInfo();

        if (user_info == null) {

            client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(uri.getHost(), uri.getPort(), "http"))
            );

        } else {

            String[] auth = user_info.split(":");

            CredentialsProvider credentials_provider = new BasicCredentialsProvider();
            credentials_provider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(auth[0], auth[1])
            );

            client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme()))
                    .setHttpClientConfigCallback(
                        httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(credentials_provider)
                            .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                    )
            );

        }

        return client;

    }

    private static KafkaConsumer<String, String> createKafkaConsumer() {

        String group_id = "opensearch-consumer-group-1";
        String bootstrap_servers = "127.0.0.1:9092";
        String string_deserializer = StringDeserializer.class.getName();
        String auto_offset_reset = "latest";
        String enable_auto_commit = "false";

        Properties props = new Properties();
        props.setProperty(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrap_servers);
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, string_deserializer);
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, string_deserializer);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, group_id);
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, auto_offset_reset);
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enable_auto_commit);

        return new KafkaConsumer<>(props);

    }

    private static String extractId(String json) {
        String id = JsonParser.parseString(json)
            .getAsJsonObject()
            .get("meta")
            .getAsJsonObject()
            .get("id")
            .getAsString();
        return id;
    }

    public static void main(String[] args) throws IOException {

        Logger log = LoggerFactory.getLogger(OpenSearchConsumer.class.getSimpleName());

        // Create OpenSearch Client
        RestHighLevelClient opensearch_client = createOpenSearchClient();

        // Create Kafka Client
        KafkaConsumer<String, String> consumer = createKafkaConsumer();

        // Get a Reference to the Main Thread
        final Thread main_thread = Thread.currentThread();

        // Add Shutdown Hook
        Runtime.getRuntime().addShutdownHook(new Thread() {

                public void run() {
                    log.info(">>>>>> Detected Shutdown. Exiting by Calling consumer.wakeup()...");
                    consumer.wakeup();
                    try {
                        main_thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        );

        // Start to Consume

        try (opensearch_client; consumer) {

            String topic = "wikimedia.recentchange";
            String index = "wikimedia.recentchange";

            // Create Index on OpenSearch
            boolean index_exists = opensearch_client.indices().exists(
                new GetIndexRequest(index),
                RequestOptions.DEFAULT
            );
            if (index_exists) {
                log.info(">>>>>> The Index Already Exists.");
            } else {
                CreateIndexRequest create_index_request = new CreateIndexRequest(index);
                opensearch_client.indices().create(create_index_request, RequestOptions.DEFAULT);
                log.info(">>>>>> The Index has been Created.");
            }

            // Subscribe the Consumer
            consumer.subscribe(Collections.singleton(topic));

            while (true) {

                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(3000));

                int records_cnt = records.count();
                log.info(">>>>>> Received " + records_cnt + " record(s).");

                BulkRequest bulk_request = new BulkRequest();

                for (ConsumerRecord<String, String> record : records) {

                    // Send Record into OpenSearch
                    try {
                        String id = extractId(record.value());
                        IndexRequest index_request = new IndexRequest(index)
                            .source(record.value(), XContentType.JSON)
                            .id(id);
                        bulk_request.add(index_request);
                    } catch (Exception e) {

                    }

                }

                if (bulk_request.numberOfActions() > 0) {

                    BulkResponse bulk_response = opensearch_client.bulk(bulk_request, RequestOptions.DEFAULT);
                    log.info(">>>>>> Inserted " + bulk_response.getItems().length + " record(s).");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    consumer.commitSync();
                    log.info(">>>>>> Offsets have been Committed.");

                }

            }

        } catch (WakeupException e) {
            log.info(">>>>>> Starting to Shut Down...");
        } catch (Exception e) {
            log.error(">>>>>> Unexpected Exception in the Consumer", e);
        } finally {
            consumer.close();
            opensearch_client.close();
            log.info(">>>>>> Consumer is not Gracefully Shut Down.");
        }

    }

}