package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class FileSerializer implements Serializer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String fileName;

    @Override
    public void serialize(Map<String, Double> data) {
        try {
            objectMapper.writeValue(new File(fileName), data);
        } catch (IOException exception) {
            throw new FileProcessException("Error during writing file");
        }
    }
}
