package org.legislative.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Bill {
    Integer id;
    String title;
    Integer primarySponsor;
}
