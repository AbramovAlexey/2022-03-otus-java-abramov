package ru.otus.protobuf.service;

import java.util.List;
import java.util.stream.IntStream;

public class SequenceGeneratorImpl implements SequenceGenerator{

    @Override
    public List<Integer> generate(int first, int last) {
        return IntStream.rangeClosed(first + 1, last)
                        .boxed().toList();
    }

}
