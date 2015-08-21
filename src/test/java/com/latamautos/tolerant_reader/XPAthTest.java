package com.latamautos.tolerant_reader;

import com.google.gson.Gson;
import com.latamautos.tolerant_reader.dto.ResultDTO;
import com.latamautos.tolerant_reader.dto.VersionDTO;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;

import org.eclipse.persistence.jaxb.JAXBContextFactory;

/**
 * Created by xavier on 8/19/15.
 */
public class XPathTest {

    private String json;

    @Test
    public void convertJsonToXML() throws JAXBException {
        JSONObject jsonObject = new JSONObject(json);
        String xml = XML.toString(jsonObject);
        System.out.println(xml);
//        JAXBContext jc = JAXBContext.newInstance(ResultDTO.class);
            JAXBContext jc =  JAXBContextFactory.createContext(new Class[]{ResultDTO.class}, null);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        ResultDTO response = (ResultDTO) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        System.out.println(response);
    }

    @Before
    public void setUp() throws Exception {
        json = "{\"root\":{\n" +
                "  \"paging\": {\n" +
                "    \"total\": 123244,\n" +
                "    \"offset\": 0,\n" +
                "    \"limit\": 100\n" +
                "  },\n" +
                "  \"autos\":[\n" +
                "    {\n" +
                "      \"id.evento\":\"codigo.alfanumerico\",\n" +
                "      \"evento\":\"update\",      \n" +
                "      \"id\":\"codigo.alfanumerico\",\n" +
                "      \"fecha_evento\":\"2015-08-18T17:29:56Z\",\n" +
                "      \"mercado\":\"AR\",\n" +
                "      \"pais\":\"ARGENTINA\",\n" +
                "      \"foto\":\"m_chevroletcruzesedan.jpg\",\n" +
                "      \"video\":\"link_youtube\",\n" +
                "      \"segmento\":\"Sedan\",\n" +
                "      \"marca\":\"Chevrolet\",\n" +
                "      \"modelo\":\"Cruze\",\n" +
                "      \"version\":\"1.8N LT\",\n" +
                "      \"anio\":\"2014\",\n" +
                "      \"resumen\":\"El Chevrolet Cruze llega  a la Argentina importado desde Corea, con dos carrocerias y dos motorizaciones se posiciona en el segmento Medio con los vehiculos nacionales como competencia. Con diseño sobrio de exterior, la comodidad  y confort interior se hacen presentes.\",\n" +
                "      \"precio_publicado\":\"$290.000,00\",\n" +
                "      \"precio_ars\":\"$290.000,00\",\n" +
                "      \"garantia\":{\n" +
                "        \"anios\":2,\n" +
                "        \"kms\":100.00\n" +
                "      },\n" +
                "      \"motor\":[\n" +
                "        {\"descripcion\":\"Cilindrada\",\"valor\":\"1796 cc\"},\n" +
                "        {\"descripcion\":\"Válvulas\",\"valor\":\"16\"},\n" +
                "        {\"descripcion\":\"Potencia\",\"valor\":\"138 cv\"},\n" +
                "        {\"descripcion\":\"Torque\",\"valor\":\"174/3800 nm/rpm\"},\n" +
                "        {\"descripcion\":\"Cilindros\",\"valor\":\"4\"},\n" +
                "        {\"descripcion\":\"Posición\",\"valor\":\"Delantera\"},\n" +
                "        {\"descripcion\":\"Motor\",\"valor\":\"1.8L\"},\n" +
                "        {\"descripcion\":\"Alimentación\",\"valor\":\"1796 cc\"},\n" +
                "        {\"descripcion\":\"Combustible\",\"valor\":\"Gasolina\"},\n" +
                "        {\"descripcion\":\"Transmisión y Chasis\",\"valor\":\"Automática\"}\n" +
                "      ],\n" +
                "      \"transmision\":[\n" +
                "        {\"descripcion\":\"Marchas\",\"valor\":\"6\"},\n" +
                "        {\"descripcion\":\"Tracción\",\"valor\":\"Delantera\"},\n" +
                "        {\"descripcion\":\"Frenos delanteros\",\"valor\":\"Tambor\"},\n" +
                "        {\"descripcion\":\"Frenos traseros\",\"valor\":\"Tambor\"},\n" +
                "        {\"descripcion\":\"Suspensión delantera\",\"valor\":\"Independiente McPherson\"},\n" +
                "        {\"descripcion\":\"Suspensión trasera\",\"valor\":\"Semi-independiente con barra de torsión\"},\n" +
                "        {\"descripcion\":\"Neumáticos\",\"valor\":\"215/50 R17\"}\n" +
                "      ],\n" +
                "      \"dimensiones\":[\n" +
                "        {\"descripcion\":\"Largo / Ancho / Alto / Distancia entre ejes\",\"valor\":\"4597/1788/1477/2685 mm\"},\n" +
                "        {\"descripcion\":\"Filas de asientos\",\"valor\":\"2\"},\n" +
                "        {\"descripcion\":\"Tanque de combustible\",\"valor\":\"60 lts\"},\n" +
                "        {\"descripcion\":\"Peso\",\"valor\":\"1434 kg\"},\n" +
                "        {\"descripcion\":\"Baúl\",\"valor\":\"450 dm3\"},\n" +
                "        {\"descripcion\":\"Puertas\",\"valor\":\"4\"},\n" +
                "        {\"descripcion\":\"Plazas\",\"valor\":\"5\"}\n" +
                "      ],\n" +
                "      \"performance\":[\n" +
                "        {\"descripcion\":\"Consumo en ciudad\",\"valor\":\"10.6 lts / 100 kms\"},\n" +
                "        {\"descripcion\":\"Consumo en ruta\",\"valor\":\"6.7 lts / 100 kms\"}\n" +
                "      ],\n" +
                "      \"seguridad\":[\n" +
                "        {\"descripcion\":\"Cinturones inerciales en todas las plazas\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Sistema de frenos ABS (Antibloqueo)\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Desempañador de espejos exteriores\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Cinturones inerciales en cuatro plazas\",\"valor\":\"-\"},\n" +
                "        {\"descripcion\":\"Airbags (2) para conductor y acompañante\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Ganchos ISOFIX para ajustar silla de niños\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Airbags (6) delanteros, laterales delanteros y de cortina\",\"valor\":\"-\"},\n" +
                "        {\"descripcion\":\"Faros antiniebla delanteros\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Tercer apoyacabezas trasero\",\"valor\":\"SI\"}\n" +
                "      ],\n" +
                "      \"exterior\":[\n" +
                "        {\"descripcion\":\"Llantas de aleación liviana\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Espejos color carrocería\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Molduras color carrocería\",\"valor\":\"SI\"}\n" +
                "      ],\n" +
                "      \"comunicacion\":[\n" +
                "        {\"descripcion\":\"Reproductor de radio AM/FM con cargador para CD\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Pantalla delantera multifunción\",\"valor\":\"-\"},\n" +
                "        {\"descripcion\":\"Conexión auxiliar\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Reproductor Mp3\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Bluetooth\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Seis parlantes\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Puerto USB\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Cuatro parlantes\",\"valor\":\"SI\"}\n" +
                "      ],\n" +
                "      \"confort\":[\n" +
                "        {\"descripcion\":\"Apoyabrazo trasero\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Apoyabrazos central de butacas delanteras\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Encendido automático de luces\",\"valor\":\"-\"},\n" +
                "        {\"descripcion\":\"Alzacristales eléctricos en todas las ventanas\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Climatizador automático\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Dirección asistida\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Cierre centralizado de puertas por comando a distancia\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Apertura del baúl a distancia\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Computadora de abordo\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Espejo interior antideslumbrante día/noche automático\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Espejos exteriores eléctricos\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Boton (start/stop) de encendido de motor sin llave.\",\"valor\":\"-\"},\n" +
                "        {\"descripcion\":\"Asientos traseros rebatibles 60/40\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Control de velocidad crucero\",\"valor\":\"-\"},\n" +
                "        {\"descripcion\":\"Volante recubierto en cuero\",\"valor\":\"-\"},\n" +
                "        {\"descripcion\":\"Volante con ajuste vertical\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Palanca revestida en cuero\",\"valor\":\"-\"},\n" +
                "        {\"descripcion\":\"Tapizado de cuero\",\"valor\":\"-\"},\n" +
                "        {\"descripcion\":\"Inserciones en aluminio\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Toma corriente de 12v\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Llave de reconocimiento a distancia al conductor\",\"valor\":\"-\"},\n" +
                "        {\"descripcion\":\"Mandos de sistema de audio desde el volante\",\"valor\":\"-\"},\n" +
                "        {\"descripcion\":\"Techo corredizo delantero\",\"valor\":\"-\"},\n" +
                "        {\"descripcion\":\"Alarma e inmovilizador de motor\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Luneta térmica\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Sistema One Touch en ventana conductor\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Butaca del conductor regulable en altura manualmente\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Vidrios tonalizados\",\"valor\":\"SI\"},\n" +
                "        {\"descripcion\":\"Volante con ajuste vertical y profundidad\",\"valor\":\"-\"},\n" +
                "        {\"descripcion\":\"Portavasos delanteros\",\"valor\":\"-\"}\n" +
                "      ],\n" +
                "      \"fotos_interior\":[\n" +
                "        \"i1_chevroletcruzesedan.jpg\",\n" +
                "        \"i2_chevroletcruzesedan.jpg\",\n" +
                "        \"i3_chevroletcruzesedan.jpg\",\n" +
                "        \"i4_chevroletcruzesedan.jpg\"\n" +
                "      ],\n" +
                "      \"fotos_exterior\":[\n" +
                "        \"e1_chevroletcruzesedan.jpg\",\n" +
                "        \"e2_chevroletcruzesedan.jpg\",\n" +
                "        \"e3_chevroletcruzesedan.jpg\",\n" +
                "        \"e4_chevroletcruzesedan.jpg\"\n" +
                "      ]\n" +
                "    }\n" +
                "  ]  \n" +
                "}}}";

    }
}
