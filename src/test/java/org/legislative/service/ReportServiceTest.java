package org.legislative.service;

import org.junit.jupiter.api.Test;
import org.legislative.entity.Person;
import org.legislative.entity.VoteResult;
import org.legislative.entity.VotedBillsPerLegislator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportServiceTest {

    ReportService reportService = new ReportService();

    @Test
    void getLegislatorsBillsVotes_shouldReturnListOfCountOfBillVotesPerLegislator() {
        var votedBillsPerLegislator1 = new VotedBillsPerLegislator(1, "Rep. Don Bacon (R-NE-2)", 1, 2);
        var votedBillsPerLegislator2 = new VotedBillsPerLegislator(2, "Rep. Jamaal Bowman (D-NY-16)", 2, 1);
        List<VotedBillsPerLegislator> expectedVotedBillsPerLegislators = List.of(votedBillsPerLegislator1, votedBillsPerLegislator2);

        List<Person> legislators = List.of(new Person(1, "Rep. Don Bacon (R-NE-2)"), new Person(2, "Rep. Jamaal Bowman (D-NY-16)"));
        List<VoteResult> listOfVoteResults = List.of(new VoteResult(1, 1, 1, 1), new VoteResult(2, 1, 3, 2),new VoteResult(3, 1, 6, 2), new VoteResult(4, 2, 2, 1), new VoteResult(5, 2, 4, 1), new VoteResult(6, 2, 5, 2));

        List<VotedBillsPerLegislator> fetchedVotedBillsPerLegislator = reportService.getListOfBillVotesPerLegislator(legislators, listOfVoteResults);

        assertEquals(expectedVotedBillsPerLegislators, fetchedVotedBillsPerLegislator);
    }

}