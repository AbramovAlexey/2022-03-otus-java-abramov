package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.otus.dto.MeasurementDto;
import ru.otus.model.Measurement;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ResourcesFileLoader implements Loader {

    private final String fileName;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public List<Measurement> load() {
        var sourceFile = getClass().getClassLoader().getResourceAsStream(fileName);
        MappingIterator<MeasurementDto> iterator = objectMapper.readerFor(MeasurementDto.class)
                                                               .readValues(sourceFile);
        return iterator.readAll().stream()
                       .map(dto -> new Measurement(dto.getName(), dto.getValue()))
                       .collect(Collectors.toList());
    }

}
