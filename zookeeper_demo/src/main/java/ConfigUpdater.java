import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 随机更新ZooKeeper中的属性
 * <p>
 * Created by cliffyan on 2017/9/24.
 */
public class ConfigUpdater {
    public static final String PATH = "/config";

    private ActiveKeyValueStore store;
    private Random random = new Random();

    public ConfigUpdater(String hosts) throws IOException, InterruptedException {
        store = new ActiveKeyValueStore();
        store.connect(hosts);
    }

    public void run() throws InterruptedException, KeeperException {
        while (true) {
            String value = random.nextInt(100) + "";
            store.write(PATH, value);
            System.out.printf("Set %s to %s\n", PATH, value);
//            TimeUnit.SECONDS.sleep(random.nextInt(1000));
            Thread.sleep(1000);

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ConfigUpdater configUpdater = new ConfigUpdater(Constant.HOST);
        configUpdater.run();
    }
}
