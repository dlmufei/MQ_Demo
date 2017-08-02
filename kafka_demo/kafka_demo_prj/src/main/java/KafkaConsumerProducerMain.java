/**
 * Demo启动入口函数
 *
 * Created by cliffyan on 2017/8/3.
 */
public class KafkaConsumerProducerMain {

    public static void main(String[] args){
        KafkaProducerThread producerThread = new KafkaProducerThread(KafkaProperties.topic);
        producerThread.start();

        KafkaConsumerThread consumerThread = new KafkaConsumerThread(KafkaProperties.topic);
        consumerThread.start();
    }
}
