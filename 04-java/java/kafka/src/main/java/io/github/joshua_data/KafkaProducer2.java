package io.github.joshua_data;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/*
Kafka Producer 2
- Callback: "작업이 끝났을 때 실행되는 함수"
- Kafka 작업은 메시지 전송이 비동기라서 언제 끝날지 모른다. 따라서 작업이 끝났을 때 실행되는 Callback 함수를 넣어서 성공/실패 여부를 확인하기 위해 사용한다.
    - 메시지 전송 성공 (브로커가 메시지를 잘 받음): e == null
    - 메시지 전송 실패 (브로커 연결 문제 등): e != null
- 파티션 할당 문제 발생: 자동으로 Sticky Partitioner를 사용함 (partitioner.class = null)
    - 30개 메시지가 각 파티션에 할당되는 것이 아니라, 한 번 실행할 때는 모든 메시지가 특정 파티션 1개에만 보내짐 (배치 처리)

(1) Run the command to create topic2.
kafka-topics --bootstrap-server localhost:9092 --topic topic2 --create --partitions 3

(2) Run KafkaProducer2.main.

(3) Run the command to check out if the message has been successfully sent.
kafka-console-consumer --bootstrap-server localhost:9092 --topic topic2 --from-beginning

(4) Run the command to delete topic2.
kafka-topics --bootstrap-server localhost:9092 --topic topic2 --delete
*/

public class KafkaProducer2 {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer2.class.getSimpleName());

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

        // 30 Messages
        for (int i=0; i<30; i++) {

            // Create a Record
            ProducerRecord<String, String> record = new ProducerRecord<>(
                    "topic2",
                    "Joshua viewed the page " + i
            );

            // Send Data (Callback)
            producer.send(
                    record,
                    new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata metadata, Exception e) {
                            if (e == null) {
                                log.info(
                                        ">>>>>> Topic: " + metadata.topic() + "\n" +
                                        ">>>>>> Partition: " + metadata.partition() + "\n" +
                                        ">>>>>> Offset: " + metadata.offset() + "\n" +
                                        ">>>>>> Timestamp: " + metadata.timestamp()
                                );
                            } else {
                                log.error(">>>>>> Error while Producing", e);
                            }
                        }
                    }
            );

        }

        // Flush & Close Producer
        producer.flush();
        producer.close();

    }

}