package org.legislative.processor;

import org.apache.commons.csv.CSVRecord;
import org.legislative.entity.Bill;
import org.legislative.entity.Person;
import org.legislative.entity.Vote;
import org.legislative.entity.VoteResult;

public final class Mappers {
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
}
