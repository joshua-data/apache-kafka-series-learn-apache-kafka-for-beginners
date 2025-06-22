package io.github.joshua_data;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/*
Kafka Producer 1
- Basic

(1) Run the command to create topic1.
kafka-topics --bootstrap-server localhost:9092 --topic topic1 --create --partitions 3

(2) Run KafkaProducer1.main.

(3) Run the command to check out if the message has been successfully sent.
kafka-console-consumer --bootstrap-server localhost:9092 --topic topic1 --from-beginning

(4) Run the command to delete topic1.
kafka-topics --bootstrap-server localhost:9092 --topic topic1 --delete
*/

public class KafkaProducer1 {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer1.class.getSimpleName());

    public static void main(String[] args) {

        log.info(">>>>>> Starting Kafka Producer...");

        // Create Kafka Producer Properties
        Properties props = new Properties();

        // Connect to Kafka Broker
        props.setProperty("bootstrap.servers",  "127.0.0.1:9092");

        // Set Properties
        props.setProperty("key.serializer", StringSerializer.class.getName());
        props.setProperty("value.serializer", StringSerializer.class.getName());

        // Create Kafka Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Create a Record
        ProducerRecord<String, String> record = new ProducerRecord<>(
                "topic1",
                "Joshua clicked the cta button."
        );

        // Send Data
        producer.send(record);

        // Flush & Close Producer
        producer.flush();
        producer.close();

    }

}