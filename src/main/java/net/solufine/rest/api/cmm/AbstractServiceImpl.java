/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.cmm
 * @file AbstractServiceImpl.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일				수정자			수정내용
 * -------------	------		-------------------------------
 * 2018. 5. 23.		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.cmm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  <p>클래스 설명 : 사용자정의 서비스 추상 클래스. 비즈니스 서비스 구현체가 상속받는 추상추상클래스</p>
  <p>AbstractServiceImpl</p>
  <pre>
   net.solufine.rest.api.cmm
          └ AbstractServiceImpl.java
  </pre>
  @author  redkaras 
  @since  2018. 5. 23.
  @version 1.0
 **/
public abstract class AbstractServiceImpl {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
}
