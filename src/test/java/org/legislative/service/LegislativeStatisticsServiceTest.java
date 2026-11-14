package org.legislative.service;

import org.junit.jupiter.api.Test;
import org.legislative.entity.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LegislativeStatisticsServiceTest {

    final LegislativeStatisticsService legislativeStatisticsService = new LegislativeStatisticsService();

    @Test
    void getLegislatorsBillsVotes_shouldReturnListOfCountOfBillVotesPerLegislator() {
        var votedBillsPerLegislator1 = new VotedBillsPerLegislator(1, "Rep. 1", 1, 2);
        var votedBillsPerLegislator2 = new VotedBillsPerLegislator(2, "Rep. 2", 2, 1);
        List<VotedBillsPerLegislator> expectedVotedBillsPerLegislators = List.of(votedBillsPerLegislator1, votedBillsPerLegislator2);

        List<Person> legislators = List.of(new Person(1, "Rep. 1"), new Person(2, "Rep. 2"));
        List<VoteResult> listOfVoteResults = List.of(new VoteResult(1, 1, 1, 1), new VoteResult(2, 1, 3, 2),new VoteResult(3, 1, 6, 2), new VoteResult(4, 2, 2, 1), new VoteResult(5, 2, 4, 1), new VoteResult(6, 2, 5, 2));

        List<VotedBillsPerLegislator> fetchedVotedBillsPerLegislator = legislativeStatisticsService.getListOfBillVotesPerLegislator(legislators, listOfVoteResults);

        assertEquals(expectedVotedBillsPerLegislators, fetchedVotedBillsPerLegislator);
    }

    @Test
    void getBillsSupport_shouldReturnListOfBillsWithSupportInfo() {
        List<Vote> listOfVotes = List.of(new Vote(1, 1), new Vote(2, 1), new Vote(3, 1), new Vote(4, 2), new Vote(5, 2), new Vote(6, 3));
        List<VoteResult> listOfVoteResults = List.of(new VoteResult(1, 1, 1, 1), new VoteResult(2, 1, 2, 2),new VoteResult(3, 1, 3, 2), new VoteResult(4, 2, 4, 1), new VoteResult(5, 2, 5, 1), new VoteResult(6, 2, 6, 2));

        Person legislator1 = new Person(1, "Rep. 1");
        Person legislator2 = new Person(2, "Rep. 2");
        Person legislator3 = new Person(3, "Rep. 3");

        List<Person> legislators = List.of(legislator1, legislator2, legislator3);

        Bill bill1 = BillSupport.builder().id(1).title("Bill 1").primarySponsor(1).build();
        Bill bill2 = BillSupport.builder().id(2).title("Bill 2").primarySponsor(2).build();
        Bill bill3 = BillSupport.builder().id(3).title("Bill 3").primarySponsor(3).build();

        List<Bill> bills = List.of(bill1, bill2, bill3);

        BillSupport bill1Support = BillSupport.builder().id(1).title("Bill 1").supporterCount(1).opposerCount(2).primarySponsorName("Rep. 1").build();
        BillSupport bill2Support = BillSupport.builder().id(2).title("Bill 2").supporterCount(2).opposerCount(0).primarySponsorName("Rep. 2").build();
        BillSupport bill3Support = BillSupport.builder().id(3).title("Bill 3").supporterCount(0).opposerCount(1).primarySponsorName("Rep. 3").build();

        List<BillSupport> expectedListOfBillsSupport = List.of(bill1Support, bill2Support, bill3Support);

        List<BillSupport> fetchedListOfBillsSupport = legislativeStatisticsService.getListOfBillSupport(legislators, bills, listOfVotes, listOfVoteResults);

        assertEquals(expectedListOfBillsSupport, fetchedListOfBillsSupport);
    }

}