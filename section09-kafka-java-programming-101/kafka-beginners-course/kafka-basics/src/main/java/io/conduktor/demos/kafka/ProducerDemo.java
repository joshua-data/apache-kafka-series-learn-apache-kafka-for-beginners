package io.conduktor.demos.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        log.info("Hello World!");

        // Create Producer Properties.
        Properties properties = new Properties();

        // Connect to Localhost.
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // Set Producer Properties.
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        // Create the Producer.
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // Create a Producer Record.
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>("demo_java", "Hello World!");

        // Send Data.
        producer.send(producerRecord);

        // Flush and Close the Producer.
        producer.flush();
        producer.close();

    }
}