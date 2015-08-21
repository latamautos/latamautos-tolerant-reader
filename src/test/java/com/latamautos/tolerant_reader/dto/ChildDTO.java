package com.latamautos.tolerant_reader.dto;

import com.latamautos.tolerant_reader.annotation.PossibleValues;
import lombok.Data;

/**
 * Created by andreslopez on 8/6/15.
 */
@Data
@PossibleValues(also = "child")
public class ChildDTO {

    private String nombre;

    @PossibleValues(also = "activo")
    private boolean esActivo;
}
