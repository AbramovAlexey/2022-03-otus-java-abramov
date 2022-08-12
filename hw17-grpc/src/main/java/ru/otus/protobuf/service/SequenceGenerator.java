package ru.otus.protobuf.service;

import java.util.List;

public interface SequenceGenerator {

    List<Integer> generate(int first, int last);

}
