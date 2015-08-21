package com.latamautos.tolerant_reader.dto;

import com.latamautos.tolerant_reader.annotation.PossibleValues;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by xavier on 19/8/15.
 */
@Data
@PossibleValues(also = {"autos"})
public class VersionDTO {

    private String id;
    @PossibleValues(also = {"id.evento"})
    private String eventId;
    @PossibleValues(also = {"evento"})
    private String eventType;
    @PossibleValues(also = {"fecha_evento"})
    private String eventDate;
    @PossibleValues(also = {"mercado"})
    private String market;
    @PossibleValues(also = {"resumen"})
    private String summary;
    @PossibleValues(also = {"modelo"})
    private String model;
    @PossibleValues(also = {"marca"})
    private String brand;
    @PossibleValues(also = {"segmento"})
    private String subtype;
    private String version;
    @PossibleValues(also = {"precio_publicado"})
    private String price;
    @PossibleValues(also = {"anio"})
    private int year;
    @PossibleValues(also = {"precio_publicado"})
    private String videoURL;
    @PossibleValues(also = {"foto"})
    private String mainImage;
    @PossibleValues(also = {"motor", "transmision", "dimensiones", "performance", "seguridad", "exterior", "comunicacion", "confort"})
    private List<AttributeDTO> attributes;
    @PossibleValues(also = {"fotos_interior"})
    private List<String> interiorImageURL;
    @PossibleValues(also = {"fotos_exterior"})
    private List<String> exteriorImageURL;


}
