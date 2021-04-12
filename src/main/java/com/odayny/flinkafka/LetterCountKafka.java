package com.odayny.flinkafka;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

public class LetterCountKafka {
    static String inTopic = "messages_in";
    static String outTopic = "messages_out";
    static String consumerGroup = "flink";
    static String kafkaAddress = "localhost:9092";

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        FlinkKafkaConsumer011<String> flinkKafkaConsumer = Kafka.createStringConsumerForTopic(inTopic, kafkaAddress, consumerGroup);
        DataStreamSource<String> kafkaDataStream = environment.addSource(flinkKafkaConsumer);
        FlinkKafkaProducer011<Tuple2<String, Integer>> flinkKafkaProducer = Kafka.createTupleProducer(outTopic, kafkaAddress);
        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = kafkaDataStream.flatMap(new LetterSplitter())
                .keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
                    @Override
                    public String getKey(Tuple2<String, Integer> value) throws Exception {
                        return value.f0;
                    }
                })
                .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
                .sum(1);
        sum.addSink(flinkKafkaProducer);
        System.out.println("Flink environment initiated");
        environment.execute();
        System.out.println("Flink environment executed");
    }
}
