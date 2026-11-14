package org.legislative;

import org.legislative.entity.*;
import org.legislative.processor.CsvProcessor;
import org.legislative.processor.Mappers;
import org.legislative.service.LegislativeStatisticsService;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ReportGenerationService {
    public void generateReports(String[] inputArgs) throws IOException {
        Files.createDirectories(Path.of("out"));

        CsvProcessor csvProcessor = new CsvProcessor();
        LegislativeStatisticsService legislativeStatisticsService = new LegislativeStatisticsService();
        List<Path> inputs = Arrays.stream(inputArgs).map(Path::of).toList();

        List<Person> legislators = csvProcessor.read(inputs.get(0), Mappers::person);
        List<Bill> bills = csvProcessor.read(inputs.get(1), Mappers::bill);
        List<Vote> votes = csvProcessor.read(inputs.get(2), Mappers::vote);
        List<VoteResult> voteResults = csvProcessor.read(inputs.get(3), Mappers::voteResult);

        try (Writer writer = Files.newBufferedWriter(Path.of("out/legislators-support-oppose-count.csv"))) {
            List<VotedBillsPerLegislator> votesPerLegislator = legislativeStatisticsService.getListOfBillVotesPerLegislator(legislators, voteResults);
            csvProcessor.write(writer, votesPerLegislator, Mappers.VOTED_BILLS_PER_LEGISLATOR_HEADER, Mappers::voteBillsPerLegislator);
            System.out.println("Output file legislators-support-oppose-count.csv generated");
        }

        try (Writer writer = Files.newBufferedWriter(Path.of("out/bills.csv"))) {
            List<BillSupport> billsSupport = legislativeStatisticsService.getListOfBillSupport(legislators, bills, votes, voteResults);
            csvProcessor.write(writer, billsSupport, Mappers.BILLS_SUPPORT_HEADER, Mappers::billSupport);
            System.out.println("Output file bills.csv generated");
        }
    }
}