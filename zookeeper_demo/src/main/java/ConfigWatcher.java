import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.IOException;

/**
 * 观察ZooKeeper中属性的更新情况，并将其打印到控制台
 * http://www.cnblogs.com/sunddenly/p/4064992.html
 * <p>
 * Created by cliffyan on 2017/9/24.
 */
public class ConfigWatcher implements Watcher {
    private ActiveKeyValueStore store;

    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeDataChanged) {
            try {
                dispalyConfig();
            } catch (InterruptedException e) {
                System.err.println("Interrupted. exiting. ");
                Thread.currentThread().interrupt();
            } catch (KeeperException e) {
                System.out.printf("KeeperExceptions. Exiting.\n", e);
            }

        }

    }

    public ConfigWatcher(String hosts) throws IOException, InterruptedException {
        store = new ActiveKeyValueStore();
        store.connect(hosts);
    }

    public void dispalyConfig() throws KeeperException, InterruptedException {
        String value = store.read(ConfigUpdater.PATH, this);
        System.out.printf("Read %s as %s\n", ConfigUpdater.PATH, value);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ConfigWatcher configWatcher = new ConfigWatcher(Constant.HOST);
        configWatcher.dispalyConfig();
        //stay alive until process is killed or Thread is interrupted
        Thread.sleep(Long.MAX_VALUE);
    }
}