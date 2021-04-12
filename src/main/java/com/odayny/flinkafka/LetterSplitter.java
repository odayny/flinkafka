package com.odayny.flinkafka;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class LetterSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
    @Override
    public void flatMap(String input, Collector<Tuple2<String, Integer>> out) throws Exception {
        for (String letter : input.split("")) {
            out.collect(new Tuple2<>(letter, 1));
        }
    }
}
