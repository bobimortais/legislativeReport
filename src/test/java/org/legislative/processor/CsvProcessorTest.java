package org.legislative.processor;

import org.junit.jupiter.api.Test;
import org.legislative.entity.Bill;
import org.legislative.entity.Person;
import org.legislative.entity.Vote;
import org.legislative.entity.VoteResult;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvProcessorTest {
    CsvProcessor csvProcessor = new CsvProcessor();

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
}