/*
 * Copyright 2008-2009 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.etri.rest.api.except;

import java.text.MessageFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

/**
 * BaseException 은 EgovBizException의 상위클래스이다.  * 
 * 
 * <p>NOTE: Exception Handling 상의 BaseException 은 
 * EgovBizException, RuntimeException, FdlException을 제외한
 * 나머지 Exception 이 발생하는 경우 BaseException 을 바뀌도록 되어 있다.
 *
 */
@SuppressWarnings("serial")
public class BaseException extends Exception {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	protected String message = null;
	protected String messageKey = null;
	protected Object[] messageParameters = null;
	protected Exception wrappedException = null;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public Object[] getMessageParameters() {
		return messageParameters;
	}

	public void setMessageParameters(Object[] messageParameters) {
		this.messageParameters = messageParameters;
	}

	public Throwable getWrappedException() {
		return wrappedException;
	}

	public void setWrappedException(Exception wrappedException) {
		this.wrappedException = wrappedException;
	}

	public BaseException() {
		this("BaseException without message", null, null);
	}

	/**
	 BaseException 생성자 
     @param defaultMessage 메세지 지정
	*/
	public BaseException(String defaultMessage) {
		this(defaultMessage, null, null);
	}
	/**
	 BaseException 생성자 
  	 @param wrappedException 발생한 Exception 내포함.
	*/
	public BaseException(Throwable wrappedException) {
		this("BaseException without message", null, wrappedException);
	}

	/**
	 BaseException 생성자 
  	 @param defaultMessage 메세지 지정
  	 @param wrappedException 발생한 Exception 내포함.
	*/
	public BaseException(String defaultMessage, Throwable wrappedException) {
		this(defaultMessage, null, wrappedException);
	}

	/**
	 BaseException 생성자 
  	 @param defaultMessage 메세지 지정(변수지정)
  	 @param messageParameters 치환될 메세지 리스트
  	 @param wrappedException 발생한 Exception 내포함.
	*/
	public BaseException(String defaultMessage, Object[] messageParameters, Throwable wrappedException) {
		super(wrappedException);

		String userMessage = defaultMessage;
		if (messageParameters != null) {
			userMessage = MessageFormat.format(defaultMessage, messageParameters);
		}
		this.message = userMessage;

	}

	/**
	 BaseException 생성자 
  	 @param messageSource 메세지 리소스
  	 @param messageKey 메세지키값
	*/
	public BaseException(MessageSource messageSource, String messageKey) {
		this(messageSource, messageKey, null, null, Locale.getDefault(), null);
	}

	/**
	 BaseException 생성자 
  	 @param messageSource 메세지 리소스
  	 @param messageKey 메세지키값
  	 @param wrappedException
	*/
	public BaseException(MessageSource messageSource, String messageKey, Throwable wrappedException) {
		this(messageSource, messageKey, null, null, Locale.getDefault(), wrappedException);
	}
	/**
	 BaseException 생성자 
  	 @param messageSource 메세지 리소스
  	 @param messageKey 메세지키값
  	 @param locale 국가/언어지정
  	 @param wrappedException 발생한 Exception 내포함.
	*/
	public BaseException(MessageSource messageSource, String messageKey, Locale locale, Throwable wrappedException) {
		this(messageSource, messageKey, null, null, locale, wrappedException);
	}
	/**
	 BaseException 생성자 
  	 @param messageSource 메세지 리소스
  	 @param messageKey 메세지키값
  	 @param messageParameters 치환될 메세지 리스트
  	 @param locale 국가/언어지정
  	 @param wrappedException 발생한 Exception 내포함.
	*/
	public BaseException(MessageSource messageSource, String messageKey, Object[] messageParameters, Locale locale,
	        Throwable wrappedException) {
		this(messageSource, messageKey, messageParameters, null, locale, wrappedException);
	}
	/**
	 BaseException 생성자 
  	 @param messageSource 메세지 리소스
  	 @param messageKey 메세지키값
  	 @param messageParameters 치환될 메세지 리스트
  	 @param wrappedException 발생한 Exception 내포함.
	*/
	public BaseException(MessageSource messageSource, String messageKey, Object[] messageParameters,
	        Throwable wrappedException) {
		this(messageSource, messageKey, messageParameters, null, Locale.getDefault(), wrappedException);
	}
	/**
	 BaseException 생성자 
  	 @param messageSource 메세지 리소스
  	 @param messageKey 메세지키값
  	 @param messageParameters 치환될 메세지 리스트
  	 @param defaultMessage 메세지 지정(변수지정)
  	 @param wrappedException 발생한 Exception 내포함.
	*/
	public BaseException(MessageSource messageSource, String messageKey, Object[] messageParameters,
	        String defaultMessage, Throwable wrappedException) {
		this(messageSource, messageKey, messageParameters, defaultMessage, Locale.getDefault(), wrappedException);
	}
	/**
	 BaseException 생성자 
  	 @param messageSource 메세지 리소스
  	 @param messageKey 메세지키값
  	 @param messageParameters 치환될 메세지 리스트
  	 @param defaultMessage 메세지 지정(변수지정)
  	 @param locale 국가/언어지정
  	 @param wrappedException 발생한 Exception 내포함.
	*/
	public BaseException(MessageSource messageSource, String messageKey, Object[] messageParameters,
	        String defaultMessage, Locale locale, Throwable wrappedException) {
		super(wrappedException);

		this.messageKey = messageKey;
		this.messageParameters = messageParameters;
		this.message = messageSource.getMessage(messageKey, messageParameters, defaultMessage, locale);

	}

}
