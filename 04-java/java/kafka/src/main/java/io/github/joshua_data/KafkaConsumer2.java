package io.github.joshua_data;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/*
Kafka Consumer 2
- Graceful Shutdown

(1) Run the command to create topic2.
kafka-topics --bootstrap-server localhost:9092 --topic topic2 --create --partitions 3

(2) Run KafkaProducer2.main to produce messages.

(3) Run KafkaConsumer2.main to consume messages.

(4) Run the command to delete topic2.
kafka-topics --bootstrap-server localhost:9092 --topic topic2 --delete
*/

public class KafkaConsumer2 {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer2.class.getSimpleName());

    public static void main(String[] args) {

        log.info(">>>>>> Starting Kafka Consumer...");

        String consumerGroupId = "consumer-group-1";
        String topic = "topic2";

        // Create Kafka Consumer Properties
        Properties props = new Properties();

        // Connect to Kafka Broker
        props.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // Set Properties
        props.setProperty("key.deserializer", StringDeserializer.class.getName());
        props.setProperty("value.deserializer", StringDeserializer.class.getName());
        props.setProperty("group.id", consumerGroupId);
        props.setProperty("auto.offset.reset", "earliest");

        // Create Kafka Consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Add Shutdown Hook
        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(
            new Thread() {

                public void run() {
                    log.info(">>>>>> Exiting by Calling comsumer.wakeup()...");
                    consumer.wakeup();
                    try {
                        mainThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        );

        try {

            // Subscribe to Topic
            consumer.subscribe(Arrays.asList(topic));

            // Poll for Data
            while (true) {

                log.info(">>>>>> Polling...");

                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record: records) {
                    log.info(">>>>>> Key: " + record.key());
                    log.info(">>>>>> Value: " + record.value());
                    log.info(">>>>>> Partition: " + record.partition());
                    log.info(">>>>>> Offset: " + record.offset());
                }

            }

        } catch (WakeupException e) {

            log.info(">>>>>> Starting to Shut Down...");

        } catch (Exception e) {

            log.error(">>>>>> Unexpected Exception in the Consumer", e);

        } finally {

            consumer.close(); // This will also commit offsets.
            log.info(">>>>>> Gracefully Showdown...");

        }

    }

}




