package com.latamautos.tolerant_reader.dto;

import com.latamautos.tolerant_reader.annotation.PossibleValues;
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
