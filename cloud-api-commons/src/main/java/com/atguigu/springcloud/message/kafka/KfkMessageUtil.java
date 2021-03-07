package com.atguigu.springcloud.message.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * kafka 消息操作工具类
 */
public class KfkMessageUtil {

    /**
     * 发送消息
     * @param topic 主题名称
     * @param msg 消息内容
     */
    public static void sendMessage(String topic, Object msg){
        KafkaProducer kafkaProducer = KfkProducerFactory.getKafkaProducer(topic);
        kafkaProducer.send(new ProducerRecord<String, Object>(topic, msg));
        KfkProducerFactory.returnProducerToPool(topic, kafkaProducer);
    }

    public static void main(String[] args) {
        int index  = 0;
        while(true){
            sendMessage("test", "{\"name\":\"shaocongwang\",\"age\":31,\"birthday\":\"1989-07-18\",\"university\":\"zhengzhou University " +
                    "international college\",\"primaryschool\":\"yanglou primary school\",\"hobby\":[\"reading\",\"sports\",\"music\"]," +
                    "\"work\":\"program engeneerring\",\"hometown\":\"zhumadian_henan_china\",\"mobilephone\":\"18600681256\"," +
                    "\"email\":\"shaocongwang@126.com\",\"weight\":\"62kg\",\"height\":\"170cm\",\"address\":\"bai miao xi jie, bei qing lu, bei qi" +
                    " jia zhen, chang ping qu, bei jing shi\",\"index\": \"" + (index ++) + "\"} " );
        }
    }
}
