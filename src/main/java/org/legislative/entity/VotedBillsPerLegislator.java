package org.legislative.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotedBillsPerLegislator {
    Integer legislatorId;
    String name;
    Integer numberOfSupportedBills;
    Integer numberOfOpposedBills;
}
