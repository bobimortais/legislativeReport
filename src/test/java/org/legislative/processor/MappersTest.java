package org.legislative.processor;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.legislative.entity.Bill;
import org.legislative.entity.Person;
import org.legislative.entity.Vote;
import org.legislative.entity.VoteResult;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MappersTest {

    @Test
    void person_validateCorrectObjectMapped() throws IOException {
        CSVRecord personRecord = testRecord("id,name", "1,Rep. 1");
        Person expectedPerson = new Person(1, "Rep. 1");

        Person person = Mappers.person(personRecord);

        assertEquals(expectedPerson, person);
    }

    @Test
    void bill_validateCorrectObjectMapped() throws IOException {
        CSVRecord billRecord = testRecord("id,title,sponsor_id", "1,bill1,1");
        Bill expectedBill = new Bill(1, "bill1", 1);

        Bill bill = Mappers.bill(billRecord);

        assertEquals(expectedBill, bill);
    }

    @Test
    void vote_validateCorrectObjectMapped() throws IOException {
        CSVRecord voteRecord = testRecord("id,bill_id", "1,1");
        Vote expectedVote = new Vote(1, 1);

        Vote vote = Mappers.vote(voteRecord);

        assertEquals(expectedVote, vote);
    }

    @Test
    void voteResult_validateCorrectObjectMapped() throws IOException {
        CSVRecord personRecord = testRecord("id,legislator_id,vote_id,vote_type", "1,1,1,1");
        VoteResult expectedVoteResult = new VoteResult(1, 1,1,1);

        VoteResult voteResult = Mappers.voteResult(personRecord);          // real class, real method

        assertEquals(expectedVoteResult, voteResult);
    }

    private CSVRecord testRecord(String header, String row) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setTrim(true)
                .build();

        return format.parse(new StringReader(header + "\n" + row)).getRecords().get(0);
    }
}