import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;

import java.util.Properties;

/**
 * 生产者线程,模拟产生消息
 * http://kafka.apache.org/0110/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html
 * <p>
 * Created by cliffyan on 2017/8/2.
 */
public class KafkaProducerThread extends Thread {
    private Producer<String, String> producer = null;
    private String topic = null;
    private Properties props = new Properties();

    public KafkaProducerThread(String topic) {
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer(props);
        this.topic = topic;
    }

    @Override
    public void run() {
        try {
            int messageNo = 1;
//            producer.initTransactions();
            while (true) {
                String keyStr = new String("key_" + messageNo);
                String valueStr = new String("value_" + messageNo);
                System.out.println("Send:" + messageNo);
                try {
//                    producer.beginTransaction();
                    System.out.println("beginTransaction");
                    for (int i = 0; i < 10; i++) {
                        producer.send(new ProducerRecord<String, String>(topic, keyStr + "_" + i, valueStr + "_" + i));
                    }
//                    producer.commitTransaction();
                    System.out.println("commitTransaction");


                } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
                    // We can't recover from these exceptions, so our only option is to close the producer and exit.
                    producer.close();
                } catch (KafkaException e) {
                    // For all other exceptions, just abort the transaction and try again.
//                    producer.abortTransaction();
                }
                messageNo++;
                if (messageNo == 30) {
                    break;
                }
            }
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            producer.close();
            System.out.println("producer.close()");

        }
    }
}
