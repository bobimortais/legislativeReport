package org.legislative.entity;

import lombok.Data;

@Data
public class VoteResult {
    Integer id;
    Integer legislatorId;
    Integer voteId;
    Integer voteType;
}
