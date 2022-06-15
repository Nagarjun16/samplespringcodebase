/**
 * Notification Message Model
 */
package com.ngen.cosys.message.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Notification Message Model
 */
@ApiModel("Notification Message")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NotificationMessage extends Message {
	private static final long serialVersionUID = 5285006939562434817L;
	//
	private String type;
}
