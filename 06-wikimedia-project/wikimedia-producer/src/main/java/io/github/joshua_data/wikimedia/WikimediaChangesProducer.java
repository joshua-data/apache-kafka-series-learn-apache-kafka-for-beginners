package io.github.joshua_data.wikimedia;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class WikimediaChangesProducer {

    public static void main(String[] args) throws InterruptedException {

        // Variables
        String bootstrap_server = "127.0.0.1:9092";
        String string_serializer = StringSerializer.class.getName();
        String topic = "wikimedia.recentchange";
        String source_url = "https://stream.wikimedia.org/v2/stream/recentchange";


        // Properties
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_server);
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, string_serializer);
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, string_serializer);

        // Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Source Info
        EventHandler event_handler = new WikimediaChangeHandler(producer, topic);
        EventSource.Builder source_builder = new EventSource.Builder(event_handler, URI.create(source_url));
        EventSource event_source = source_builder.build();

        // Start
        event_source.start();
        TimeUnit.MINUTES.sleep(10);

    }

}
