package io.conduktor.demos.kafka;

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

;

public class ConsumerDemoWithShutdown {

    private static final Logger log = LoggerFactory.getLogger(ConsumerDemoWithShutdown.class.getSimpleName());

    public static void main(String[] args) {
        log.info("Hello World!");
        String groupId = "joshua-app";
        String topic = "demo_java";

        // Create Consumer Properties.
        Properties properties = new Properties();

        // Connect to Localhost.
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // Set Consumer Properties.
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", groupId);
        properties.setProperty("auto.offset.reset", "earliest");

        // Create the Consumer.
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // Get a Reference to the Main Thread.
        final Thread mainThread = Thread.currentThread();

        // Adding the Shutdown Hook.
        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {
                log.info("Detected a shutdown, let's exit by calling consumer.wakeup()...");
                consumer.wakeup();
                // Join the Main Thread to Allow the Execution of the Code in the Main Thread.
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            // Subscribe to a Topic.
            consumer.subscribe(Arrays.asList(topic));
            // Poll for Data.
            while (true) {
                log.info("Polling...");
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("Key: " + record.key() + ", Value: " + record.value());
                    log.info("Partition: " + record.partition() + ", Offset: " + record.offset());
                }
            }
        } catch (WakeupException e) {
            log.info("Consumer is starting to shuw down...");
        } catch (Exception e) {
            log.error("Unexpected exception in the consumer", e);
        } finally {
            consumer.close(); // Close the Consumer. This will also commit offsets.
            log.info("The Consumer is Now Gracefully Shutdown");
        }

    }

}