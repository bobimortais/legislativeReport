package org.legislative.entity;

import lombok.Data;

@Data
public class Bill {
    Integer id;
    String title;
    Integer primarySponsor;
}
