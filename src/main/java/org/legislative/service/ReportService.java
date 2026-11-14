package org.legislative.service;

import org.legislative.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<BillSupport> getListOfBillSupport(List<Person> legislators, List<Bill> bills, List<Vote> listOfVotes, List<VoteResult> listOfVoteResults) {
        var billSupportList =  new ArrayList<BillSupport>();

        bills.forEach(bill -> {
            var billSupport = BillSupport.builder();
            billSupport.title(bill.getTitle());
            billSupport.id(bill.getId());
            billSupport.primarySponsorName(legislators.stream().filter(l -> l.getId().equals(bill.getPrimarySponsor())).findFirst().get().getName());

            Set<Integer> listOfUniqueVotesForBill = listOfVotes.stream().filter(vote -> vote.getBillId().equals(bill.getId())).map(Vote::getId).collect(Collectors.toSet());;
            long billSupporterCount = listOfVoteResults.stream().filter(voteResult -> listOfUniqueVotesForBill.contains(voteResult.getVoteId()) && voteResult.getVoteType().equals(1)).count();
            long billOpposerCount = listOfVoteResults.stream().filter(voteResult -> listOfUniqueVotesForBill.contains(voteResult.getVoteId()) && voteResult.getVoteType().equals(2)).count();

            billSupport.supporterCount(Long.valueOf(billSupporterCount).intValue());
            billSupport.opposerCount(Long.valueOf(billOpposerCount).intValue());

            billSupportList.add(billSupport.build());
        });

        return billSupportList;
    }
}
