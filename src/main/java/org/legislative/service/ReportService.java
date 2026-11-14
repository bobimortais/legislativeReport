package org.legislative.service;

import org.legislative.entity.Person;
import org.legislative.entity.VoteResult;
import org.legislative.entity.VotedBillsPerLegislator;

import java.util.ArrayList;
import java.util.List;

public class ReportService {

    public List<VotedBillsPerLegislator> getListOfBillVotesPerLegislator(List<Person> legislators, List<VoteResult> listOfVoteResults) {
        var votedBillsPerLegislators = new ArrayList<VotedBillsPerLegislator>();
        legislators.forEach(legislator -> {
            var votedBillsPerLegislator = new VotedBillsPerLegislator();
            votedBillsPerLegislator.setLegislatorId(legislator.getId());
            votedBillsPerLegislator.setName(legislator.getName());

            long supportedBills = listOfVoteResults.stream().filter(v -> v.getLegislatorId().equals(legislator.getId())).filter(v -> v.getVoteType().equals(1)).count();
            long unsupportedBills = listOfVoteResults.stream().filter(v -> v.getLegislatorId().equals(legislator.getId())).filter(v -> v.getVoteType().equals(2)).count();
            votedBillsPerLegislator.setNumberOfSupportedBills(Long.valueOf(supportedBills).intValue());
            votedBillsPerLegislator.setNumberOfOpposedBills(Long.valueOf(unsupportedBills).intValue());

            votedBillsPerLegislators.add(votedBillsPerLegislator);});

        return votedBillsPerLegislators;
    }
}
