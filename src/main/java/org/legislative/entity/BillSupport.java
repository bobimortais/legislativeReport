package org.legislative.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class BillSupport extends Bill {
    Integer supporterCount;
    Integer opposerCount;
    String primarySponsorName;
}
