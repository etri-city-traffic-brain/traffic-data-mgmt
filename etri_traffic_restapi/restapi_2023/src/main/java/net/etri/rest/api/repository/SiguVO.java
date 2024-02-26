package net.etri.rest.api.repository;

/**
  <p>클래스 설명 : 시구VO 클래스 </p>
  <p>SiguVO</p>
  <pre>
   net.etri.rest.api.repository
          └ SiguVO.java
  </pre>
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
