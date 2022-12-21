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

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.util.PathMatcher;

/**
 * AbsExceptionHandleManager
 * 사용자 ExceptionHandleManager 구현시 상속되는 추상클래스이다.
 * ExceptionHandlerService 인터페이스의 메소드를 거의 다 구현해 놓은 추상클래스이기 때문에 사용자가 구현시
 * run(Exception exception) 만 구현을 해주면 된다.
 * 
 */
public abstract class AbsExceptionHandleManager {

	protected Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "messageSource")
	protected MessageSource messageSource;

	protected Exception ex;
	protected String thisPackageName;
	protected String[] patterns;
	protected ExceptionHandler[] handlers;
	protected PathMatcher pm;

	/**
	  setPatterns 메소드
	  
	  패키지,클래스 이름으로 패턴등록(Ant형식의 매칭)
  	 @param patterns 패턴리스트
	*/
	public void setPatterns(String[] patterns) {
		this.patterns = patterns;
	}

	/**
	  setHandlers 메소드
	  ExceptionHandler 리스트 등록
  	 @param handlers handler리스트
	*/
	public void setHandlers(ExceptionHandler[] handlers) {
		this.handlers = handlers;
	}
	/**
	 setPackageName 메소드
	  비교할 클래스 정보 
	  
  	 @param canonicalName 비교할 클래스명
	*/
	public void setPackageName(String canonicalName) {
		this.thisPackageName = canonicalName;
	}

	public String getPackageName() {
		return this.thisPackageName;
	}
	/**
	 setException 메소드	  
  	 @param be Exception
	*/
	public void setException(Exception be) {
		this.ex = be;
	}

	/**
	 setReqExpMatcher 메소드
  	 @param pm 별도의 PathMatcher
	*/
	public void setReqExpMatcher(PathMatcher pm) {
		this.pm = pm;
	}

	/**
	 hasReqExpMatcher 메소드
	 PathMatcher 가 있는지 여부 반환
     @return boolean true|false 
	*/
	public boolean hasReqExpMatcher() {
		return this.enableMatcher();
	}

	/**
	 enableMatcher 메소드
	 PathMatcher 가 있는지 여부 반환
     @return boolean true|false 
	*/
	public boolean enableMatcher() {
		return (this.pm == null) ? false : true;
	}

	/**
	 run 메소드
	  상속받아 구현해야할 메스드 하지만 미리구현은 먼저 해둠. 실 구현체에서 override 하여 구현해야 함.
  	 @param exception 발생한 Exception
     @return boolean 실행성공여부 
     @exception Exception
	*/
	public boolean run(Exception exception) throws Exception {

		if (!enableMatcher())
			return false;

		for (String pattern : patterns) {
			if (pm.match(pattern, thisPackageName)) {
				for (ExceptionHandler eh : handlers) {
					eh.occur(exception, getPackageName());
				}
				break;
			}
		}

		return true;
	}

}