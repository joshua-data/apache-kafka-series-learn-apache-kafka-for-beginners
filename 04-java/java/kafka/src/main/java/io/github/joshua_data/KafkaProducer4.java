package io.github.joshua_data;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/*
Kafka Producer 4
- Message with Key

(1) Run the command to create topic4.
kafka-topics --bootstrap-server localhost:9092 --topic topic4 --create --partitions 3

(2) Run KafkaProducer4.main.

(3) Run the command to check out if the message has been successfully sent.
kafka-console-consumer --bootstrap-server localhost:9092 --topic topic4 --from-beginning

(4) Run the command to delete topic4.
kafka-topics --bootstrap-server localhost:9092 --topic topic4 --delete
*/

public class KafkaProducer4 {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer4.class.getSimpleName());

    public static void main(String[] args) {

        log.info(">>>>>> Starting Kafka Producer...");

        // Create Kafka Producer Properties
        Properties props = new Properties();

        // Connect to Kafka Broker
        props.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // Set Properties
        props.setProperty("key.serializer", StringSerializer.class.getName());
        props.setProperty("value.serializer", StringSerializer.class.getName());

        // Create Kafka Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // 2 Batches
        for (int batch = 0; batch < 2; batch++) {

            // 10 Messages
            for (int i = 0; i < 10; i++) {

                String topic = "topic4";
                String key = "id_" + i;
                String value = "Joshua purchased the item " + i;

                // Create a Record
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

                // Send Data (Callback)
                producer.send(
                        record,
                        new Callback() {
                            @Override
                            public void onCompletion(RecordMetadata metadata, Exception e) {
                                if (e == null) {
                                    log.info(">>>>>> Key: " + key + " | Partition: " + metadata.partition());
                                } else {
                                    log.error(">>>>>> Error while Producing", e);
                                }
                            }
                        }
                );

            }

        }

        // Flush & Close Producer
        producer.flush();
        producer.close();

    }

}