package org.legislative.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class Bill {
    Integer id;
    String title;
    Integer primarySponsor;
}
