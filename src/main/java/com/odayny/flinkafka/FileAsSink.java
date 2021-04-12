package com.odayny.flinkafka;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

public class FileAsSink {
    static String inTopic = "messages_in";
    static String outTopic = "messages_out";
    static String consumerGroup = "flink";
    static String kafkaAddress = "localhost:9092";

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        FlinkKafkaConsumer011<String> flinkKafkaConsumer = Kafka.createStringConsumerForTopic(inTopic, kafkaAddress, consumerGroup);
        DataStream<String> consoleDataStream = environment.readTextFile("text.txt");
//        DataStreamSource<String> kafkaDataStream = environment.addSource(flinkKafkaConsumer);
//        DataStream<String> allStreams = consoleDataStream.union(kafkaDataStream);
        FlinkKafkaProducer011<String> flinkKafkaProducer = Kafka.createStringProducer(outTopic, kafkaAddress);
        ConsoleSink consoleSink = new ConsoleSink();
        SingleOutputStreamOperator<String> map = consoleDataStream.map(new WordsCapitalizer());
        map.addSink(consoleSink);
        map.addSink(flinkKafkaProducer);
        System.out.println("Flink environment initiated");
        environment.execute();
        System.out.println("Flink environment executed");
    }
}
