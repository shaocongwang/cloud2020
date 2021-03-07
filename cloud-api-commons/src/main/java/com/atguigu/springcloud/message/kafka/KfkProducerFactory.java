package com.atguigu.springcloud.message.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * kafka 的生产者工厂，用于获取生产者连接实例
 */
public class KfkProducerFactory {
    /**
     * 提供私有构造，不对外提供获取当前工厂类的权限
     */
    private KfkProducerFactory(){}
    /**
     * 通用 kafka 生产者连接池，根据主题进行分类管理
     */
    private static Map<String, List<KafkaProducer>> generalKfkProducerPool;
    //创建当前类的实例，单利模式，供当前类内部使用
    private static final KfkProducerFactory KFK_PRODUCER_FACTORY = new KfkProducerFactory();
    // 最大空闲生产者数量
    private static final Integer MIN_IDEL_PRODUCER_NUM = 5;
    // 最大生产者数量
    private static final Integer MAX_PRODUCER_NUM = 10;
    // 统计各个 topic 生产者的当前连接数量
    private static Map<String, AtomicInteger> producerCounterMap;

    /**
     * 初始化基础信息
     */
    static {
        generalKfkProducerPool = new HashMap<>();
        producerCounterMap = new HashMap<>();
    }

    /**
     * 根据主题获取对应的生产者实例
     * @param topic
     * @return
     */
    public synchronized static KafkaProducer getKafkaProducer(String topic){
        KafkaProducer returnKafkaProducer = null;
        List<KafkaProducer> currentKfkProducerPool = generalKfkProducerPool.get(topic);
        //对当前主题的生产者连接池进行非空判断
        if(null == currentKfkProducerPool){
            currentKfkProducerPool = new ArrayList<>();
            generalKfkProducerPool.put(topic, currentKfkProducerPool);
        }
        //获取当前主题的生产者连接池计数器
        AtomicInteger currentProducerNum = producerCounterMap.get(topic);
        if(null == currentProducerNum){
            //初始化连接池计数器
            currentProducerNum = new AtomicInteger(0);
            producerCounterMap.put(topic, currentProducerNum);
        }
        //连接池控制
        if(currentKfkProducerPool.size() < 1){
            boolean continueCreate = true;
            while(continueCreate){
                //判断当前主题存活的生产者是否已经到达允许的最大值
                if(currentProducerNum.intValue() < MAX_PRODUCER_NUM){
                    //创建一个生产者
                    KafkaProducer<Object, Object> currentProducer = new KafkaProducer<>(KfkConsumerProducerConfig.getKfkProducerPro());
                    //将创建的生产者添加到当前主题的生产者连接池中
                    currentKfkProducerPool.add(currentProducer);
                    //记录当前主题存活的生产者数量
                    currentProducerNum.incrementAndGet();
                } else {
                    continueCreate = false;
                }
            }
        }

        if(currentKfkProducerPool.size() < 1){
            //如果连接池中已经没有生产者，则认为生产者数量已经到达连接的上限
            throw new RuntimeException("the procuder num of current topic [" + topic + "], reached the max limit [" + MAX_PRODUCER_NUM + "]");
        } else {
            //获取一个生产者，返回给调用者
            returnKafkaProducer = currentKfkProducerPool.remove(0);
        }
        return returnKafkaProducer;
    }

    /**
     * 归还生产者到连接池
     * @param topic
     * @param kafkaProducer
     */
    public synchronized static void returnProducerToPool(String topic, KafkaProducer kafkaProducer){
        List<KafkaProducer> currentKafkaProducersPool = generalKfkProducerPool.get(topic);
        //判断要归还的主题对应的连接池是否存在
        if(null == currentKafkaProducersPool){
            throw new RuntimeException("return kafkaProducer failed, can not find ProducerPool of topic [" + topic + "]");
        }
        //归还生产者到连接池
        currentKafkaProducersPool.add(kafkaProducer);
        //维护连接池
        if(currentKafkaProducersPool.size() > MIN_IDEL_PRODUCER_NUM){
            //如果空闲生产者数量大于指定值
            boolean continueRemoveProduderFromPoolFlag = true;
            while(continueRemoveProduderFromPoolFlag){
                if(currentKafkaProducersPool.size() > MIN_IDEL_PRODUCER_NUM){
                    currentKafkaProducersPool.remove(0).close();//从连接池中移除并关闭连接
                    producerCounterMap.get(topic).decrementAndGet();//当前主题的连接数量减一
                } else {
                    continueRemoveProduderFromPoolFlag = false;//维护完成
                }
            }
        }
    }
}
