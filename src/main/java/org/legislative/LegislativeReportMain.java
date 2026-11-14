package org.legislative;

import org.legislative.entity.*;
import org.legislative.exception.MissingInputArgumentException;
import org.legislative.processor.CsvProcessor;
import org.legislative.processor.Mappers;
import org.legislative.service.ReportService;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class LegislativeReportMain {

    public static void main(String[] args) {
        try {
            run(args);
        } catch (Exception e) {
            System.out.println("There was an issue while generating the reports. Please refer to README.md for troubleshooting.");
        }
    }

    static void run(String[] args) throws IOException {
        if(args.length != 4) {
            throw new MissingInputArgumentException();
        }

        CsvProcessor csvProcessor = new CsvProcessor();
        ReportService reportService = new ReportService();
        List<Path> inputs = Arrays.stream(args).map(Path::of).toList();

        List<Person> legislators = csvProcessor.read(inputs.get(0), Mappers::person);
        List<Bill> bills = csvProcessor.read(inputs.get(1), Mappers::bill);
        List<Vote> votes = csvProcessor.read(inputs.get(2), Mappers::vote);
        List<VoteResult> voteResults = csvProcessor.read(inputs.get(3), Mappers::voteResult);

        generateReports(reportService, legislators, voteResults, csvProcessor, bills, votes);
    }

    private static void generateReports(ReportService reportService, List<Person> legislators, List<VoteResult> voteResults, CsvProcessor csvProcessor, List<Bill> bills, List<Vote> votes) throws IOException {
        Files.createDirectories(Path.of("out"));

        try (Writer writer = Files.newBufferedWriter(Path.of("out/legislators-support-oppose-count.csv"))) {
            List<VotedBillsPerLegislator> votesPerLegislator = reportService.getListOfBillVotesPerLegislator(legislators, voteResults);
            csvProcessor.write(writer, votesPerLegislator, Mappers.VOTED_BILLS_PER_LEGISLATOR_HEADER, Mappers::voteBillsPerLegislator);
            System.out.println("Output file legislators-support-oppose-count.csv generated");
        }

        try (Writer writer = Files.newBufferedWriter(Path.of("out/bills.csv"))) {
            List<BillSupport> billsSupport = reportService.getListOfBillSupport(legislators, bills, votes, voteResults);
            csvProcessor.write(writer, billsSupport, Mappers.BILLS_SUPPORT_HEADER, Mappers::billSupport);
            System.out.println("Output file bills.csv generated");
        }
    }
}