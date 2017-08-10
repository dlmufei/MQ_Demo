## 启动kafka命令
* 0.切换到kafka目录
> cd ../kafka_2.11-0.11.0.0/
* 1.启动zookeeper:
>  bin/zookeeper-server-start.sh config/zookeeper.properties
* 2.启动kafka
> bin/kafka-server-start.sh config/server.properties
* 3.创建topic
> bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
