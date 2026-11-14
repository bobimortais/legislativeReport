package org.legislative.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteResult {
    Integer id;
    Integer legislatorId;
    Integer voteId;
    Integer voteType;
}
