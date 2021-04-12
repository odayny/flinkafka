package com.odayny.flinkafka;

import org.apache.flink.streaming.api.functions.sink.SinkFunction;

public class ConsoleSink implements SinkFunction<String> {
    @Override
    public void invoke(String value, Context context) throws Exception {
        System.out.println("Sink received " + value);
    }
}
