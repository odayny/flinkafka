package com.odayny.flinkafka;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TupleSerializationSchema implements SerializationSchema<Tuple2<String, Integer>> {

    @Override
    public byte[] serialize(Tuple2<String, Integer> element) {
        return (element.f0 + ": " + element.f1).getBytes(StandardCharsets.UTF_8);
    }
}
