package com.latamautos.labs.dto;

import com.latamautos.labs.annotation.PossibleValues;
import lombok.Data;

/**
 * Created by andreslopez on 8/6/15.
 */
@Data
@PossibleValues(also = "parent")
public class ParentDTO {

    private Integer integerObj;

    private int intPrimitive;

    private Long longObj;

    private long longPrimitive;

    private Double dobleObj;

    private double doublePrimitive;

    @PossibleValues(also = {"name", "email"})
    private String stringObj;

    private Boolean booleanObj;

    private boolean booleanPrimitive;

    private ChildDTO childDTO;

}
