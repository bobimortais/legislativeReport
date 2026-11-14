package org.legislative.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vote {
    Integer id;
    Integer billId;
}
