package io.github.joshua_data;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/*
Kafka Consumer 1
- Basic

(1) Run the command to create topic1.
kafka-topics --bootstrap-server localhost:9092 --topic topic1 --create --partitions 3

(2) Run KafkaProducer1.main to produce messages.

(3) Run KafkaConsumer1.main to consume messages.

(4) Run the command to delete topic1.
kafka-topics --bootstrap-server localhost:9092 --topic topic1 --delete
*/

public class KafkaConsumer1 {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer1.class.getSimpleName());

    public static void main(String[] args) {

        log.info(">>>>>> Starting Kafka Consumer...");

        String consumerGroupId = "consumer-group-1";
        String topic = "topic1";

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

    }

}




