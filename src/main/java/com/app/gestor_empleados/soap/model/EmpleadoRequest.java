//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v2.3.7 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2025.06.11 a las 09:28:46 PM COT 
//

package com.app.gestor_empleados.soap.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="numeroDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nombres" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="apellidos" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fechaNacimiento" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="fechaVinculacion" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="cargo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="salario" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "empleadoRequest", propOrder = {
    "numeroDocumento",
    "nombres",
    "apellidos",
    "tipoDocumento",
    "fechaNacimiento",
    "fechaVinculacion",
    "cargo",
    "salario"
})
@XmlRootElement(name = "empleadoRequest", namespace = "http://www.gestor-empleados.com/soap")
@Data
public class EmpleadoRequest {

    @XmlElement(required = true)
    private String numeroDocumento;
    @XmlElement(required = true)
    private String nombres;
    @XmlElement(required = true)
    private String apellidos;
    @XmlElement(required = true)
    private String tipoDocumento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar fechaNacimiento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar fechaVinculacion;
    @XmlElement(required = true)
    private String cargo;
    @XmlElement(required = true)
    private Double salario;

}
