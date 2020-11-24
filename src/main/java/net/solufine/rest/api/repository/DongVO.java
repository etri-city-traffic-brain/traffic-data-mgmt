/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.repository
 * @file DongVO.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 7. 3.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.repository;

import net.solufine.rest.api.service.DefaultVO;

/**
  <p>클래스 설명 : 동코드VO 클래스</p>
  <p>DongVO</p>
  <pre>
   net.solufine.rest.api.repository
          └ DongVO.java
  </pre>
  @author  redkaras 
  @since  2018. 7. 3.
  @version 1.0
 **/
@SuppressWarnings("serial")
public class DongVO extends DefaultVO {
	private String sigu_cd;
	private String dong_cd;
	private String sido_nm;
	private String sigu_nm;
	private String dong_nm;
	
	/**
     @return the sigu_cd
	*/
	public String getSigu_cd() {
		return sigu_cd;
	}
	/**
  	 @param sigu_cd the sigu_cd to set
	*/
	public void setSigu_cd(String sigu_cd) {
		this.sigu_cd = sigu_cd;
	}
	/**
     @return the dong_cd
	*/
	public String getDong_cd() {
		return dong_cd;
	}
	/**
  	 @param dong_cd the dong_cd to set
	*/
	public void setDong_cd(String dong_cd) {
		this.dong_cd = dong_cd;
	}
	/**
     @return the sido_nm
	*/
	public String getSido_nm() {
		return sido_nm;
	}
	/**
  	 @param sido_nm the sido_nm to set
	*/
	public void setSido_nm(String sido_nm) {
		this.sido_nm = sido_nm;
	}
	/**
     @return the sigu_nm
	*/
	public String getSigu_nm() {
		return sigu_nm;
	}
	/**
  	 @param sigu_nm the sigu_nm to set
	*/
	public void setSigu_nm(String sigu_nm) {
		this.sigu_nm = sigu_nm;
	}	
	/**
     @return the dong_nm
	*/
	public String getDong_nm() {
		return dong_nm;
	}
	/**
  	 @param dong_nm the dong_nm to set
	*/
	public void setDong_nm(String dong_nm) {
		this.dong_nm = dong_nm;
	}
	/* (non-Javadoc)
	 @see java.lang.Object#toString()
	*/
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("sigu_cd :").append(sigu_cd).append(", ");
		sb.append("dong_cd :").append(dong_cd).append(", ");
		sb.append("sido_nm :").append(sido_nm).append(", ");
		sb.append("sigu_nm :").append(sigu_nm).append(", ");
		sb.append("dong_nm :").append(dong_nm);
		return sb.toString();
	}
	
	
}
