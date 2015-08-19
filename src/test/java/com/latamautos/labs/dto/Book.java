package com.latamautos.labs.dto;

import com.latamautos.labs.annotation.PossibleValues;
import lombok.Data;

/**
 * Created by andreslopez on 8/6/15.
 */
@Data
public class Book {

    private Long id;

    private Double price;

    private String category;

    @PossibleValues(also = {"name", "email"})
    private String author;

    private String email;

}
