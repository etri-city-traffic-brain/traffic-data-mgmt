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

import org.springframework.util.PathMatcher;

/**
 * ExceptionHandlerService 인터페이스.
 * 구현시 run(Exception exception) 만 구현한다.
 */
public interface ExceptionHandlerService {
	/**
	 setPatterns 메소드
	 패키지,클래스 이름으로 패턴등록(Ant형식의 매칭)
  	 @param patterns 패턴리스트
	*/
	public void setPatterns(String[] patterns);
	/**
	 setHandlers 메소드
	 ExceptionHandler 리스트 등록
  	 @param handlers handler리스트
	*/
	public void setHandlers(ExceptionHandler[] handlers);
	/**
	 setPackageName 메소드
	 비교할 클래스 정보 
  	 @param canonicalName 비교할 클래스명
	*/
	public void setPackageName(String canonicalName);
	/**
	 setException 메소드
  	 @param be Exception
	*/
	public void setException(Exception be);
	/**
	 setReqExpMatcher 메소드
  	 @param pm 별도의 PathMatcher
	*/
	public void setReqExpMatcher(PathMatcher pm);
	/**
	 run 메소드 
  	 @param exception 발생한 Exception
     @return boolean 실행성공여부
     @exception Exception
	*/
	public boolean run(Exception exception) throws Exception;
	/**
	 hasReqExpMatcher 메소드
	 PathMatcher 가 있는지 여부 반환
     @return boolean true|false
	*/
	public boolean hasReqExpMatcher();
}
