package com.java.h2database;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Billionaires {

    private long id;
    private String first_name;
    private String last_name;
    private String career;
}
