import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.io.IOException;

/**
 * 每个组成员将作为一个程序运行，并且加入到组中。当程序退出时，这个组成员应当从组中被删除。为了实现这一点，我们在ZooKeeper的命名空间中使用短暂znode来代表一个组成员
 * <p>
 * Created by cliffyan on 2017/9/24.
 */
public class JoinGroup extends ConnectionWatcher {
    public void join(String groupName, String memberName) throws KeeperException, InterruptedException {
        String path = "/" + groupName + "/" + memberName;
        String createdPath = zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Created:" + createdPath);
    }

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        JoinGroup joinGroup = new JoinGroup();
        joinGroup.connect(Constant.HOST);
        joinGroup.join(Constant.GROUP_NAME, Constant.MEMBER_NAME);

        //stay alive until process is killed or thread is interrupted
        Thread.sleep(Long.MAX_VALUE);
    }
}
