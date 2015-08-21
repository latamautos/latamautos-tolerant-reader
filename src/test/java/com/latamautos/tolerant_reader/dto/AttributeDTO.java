package com.latamautos.tolerant_reader.dto;

/**
 * Created by xavier on 8/19/15.
 */

import com.latamautos.tolerant_reader.annotation.PossibleValues;
import lombok.Data;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@PossibleValues(also = {"motor", "transmision", "dimensiones", "performance", "seguridad", "exterior", "comunicacion", "confort"})
public class AttributeDTO {
    @PossibleValues(also = {"descripcion"})
    private String description;
    @PossibleValues(also = {"valor"})
    private String value;
}
