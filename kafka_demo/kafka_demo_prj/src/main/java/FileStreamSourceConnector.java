import org.apache.kafka.common.config.Config;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectorContext;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kafka connect demo
 * <p>
 * http://kafka.apache.org/documentation.html#connect_developing
 * Created by cliffyan on 2017/9/17.
 */
public class FileStreamSourceConnector extends SourceConnector {

    public final String FILE_CONFIG = "";
    public final String TOPIC_CONFIG = "";


    private static final ConfigDef CONFIG_DEF = new ConfigDef()
            .define(FILE_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "Source filename.")
            .define(TOPIC_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "The topic to publish data to");


    private String filename;
    private String topic;


    public FileStreamSourceConnector() {
        super();
    }

    @Override
    public ConfigDef config() {
        return CONFIG_DEF;
    }

    @Override
    public void initialize(ConnectorContext ctx) {
        super.initialize(ctx);
    }

    @Override
    public void initialize(ConnectorContext ctx, List<Map<String, String>> taskConfigs) {
        super.initialize(ctx, taskConfigs);
    }

    @Override
    public void reconfigure(Map<String, String> props) {
        super.reconfigure(props);
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        ArrayList<Map<String, String>> configs = new ArrayList<>();
        // Only one input stream makes sense.
        Map<String, String> config = new HashMap<>();
        if (filename != null)
            config.put(FILE_CONFIG, filename);
        config.put(TOPIC_CONFIG, topic);
        configs.add(config);
        return configs;
    }

    @Override
    public Config validate(Map<String, String> connectorConfigs) {
        return super.validate(connectorConfigs);
    }

    @Override
    public String version() {
        return null;
    }

    @Override
    public void start(Map<String, String> props) {
        // The complete version includes error handling as well.
        filename = props.get(FILE_CONFIG);
        topic = props.get(TOPIC_CONFIG);

    }

    @Override
    public void stop() {

    }

    @Override
    public Class<? extends Task> taskClass() {
        return FileStreamSourceTask.class;
    }
}
