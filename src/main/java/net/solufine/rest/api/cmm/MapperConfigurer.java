/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.cmm
 * @file MapperConfigurer.java
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

import org.mybatis.spring.mapper.MapperScannerConfigurer;

/**
  <p>클래스 설명 : Mapper를 등록하기 위한 configurer로 Mapper annotation을 대상으로 scan한다.
  			     추가적으로 sqlSessionFactoryBeanName에 대하여 "sqlSession"을 사용한다.
  </p>
  <p>MapperConfigurer</p>
  <pre>
   net.solufine.rest.api.cmm
          └ MapperConfigurer.java
  </pre>
  @author  redkaras 
  @since  2018. 5. 23.
  @version 1.0
 **/
public class MapperConfigurer extends MapperScannerConfigurer {

	/**
	   기본 정보(anntationClass, sqlSessionFactoryBeanName)으로 설정한다.
	 */
	public MapperConfigurer() {
		setBasePackage("net.solufine.rest.api.mapper");
		setAnnotationClass(Mapper.class);
		setSqlSessionTemplateBeanName("sqlSession");
	}
}
