package org.legislative.processor;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CsvProcessor {
    private static final CSVFormat READ_FORMAT = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .setTrim(true)
            .build();

    private static final CSVFormat WRITE_FORMAT = CSVFormat.DEFAULT.builder()
            .setRecordSeparator("\n")
            .build();

    public <T> List<T> read(Reader reader, Function<CSVRecord, T> mapper) throws IOException {
        List<T> result = new ArrayList<>();
        for (CSVRecord record : READ_FORMAT.parse(reader)) {
            result.add(mapper.apply(record));
        }
        return result;
    }

    public <T> List<T> read(Path path, Function<CSVRecord, T> mapper) throws IOException {
        try (Reader reader = Files.newBufferedReader(path)) {
            return read(reader, mapper);
        }
    }

    public <T> void write(Writer writer, List<T> items, List<String> header, Function<T, List<?>> rowMapper) throws IOException {
        CSVFormat format = WRITE_FORMAT.builder()
                .setHeader(header.toArray(String[]::new))
                .build();

        try (CSVPrinter printer = new CSVPrinter(writer, format)) {
            for (T item : items) {
                printer.printRecord(rowMapper.apply(item));
            }
        }
    }
}
