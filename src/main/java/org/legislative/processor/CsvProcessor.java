package org.legislative.processor;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CsvProcessor {
    private static final CSVFormat FORMAT = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .setTrim(true)
            .build();

    public <T> List<T> read(Reader reader, Function<CSVRecord, T> mapper) throws IOException {
        List<T> result = new ArrayList<>();
        for (CSVRecord record : FORMAT.parse(reader)) {
            result.add(mapper.apply(record));
        }
        return result;
    }
}
