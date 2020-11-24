/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.repository
 * @file SiguVO.java
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

/**
  <p>클래스 설명 : 시구VO 클래스 </p>
  <p>SiguVO</p>
  <pre>
   net.solufine.rest.api.repository
          └ SiguVO.java
  </pre>
  @author  redkaras 
  @since  2018. 7. 3.
  @version 1.0
**/
public class SiguVO {
	
	private String sigu_cd;
	private String sido_nm;
	private String sigu_nm;
	 
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("sigu_cd :").append(sigu_cd).append(", ");
		sb.append("sido_nm :").append(sido_nm).append(", ");
		sb.append("sigu_nm :").append(sigu_nm);
		return sb.toString();
	}
}
