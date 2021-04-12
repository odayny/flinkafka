package com.odayny.flinkafka;

import org.apache.flink.api.common.functions.MapFunction;

import java.util.Locale;

public class WordsCapitalizer implements MapFunction<String, String> {
    @Override
    public String map(String value) {
        System.out.println(Thread.currentThread().getName());
        return value.toUpperCase(Locale.ROOT);
    }
}
