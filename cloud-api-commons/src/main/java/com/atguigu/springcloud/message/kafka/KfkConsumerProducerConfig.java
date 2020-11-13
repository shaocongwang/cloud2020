package com.atguigu.springcloud.message.kafka;

import java.util.Properties;

/**
 * kafka 的消费者和生产者配置
 */
public class KfkConsumerProducerConfig {
    //生产者配置信息实体
    private static final Properties KFK_PRODUCER_PRO = new Properties();
    //消费者配置信息实体
    private static final Properties KFK_CONSUMER_PRO = new Properties();

/********************************************* 公共性属性 STSRT **************************************************/
    //broker的地址清单，建议至少填写两个，避免宕机
    private static final String BOOTSTRAP_SERVERS = "192.168.136.128:9092";
    //key和value的序列化
    private static final String key_serializer = "org.apache.kafka.common.serialization.StringSerializer";
    private static final String value_serializer = "org.apache.kafka.common.serialization.StringSerializer";
/********************************************* 公共者属性 END ****************************************************/

    /********************************************* 生产者属性 STSRT **************************************************/
    //消费者组名称
    private static final String CONSUMER_GROUP_ID = "group-2";
    //session.timeout.ms：消费者在被认为死亡之前可以与服务器断开连接的时间，默认是3s 。
    private static final String SESSION_TIMEOUT_MS = "30000";
    //消费者是否自动提交偏移量，默认值是true,避免出现重复数据和数据丢失，可以把它设为 false。
    private static final String ENABLE_AUTO_COMMIT = "false";
    private static final String AUTO_COMMIT_INTERVAL_MS = "1000";

    //auto.offset.reset:消费者在读取一个没有偏移量的分区或者偏移量无效的情况下的处理
    //earliest：在偏移量无效的情况下，消费者将从起始位置读取分区的记录。
    //latest：在偏移量无效的情况下，消费者将从最新位置读取分区的记录
    private static final String AUTO_OFFSET_RESET = "latest";
    private static final String KEY_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
    private static final String VALUE_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
    private static final String DESERIALIZER_ENCODING = "UTF8";

    // max.partition.fetch.bytes：服务器从每个分区里返回给消费者的最大字节数
    //fetch.max.wait.ms:消费者等待时间，默认是500。
    // fetch.min.bytes:消费者从服务器获取记录的最小字节数。
    // client.id：该参数可以是任意的字符串，服务器会用它来识别消息的来源。
    // max.poll.records:用于控制单次调用 call （） 方住能够返回的记录数量
    //receive.buffer.bytes和send.buffer.bytes：指定了 TCP socket 接收和发送数据包的缓冲区大小，默认值为-1
/********************************************* 生产者属性 END **************************************************/


/********************************************* 消费者属性 STSRT **************************************************/
    //acks指定必须有多少个分区副本接收消息，生产者才认为消息写入成功，用户检测数据丢失的可能性
    //acks=0：生产者在成功写入消息之前不会等待任何来自服务器的响应。无法监控数据是否发送成功，但可以以网络能够支持的最大速度发送消息，达到很高的吞吐量。
    //acks=1：只要集群的首领节点收到消息，生产者就会收到来自服务器的成功响应。
    //acks=all：只有所有参与复制的节点全部收到消息时，生产者才会收到来自服务器的成功响应。这种模式是最安全的，
    private static final String ACKS = "all";
    //retries：生产者从服务器收到的错误有可能是临时性的错误的次数
    private static final Integer RETRIES = 0;
    //batch.size：该参数指定了一个批次可以使用的内存大小，按照字节数计算（而不是消息个数)。
    private static final Integer BATCH_SIZE = 16384;
    //linger.ms：该参数指定了生产者在发送批次之前等待更多消息加入批次的时间，增加延迟，提高吞吐量
    private static final Integer LINGER_MS = 1;
    //buffer.memory该参数用来设置生产者内存缓冲区的大小，生产者用它缓冲要发送到服务器的消息。
    private static final Integer BUFFER_MEMORY = 33554432;
    //compression.type:数据压缩格式，有snappy、gzip和lz4，snappy算法比较均衡，gzip会消耗更高的cpu，但压缩比更高
    //client.id：该参数可以是任意的字符串，服务器会用它来识别消息的来源。
    //max.in.flight.requests.per.connection：生产者在收到服务器晌应之前可以发送多少个消息。越大越占用内存，但会提高吞吐量
    //timeout.ms：指定了broker等待同步副本返回消息确认的时间
    //request.timeout.ms：生产者在发送数据后等待服务器返回响应的时间
    //metadata.fetch.timeout.ms：生产者在获取元数据（比如目标分区的首领是谁）时等待服务器返回响应的时间。
    // max.block.ms：该参数指定了在调用 send（）方法或使用 partitionsFor（）方法获取元数据时生产者阻塞时间
    // max.request.size：该参数用于控制生产者发送的请求大小。
    //receive.buffer.bytes和send.buffer.bytes：指定了 TCP socket 接收和发送数据包的缓冲区大小，默认值为-1
/********************************************* 消费者属性 END **************************************************/
    static{
    /**
     *
     */
    KFK_PRODUCER_PRO.put("bootstrap.servers", BOOTSTRAP_SERVERS);
    KFK_PRODUCER_PRO.put("group.id", CONSUMER_GROUP_ID);
    KFK_PRODUCER_PRO.put("session.timeout.ms", SESSION_TIMEOUT_MS);
    KFK_PRODUCER_PRO.put("enable.auto.commit", ENABLE_AUTO_COMMIT);
    KFK_PRODUCER_PRO.put("auto.commit.interval.ms", AUTO_COMMIT_INTERVAL_MS);
    KFK_PRODUCER_PRO.put("auto.offset.reset", AUTO_OFFSET_RESET);
    KFK_PRODUCER_PRO.put("key.deserializer", KEY_DESERIALIZER);
    KFK_PRODUCER_PRO.put("value.deserializer", VALUE_DESERIALIZER);
    KFK_PRODUCER_PRO.put("deserializer.encoding", DESERIALIZER_ENCODING);

    /**
     *
     */
    KFK_CONSUMER_PRO.put("bootstrap.servers", BOOTSTRAP_SERVERS);
    KFK_CONSUMER_PRO.put("acks", ACKS);
    KFK_CONSUMER_PRO.put("retries", RETRIES);
    KFK_CONSUMER_PRO.put("batch.size", BATCH_SIZE);
    KFK_CONSUMER_PRO.put("linger.ms", LINGER_MS);
    KFK_CONSUMER_PRO.put("buffer.memory", BUFFER_MEMORY);
    KFK_CONSUMER_PRO.put("key.serializer", VALUE_DESERIALIZER);
    KFK_CONSUMER_PRO.put("value.serializer", DESERIALIZER_ENCODING);
}

    /***
     *
     * @return
     */
    public static Properties getKfkProducerPro(){
        return KFK_PRODUCER_PRO;
    }

    /**
     *
     * @return
     */
    public static Properties getKfkConsumerPro() {
        return KFK_CONSUMER_PRO;
    }
}
