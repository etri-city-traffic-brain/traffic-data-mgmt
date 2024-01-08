package net.etri.rest.api.repository;

/**
  <p>클래스 설명 : 코드정보VO 클래스</p>
  <p>CodeVO</p>
  <pre>
   net.etri.rest.api.repository
          └ CodeVO.java
  </pre>
**/
public class CodeVO {

	private String cd_group = null;
	private String cd_id = null; 
	private String cd_nm = null;
	private String remark = null;
	
	/**
	 @return the cd_group
	*/
	public String getCd_group() {
		return cd_group;
	}
	/**
	 @param cd_group the cd_group to set
	*/
	public void setCd_group(String cd_group) {
		this.cd_group = cd_group;
	}
	/**
	 @return the cd_id
	*/
	public String getCd_id() {
		return cd_id;
	}
	/**
	 @param cd_id the cd_id to set
	*/
	public void setCd_id(String cd_id) {
		this.cd_id = cd_id;
	}
	/**
	 @return the cd_nm
	*/
	public String getCd_nm() {
		return cd_nm;
	}
	/**
	 @param cd_nm the cd_nm to set
	*/
	public void setCd_nm(String cd_nm) {
		this.cd_nm = cd_nm;
	}
	/**
	 @return the remark
	*/
	public String getRemark() {
		return remark;
	}
	/**
	 @param remark the remark to set
	*/
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("cd_group :").append(cd_group).append(", ");
		sb.append("cd_id :").append(cd_id).append(", ");
		sb.append("cd_nm :").append(cd_nm).append(", ");
		sb.append("remark :").append(remark);
		return sb.toString();
	}
}