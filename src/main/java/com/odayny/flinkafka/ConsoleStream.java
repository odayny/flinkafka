package com.odayny.flinkafka;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleStream implements SourceFunction<String> {
    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            sourceContext.collect(reader.readLine());
        }
    }

    @Override
    public void cancel() {

    }
}
