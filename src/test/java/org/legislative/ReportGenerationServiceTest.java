package org.legislative;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportGenerationServiceTest {
    final ReportGenerationService reportGenerationService = new ReportGenerationService();

    private Path csvDir;

    private static final Path OUT_DIR = Path.of("out");

    @BeforeAll
    static void createOutDir() throws IOException {
        Files.createDirectories(OUT_DIR);
    }

    @AfterAll
    static void deleteOutDir() throws IOException {
        if (!Files.exists(OUT_DIR)) return;
        try (Stream<Path> paths = Files.walk(OUT_DIR)) {
            paths.sorted(Comparator.reverseOrder())   // files first, then folders
                    .forEach(p -> p.toFile().delete());
        }
    }

    @BeforeEach
    void cleanOutDir() throws IOException {
        try (Stream<Path> files = Files.list(OUT_DIR)) {
            for (Path p : files.toList()) {
                Files.deleteIfExists(p);
            }
        }
    }

    @Test
    void generateReports_shouldReadFilesAndPrintOutputFiles() throws URISyntaxException, IOException {
        URL url = getClass().getResource("/csv");
        csvDir = Path.of(url.toURI());

        List<String> filesToRead = List.of("legislators.csv", "bills.csv", "votes.csv", "vote_results.csv");

        String[] args = filesToRead.stream()
                .map(name -> csvDir.resolve(name).toString())
                .toArray(String[]::new);

        PrintStream original = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        try {
            reportGenerationService.generateReports(args);
        } finally {
            System.setOut(original);
        }

        String actual = out.toString().replace("\r\n", "\n");   // Windows-safe

        assertEquals("""
        Output file legislators-support-oppose-count.csv generated
        Output file bills.csv generated
        """, actual);
    }

    @Test
    void main_shouldReadFilesAndPrintOutputMessageWhenBillSponsorLegislatorIsMissingFromFile() throws URISyntaxException, IOException {
        URL url = getClass().getResource("/csv");
        csvDir = Path.of(url.toURI());

        List<String> filesToRead = List.of("legislators.csv", "bills_with_missing_legislator.csv", "votes.csv", "vote_results.csv");

        String[] args = filesToRead.stream()
                .map(name -> csvDir.resolve(name).toString())
                .toArray(String[]::new);

        PrintStream original = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        try {
            reportGenerationService.generateReports(args);
        } finally {
            System.setOut(original);
        }

        String actual = out.toString().replace("\r\n", "\n");   // Windows-safe

        assertEquals("""
        Output file legislators-support-oppose-count.csv generated
        Primary sponsor not found for bill 2900994 (sponsor id: 400100)
        Output file bills.csv generated
        """, actual);
    }

}