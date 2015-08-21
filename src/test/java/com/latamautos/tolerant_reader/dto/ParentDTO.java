package com.latamautos.tolerant_reader.dto;

import com.latamautos.tolerant_reader.annotation.PossibleValues;
import lombok.Data;

import java.util.List;

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

    @PossibleValues(also = "motor")
    private List<ChildDTO> childDTO;

}
