package org.legislative.processor;

import org.apache.commons.csv.CSVRecord;
import org.legislative.entity.*;

import java.util.Arrays;
import java.util.List;

public final class Mappers {
    public static final List<String> VOTED_BILLS_PER_LEGISLATOR_HEADER = List.of("id", "name", "num_supported_bills", "num_opposed_bills");
    public static final List<String> BILLS_SUPPORT_HEADER = List.of("id", "title", "supporter_count", "opposer_count", "primary_sponsor");

    private Mappers() {}

    public static Person person(CSVRecord reader) {
        return new Person(Integer.parseInt(reader.get("id")), reader.get("name"));
    }

    public static Bill bill(CSVRecord reader) {
        return new Bill(Integer.parseInt(reader.get("id")), reader.get("title"), Integer.parseInt(reader.get("sponsor_id")));
    }

    public static Vote vote(CSVRecord reader) {
        return new Vote(Integer.parseInt(reader.get("id")), Integer.parseInt(reader.get("bill_id")));
    }

    public static VoteResult voteResult(CSVRecord reader) {
        return new VoteResult(Integer.parseInt(reader.get("id")), Integer.parseInt(reader.get("legislator_id")), Integer.parseInt(reader.get("vote_id")), Integer.parseInt(reader.get("vote_type")));
    }

    public static List<?> voteBillsPerLegislator(VotedBillsPerLegislator votedBillsPerLegislator) {
        return Arrays.asList(votedBillsPerLegislator.getLegislatorId(), votedBillsPerLegislator.getName(), votedBillsPerLegislator.getNumberOfSupportedBills(), votedBillsPerLegislator.getNumberOfOpposedBills());
    }

    public static List<?> billSupport(BillSupport billSupport) {
        return Arrays.asList(billSupport.getId(), billSupport.getTitle(), billSupport.getSupporterCount(), billSupport.getOpposerCount(), billSupport.getPrimarySponsorName());
    }
}
