package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ru.otus.dto.MeasurementDto;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ResourcesFileLoader implements Loader {

    private final String fileName;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Measurement> load() {
        var sourceFile = getClass().getClassLoader().getResourceAsStream(fileName);
        try {
            MappingIterator<MeasurementDto> iterator = objectMapper.readerFor(MeasurementDto.class)
                                                               .readValues(sourceFile);
            return iterator.readAll().stream()
                           .map(dto -> new Measurement(dto.getName(), dto.getValue()))
                           .collect(Collectors.toList());
        } catch (IOException exception) {
           throw new FileProcessException("Error during reading file");
        }
    }

}
