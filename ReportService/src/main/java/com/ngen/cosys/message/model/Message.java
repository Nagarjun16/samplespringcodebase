/**
 * Message Model
 */
package com.ngen.cosys.message.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Message Model
 */
@ApiModel("Message")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Message implements Serializable {
	private static final long serialVersionUID = -8984155479467503516L;
	//
	private String from;
	private String to;
	private String message;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime time;
}
