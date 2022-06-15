package com.ngen.cosys.icms.schema.scheduleFlight;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.icms.validation.xmlValidator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Model class contains publish entity details
 */
@XmlRootElement(name = "publishEntity")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class PublishEntity implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private String publishID;
    @NotBlank(message="Entity cannot be blank",groups = xmlValidator.class)
    @Size(min = 0, max = 15, message = "Entity values should not exceed 15 characters",groups = xmlValidator.class)
    private String entity;
    @Valid
    @NotNull(message="Publish metaData cannot be null",groups = xmlValidator.class)
    private PublishMetaData publishMetaData;
    @Valid
    @NotNull(message="Publish Data cannot be null",groups = xmlValidator.class)
    private PublishData publishData;

}
