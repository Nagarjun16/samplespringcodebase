package com.ngen.cosys.icms.schema.scheduleFlight;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.icms.validation.xmlValidator;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

/**
 * Model class contains publish header details
 */
@XmlRootElement(name = "publishHeader")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Valid
public class PublishHeader implements Serializable {
 
    private static final long serialVersionUID = 1L;
	@NotNull(message="Message type cannot be null",groups = xmlValidator.class)
    @Size(min = 1, max = 1, message = "Message type must contain 1 character",groups = xmlValidator.class)
    @Pattern(regexp="^[Dd]+",message="Message type must equals to D",groups = xmlValidator.class)
    private String messageType;
    private String sourceSystem;
    private String entityUpdateTime;
    private String messageCreationTime;
    

}
