package com.xxl.conf.example;

import com.xxl.conf.core.util.ZkConfgEnvironment;
import com.xxl.conf.core.util.PropertiesUtil;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by chengzhengrong on 2017/6/27.
 */
public class A {

    private static ReentrantLock INSTANCE_INIT_LOCK = new ReentrantLock(true);

    private ZooKeeper zooKeeper;

    @Test
    public void testa() throws InterruptedException {
        try {
            zooKeeper = new ZooKeeper(ZkConfgEnvironment.getConnectString(), 20000,new ZKWatcher());
        } catch (IOException e) {
            e.printStackTrace();
        }
        TimeUnit.HOURS.sleep(1);
    }

    @Test
    public void testb() throws InterruptedException {
        try {
            zooKeeper = new ZooKeeper(ZkConfgEnvironment.getConnectString(), 20000,new ZKWatcher());
        } catch (IOException e) {
            e.printStackTrace();
        }
        createRoot(ZkConfgEnvironment.getConfDataPath());
        TimeUnit.HOURS.sleep(1);
    }

    @Test
    public void testc() throws InterruptedException {
        try {
            zooKeeper = new ZooKeeper(ZkConfgEnvironment.getConfDataPath(), 20000,new ZKWatcher());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setData("zk.client","localhost:21880");
    }

    @Test
    public void testd() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(ZkConfgEnvironment.getConfDataPath(), 20000,new ZKWatcher());
        System.out.println("++++++++++++++++++++++++++");
        String data = getData("zk.client");
        System.out.println(data);
        TimeUnit.HOURS.sleep(1);
    }

    private String getData(String key){
        String path = ZkConfgEnvironment.getConnectString() + "/" + key;
        try {
            Stat stat = zooKeeper.exists(path, true);
            if(stat != null){
                byte[] date = zooKeeper.getData(path, true, null);
                return new String(date);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setData(String key,String data) throws InterruptedException {
        String path = ZkConfgEnvironment.getConfDataPath() + "/" + key;
        try {
            Stat stat = zooKeeper.exists(path, true);
            if(stat == null){
                createRoot(path);
                stat = zooKeeper.exists(path, true);
            }
            zooKeeper.setData(path,data.getBytes(),stat.getVersion());
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TimeUnit.HOURS.sleep(1);
    }

    private void createRoot(String path) {
        Stat stat = null;
        try {
            stat = zooKeeper.exists(path, true);
            if(stat == null){
                if(path.lastIndexOf("/")>0){
                    String substring = path.substring(0, path.lastIndexOf("/"));
                    createRoot(substring);
                }
                zooKeeper.create(path,new String(path).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class ZKWatcher implements Watcher{

        @Override
        public void process(WatchedEvent watchedEvent) {
            try {
                System.out.println(">>>>>>>>>> xxl-conf: watcher:{}");
                // session expire, close old and create new
                if (watchedEvent.getState() == Event.KeeperState.Expired) {
                    zooKeeper.close();
                    zooKeeper = null;
//                        getInstance();
                }

                String path = watchedEvent.getPath();
                if (path != null) {
                    // add One-time trigger
                    zooKeeper.exists(path, true);
                    if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
                        System.out.println(path+"::NodeDeleted!");
                    } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                        System.out.println(path+"::NodeDataChanged!");
                        String data = getData(path);
                        System.out.println("NodeDataChanged:" + data);
                    }
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test(){

        String na = "/watcher-conf".substring(0,1)+"dev"+"."+"/watcher-conf".substring(1);
        System.out.println(na);

    }
}
