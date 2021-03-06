/*******************************************************************************
 * Copyright (c) 2021 Coforge PVT LTD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.ngen.cosys.icms.exception;


import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

/**
 *Exception class for message parsing
 */
@Component
@Lazy
public class MessageParseException extends MessagingException {

	/**
	 * SYstem generated serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param message
	 */
	public MessageParseException(Message<Object> message) {
		super(message);
		
	}
	public MessageParseException(String message,Throwable e) {
		super(message,e);
	}
	public MessageParseException(String message) {
		super(message);
	}

}