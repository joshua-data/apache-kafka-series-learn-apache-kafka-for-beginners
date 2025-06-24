package io.github.joshua_data.wikimedia;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikimediaChangeHandler implements EventHandler {

    private final Logger log = LoggerFactory.getLogger(WikimediaChangeHandler.class.getSimpleName());

    KafkaProducer<String, String> producer;
    String topic;

    public WikimediaChangeHandler(KafkaProducer<String, String> producer, String topic) {
        this.producer = producer;
        this.topic = topic;
    }

    @Override
    public void onOpen() {}

    @Override
    public void onClosed() {
        producer.close();
    }

    @Override
    public void onComment(String comment) {}

    @Override
    public void onMessage(String event, MessageEvent message_event) {
        log.info(">>>>>> " + message_event.getData());
        // asynchronous
        producer.send(new ProducerRecord<>(topic, message_event.getData()));
    }

    @Override
    public void onError(Throwable t) {
        log.error(">>>>>> Error in Stream Reading", t);
    }

}
