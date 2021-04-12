package com.odayny.flinkafka;

import java.util.Properties;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

public class Kafka {
    public static FlinkKafkaConsumer011<String> createStringConsumerForTopic(String topic, String kafkaAddress,
            String kafkaGroup) {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kafkaAddress);
        props.setProperty("group.id", kafkaGroup);

        return new FlinkKafkaConsumer011<>(topic, new SimpleStringSchema(), props);
    }

    public static FlinkKafkaProducer011<String> createStringProducer(String topic, String kafkaAddress) {
        return new FlinkKafkaProducer011<>(kafkaAddress, topic, new SimpleStringSchema());
    }

    public static FlinkKafkaProducer011<Tuple2<String, Integer>> createTupleProducer(String topic, String kafkaAddress) {
        return new FlinkKafkaProducer011<>(kafkaAddress, topic, new TupleSerializationSchema());
    }
}
