import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;

/**
 * 配置存储服务
 * <p>
 * Created by cliffyan on 2017/9/24.
 */
public class ActiveKeyValueStore extends ConnectionWatcher {
    private static final Charset CHARSET = Charset.forName("UTF-8");

    /**
     * 将一个关键字及其值写到ZooKeeper
     *
     * @param path  路径
     * @param value 对应value值
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void write(String path, String value) throws KeeperException, InterruptedException {
        Stat stat = zk.exists(path, false);
        if (stat == null) {
            zk.create(path, value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            zk.setData(path, value.getBytes(CHARSET), -1);
        }
    }

    public String read(String path, Watcher watch) throws KeeperException, InterruptedException {
        byte[] data = zk.getData(path, watch, null);
        return new String(data, CHARSET);

    }

}
