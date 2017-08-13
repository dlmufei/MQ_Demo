/**
 * 充当配置文件作用的接口,配置了Kafka的各种连接参数
 * <p>
 * Created by cliffyan on 2017/8/1.
 */
public interface KafkaProperties {
    final static String zkConnect = "10.22.10.139:2181";
    final static String groupId = "group1";
    final static String topic_test = "test";
    final static String kafkaServerURL = "10.22.10.139";
    final static int kafkaServerPort = 9092;
    final static int kafkaProducerBufferSize = 64 * 1024;
    final static int connectionTimeOut = 20000;
    final static int reconnectInterval = 10000;
    final static String topic2 = "topic2";
    final static String topic3 = "topic3";
    final static String clientId = "SimpleConsumerDemoClient";
}
