package net.etri.rest.api.cmm;

import org.mybatis.spring.mapper.MapperScannerConfigurer;

/**
  <p>클래스 설명 : Mapper를 등록하기 위한 configurer로 Mapper annotation을 대상으로 scan한다.
  			     추가적으로 sqlSessionFactoryBeanName에 대하여 "sqlSession"을 사용한다.
  </p>
  <p>MapperConfigurer</p>
  <pre>
   net.etri.rest.api.cmm
          └ MapperConfigurer.java
  </pre>
 **/
public class MapperConfigurer extends MapperScannerConfigurer {

	/**
	   기본 정보(anntationClass, sqlSessionFactoryBeanName)으로 설정한다.
	 */
	public MapperConfigurer() {
		setBasePackage("net.etri.rest.api.mapper");
		setAnnotationClass(Mapper.class);
		setSqlSessionTemplateBeanName("sqlSession");
	}
}
