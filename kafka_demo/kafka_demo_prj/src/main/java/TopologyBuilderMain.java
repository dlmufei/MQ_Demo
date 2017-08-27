import org.apache.kafka.streams.processor.TopologyBuilder;

/**
 * http://kafka.apache.org/0110/documentation/streams/developer-guide#streams_processor_topology
 * <p>
 * Created by cliffyan on 2017/8/28.
 */
public class TopologyBuilderMain {

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        //设置处理拓扑的数据来源topic
        builder.addSource("SOURCE", "src-topic")
                // 增加 PROCESS1 节点,设置 SOURCE 作为 数据来源
                .addProcessor("PROCESS1", () -> new MyProcessor1(), "SOURCE")

                // 增加 PROCESS2 节点,设置 PROCESS1 作为 数据来源
                .addProcessor("PROCESS2", () -> new MyProcessor2(), "PROCESS1")

                // 增加 PROCESS3 节点,设置 PROCESS1 作为 数据来源
                .addProcessor("PROCESS3", () -> new MyProcessor3(), "PROCESS1")

                // 增加sink1 将 PROCESS1 的结果作为 topic "sink-topic1"输出
                .addSink("SINK1", "sink-topic1", "PROCESS1")

                //增加sink2 将 PROCESS2 的结果作为 topic "sink-topic2"输出
                .addSink("SINK2", "sink-topic2", "PROCESS2")

                // 增加sink3 将 PROCESS2 的结果作为 topic "sink-topic3"输出
                .addSink("SINK3", "sink-topic3", "PROCESS3");
    }
}
