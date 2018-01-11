/*******************************************************************************
 * Copyright (c) 2013-2015 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Thierry Monteil (Project co-founder) - Management and initial specification,
 *         conception and documentation.
 *     Mahdi Ben Alaya (Project co-founder) - Management and initial specification,
 *         conception, implementation, test and documentation.
 *     Christophe Chassot - Management and initial specification.
 *     Khalil Drira - Management and initial specification.
 *     Yassine Banouar - Initial specification, conception, implementation, test
 *         and documentation.
 *     Guillaume Garzone - Conception, implementation, test and documentation.
 *     Francois Aissaoui - Conception, implementation, test and documentation.
 ******************************************************************************/
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2013.07.08 at 01:02:29 PM CEST
//


package org.eclipse.om2m.commons.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;



/**
 * <p>Java Class for TrpdtType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TrpdtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="tolerableDelay" type="{http://www.w3.org/2001/XMLSchema}duration" minOccurs="0"/>
 *         &lt;element name="tolerableTime" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrpdtType", propOrder = {
        "tolerableDelay",
        "tolerableTime"
})
public class TrpdtType {

    @XmlElement(namespace = "")
    protected Duration tolerableDelay;
    @XmlElement(namespace = "")
    @XmlSchemaType(name = "time")
    protected String tolerableTime;

    /**
     * Gets the value of the property tolerableDelay.
     *
     * @return
     *     possible object is
     *     {@link Duration }
     *
     */
    public Duration getTolerableDelay() {
        return tolerableDelay;
    }

    /**
     * Sets the value of the property tolerableDelay.
     *
     * @param value
     *     allowed object is
     *     {@link Duration }
     *
     */
    public void setTolerableDelay(Duration value) {
        this.tolerableDelay = value;
    }

    /**
     * Gets the value of the property tolerableTime.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTolerableTime() {
        return tolerableTime;
    }

    /**
     * Sets the value of the property tolerableTime.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTolerableTime(String value) {
        this.tolerableTime = value;
    }

}
