import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * 删除一个组及其所有成员
 * <p>
 * Created by cliffyan on 2017/9/24.
 */
public class DeleteGroup extends ConnectionWatcher {
    public void delete(String groupName) throws InterruptedException, KeeperException {
        String path = "/" + groupName;
        List<String> children;
        try {
            // ZooKeeper不支持递归的删除操作，因此在删除父节点之前必须先删除子节点。
            children = zk.getChildren(path, false);
            for (String child : children) {
                zk.delete(path + "/" + child, -1);
            }
            // 将版本号设置为-1，可以绕过这个版本检测机制,不管znode的版本号是什么而直接将其删除。
            zk.delete(path, -1);
            System.out.println("del all :" + path);
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect(Constant.HOST);
        deleteGroup.delete(Constant.GROUP_NAME);
        deleteGroup.close();
    }
}
