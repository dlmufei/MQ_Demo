import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.*;

/**
 * http://kafka.apache.org/0110/documentation/streams/developer-guide#streams_dsl
 * <p>
 * Created by cliffyan on 2017/8/29.
 */
public class StreamsDSL {

    public class GenericRecord<K,V> {
        private K key;
        private V value;

        public V get(){
            return value;
        }


    }

    public static void main(String[] args) {
        KStreamBuilder builder = new KStreamBuilder();

        KStream<String, GenericRecord> source1 = builder.stream("topic1", "topic2");
        KTable<String, GenericRecord> source2 = builder.table("topic3", "stateStoreName");
        GlobalKTable<String, GenericRecord> source3 = builder.globalTable("topic4", "globalStoreName");

        // written in Java 8+, using lambda expressions
        KStream<String, GenericRecord> mapped = source1.mapValues(record -> record.get("category"));
        KStream<String, GenericRecord> mapped = source1.mapValues(new ValueMapper<GenericRecord, GenericRecord>() {
            @Override
            public GenericRecord apply(GenericRecord value) {
                return value.;
            }
        });


        KTable<Windowed<String>, Long> counts = source1.groupByKey().aggregate(
                () -> 0L,  // initial value
                (aggKey, value, aggregate) -> aggregate + 1L,   // aggregating value
                TimeWindows.of("counts", 5000L).advanceBy(1000L), // intervals in milliseconds
                Serdes.Long() // serde for aggregated value
        );

        KStream<String, String> joined = source1.leftJoin(source2,
                (record1, record2) -> record1.get("user") + "-" + record2.get("region");
        );
        joined.to("topic4");

        KStream<String, String> materialized = joined.through("topic4");

    }

}
