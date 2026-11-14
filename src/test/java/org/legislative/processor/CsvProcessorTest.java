package org.legislative.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.legislative.entity.*;
import org.legislative.exception.MalformedInputFileException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvProcessorTest {
    final CsvProcessor csvProcessor = new CsvProcessor();

    @Test
    void read_shouldReadCSVAndReturnPersonList() throws IOException {
        List<Person> expectedPersonList = List.of(new Person(1, "Jane"), new Person(2, "Doe"));
        Reader reader = new StringReader("""
            id,name
            1,Jane
            2,Doe
            """);
        List<Person> fetchedPersonList = csvProcessor.read(reader, Mappers::person);

        assertEquals(expectedPersonList, fetchedPersonList);
    }

    @Test
    void read_shouldThrowExceptionForMalformedInputFile() {
        Reader reader = new StringReader("""
            id
            1,Jane
            2,Doe
            """);
        Assertions.assertThrows(MalformedInputFileException.class, () -> csvProcessor.read(reader, Mappers::person));
    }

    @Test
    void read_shouldReadCSVAndCReturnBillList() throws IOException {
        Bill bill1 = Bill.builder().id(1).title("bill1").primarySponsor(1).build();
        Bill bill2 = Bill.builder().id(2).title("bill2").primarySponsor(2).build();
        Bill bill3 = Bill.builder().id(3).title("bill3").primarySponsor(3).build();

        List<Bill> expectedBillList = List.of(bill1, bill2, bill3);
        Reader reader = new StringReader("""
            id,title,sponsor_id
            1,bill1,1
            2,bill2,2
            3,bill3,3
            """);
        List<Bill> fetchedBillList = csvProcessor.read(reader, Mappers::bill);

        assertEquals(expectedBillList, fetchedBillList);
    }

    @Test
    void read_shouldReadCSVAndReturnVoteList() throws IOException {
        Vote vote1 = new Vote(1, 1);
        Vote vote2 = new Vote(2, 1);
        Vote vote3 = new Vote(3, 2);

        List<Vote> expectedVoteList = List.of(vote1, vote2, vote3);
        Reader reader = new StringReader("""
            id,bill_id
            1,1
            2,1
            3,2
            """);
        List<Vote> fetchedVoteList = csvProcessor.read(reader, Mappers::vote);

        assertEquals(expectedVoteList, fetchedVoteList);
    }

    @Test
    void read_shouldReadCSVAndReturnVoteResultList() throws IOException {
        VoteResult voteResult1 = new VoteResult(1, 1, 1, 1);
        VoteResult voteResult2 = new VoteResult(2, 1, 2, 2);
        VoteResult voteResult3 = new VoteResult(3, 1, 3, 1);

        List<VoteResult> expectedVoteResultList = List.of(voteResult1, voteResult2, voteResult3);
        Reader reader = new StringReader("""
            id,legislator_id,vote_id,vote_type
            1,1,1,1
            2,1,2,2
            3,1,3,1
            """);
        List<VoteResult> fetchedVoteResultList = csvProcessor.read(reader, Mappers::voteResult);

        assertEquals(expectedVoteResultList, fetchedVoteResultList);
    }

    @Test
    void write_shouldWriteVotedBillsPerLegislatorToFile() throws IOException {
        VotedBillsPerLegislator votedBillsPerLegislator1 = new VotedBillsPerLegislator(1, "Rep. 1", 1, 2);
        VotedBillsPerLegislator votedBillsPerLegislator2 = new VotedBillsPerLegislator(2, "Rep. 2", 2, 1);
        VotedBillsPerLegislator votedBillsPerLegislator3 = new VotedBillsPerLegislator(3, "Rep. 3", 5, 3);

        List<VotedBillsPerLegislator> votedBillsPerLegislators = List.of(votedBillsPerLegislator1, votedBillsPerLegislator2, votedBillsPerLegislator3);

        StringWriter out = new StringWriter();
        csvProcessor.write(out, votedBillsPerLegislators, Mappers.VOTED_BILLS_PER_LEGISLATOR_HEADER, Mappers::voteBillsPerLegislator);

        assertEquals("""
            id,name,num_supported_bills,num_opposed_bills
            1,Rep. 1,1,2
            2,Rep. 2,2,1
            3,Rep. 3,5,3
            """, out.toString());
    }

    @Test
    void write_shouldWriteBillsSupportToFile() throws IOException {
        BillSupport billSupport1 = BillSupport.builder().id(1).title("bill1").supporterCount(2).opposerCount(1).primarySponsorName("Rep. 1").build();
        BillSupport billSupport2 = BillSupport.builder().id(2).title("bill2").supporterCount(0).opposerCount(3).primarySponsorName("Rep. 2").build();
        BillSupport billSupport3 = BillSupport.builder().id(3).title("bill3").supporterCount(2).opposerCount(5).primarySponsorName("Rep. 3").build();

        List<BillSupport> billsSupport = List.of(billSupport1, billSupport2, billSupport3);

        StringWriter out = new StringWriter();
        csvProcessor.write(out, billsSupport, Mappers.BILLS_SUPPORT_HEADER, Mappers::billSupport);

        assertEquals("""
            id,title,supporter_count,opposer_count,primary_sponsor
            1,bill1,2,1,Rep. 1
            2,bill2,0,3,Rep. 2
            3,bill3,2,5,Rep. 3
            """, out.toString());
    }

}