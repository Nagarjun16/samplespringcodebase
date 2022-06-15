/**
 * Chat Message Model
 */
package com.ngen.cosys.message.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Chat Message Model
 */
@ApiModel("Chat Message")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatMessage extends Message {
	private static final long serialVersionUID = 3224336005846843360L;
	//
	private String type;
	private String chatId;
    private Object detail;
    private long flightId;
    private long flightSegmentOrder;
}
