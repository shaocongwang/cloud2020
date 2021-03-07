package com.atguigu.springcloud.message.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.time.Duration;
import java.util.ArrayList;
/**
 * kafka 的消费者工具类
 */
public class KfkConsumerUtil {
    private static final KafkaConsumer kafkaConsumer = new KafkaConsumer(KfkConsumerProducerConfig.getKfkConsumerPro());


    public static void main(String[] args) {
        ArrayList<String> subscribedTopics = new ArrayList<>();
        subscribedTopics.add("test");
        kafkaConsumer.subscribe(subscribedTopics);
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records){
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }
    }

}
