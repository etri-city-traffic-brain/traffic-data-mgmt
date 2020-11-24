/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 * 
 * @project rest.api
 * @package net.solufine.rest.api.type
 * @file RequestTypeInterface.java
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 * 	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2019. 4. 30.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.type;

/**
  <p>클래스 설명 : </p>
  <p>RequestTypeInterface</p>
  <pre>
   net.solufine.rest.api.type
          └ RequestTypeInterface.java
  </pre>
  @author  redkaras 
  @since  2019. 4. 30.
  @version 1.0
**/
public interface RequestTypeInterface {
	/**
	 요청유형에 대한 URL
	 */
	public String URL();
}
