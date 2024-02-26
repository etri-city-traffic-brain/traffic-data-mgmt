package net.etri.rest.api.service;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
  <p>클래스 설명 : 검색 및 페이징VO 기본 클래스</p>
  <p>DefaultVO</p>
  <pre>
   net.etri.rest.api.service
          └ DefaultVO.java
  </pre>
**/
@SuppressWarnings("serial")
@XmlRootElement(name = "default")
@XmlType(propOrder = {"searchCondition", "searchKeyword", "searchUseYn", "pageIndex", "pageUnit", "pageSize", "firstIndex", "lastIndex", "recordCountPerPage"})
@JsonPropertyOrder({"searchCondition", "searchKeyword", "searchUseYn", "pageIndex", "pageUnit", "pageSize", "firstIndex", "lastIndex", "recordCountPerPage"})
public class DefaultVO implements Serializable{

	/** 검색조건 */
	private String searchCondition = "";

	/** 검색Keyword */
	private String searchKeyword = "";

	/** 검색사용여부 */
	private String searchUseYn = "";

	/** 현재페이지 */
	private int pageIndex = 1;

	/** 페이지갯수 */
	private int pageUnit = 10;

	/** 페이지사이즈 */
	private int pageSize = 10;

	/** firstIndex */
	private int firstIndex = 1;

	/** lastIndex */
	private int lastIndex = 1;

	/** recordCountPerPage */
	private int recordCountPerPage = 10;

	/**  Row Number */
	private int row_num = 1;
	
	/**
     @return the searchCondition
	*/
	public String getSearchCondition() {
		return searchCondition;
	}

	/**
     @param searchCondition the searchCondition to set
	*/
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	/**
     @return the searchKeyword
	*/
	public String getSearchKeyword() {
		return searchKeyword;
	}

	/**
  	 @param searchKeyword the searchKeyword to set
	*/
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	/**
     @return the searchUseYn
	*/
	public String getSearchUseYn() {
		return searchUseYn;
	}

	/**
  	 @param searchUseYn the searchUseYn to set
	*/
	public void setSearchUseYn(String searchUseYn) {
		this.searchUseYn = searchUseYn;
	}

	/**
     @return the pageIndex
	*/
	public int getPageIndex() {
		return pageIndex;
	}

	/**
  	 @param pageIndex the pageIndex to set
	*/
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
     @return the pageUnit
	*/
	public int getPageUnit() {
		return pageUnit;
	}

	/**
  	 @param pageUnit the pageUnit to set
	*/
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	/**
     @return the pageSize
	*/
	public int getPageSize() {
		return pageSize;
	}

	/**
  	 @param pageSize the pageSize to set
	*/
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
     @return the firstIndex
	*/
	public int getFirstIndex() {
		return firstIndex;
	}

	/**
  	 @param firstIndex the firstIndex to set
	*/
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	/**
     @return the lastIndex
	*/
	public int getLastIndex() {
		return lastIndex;
	}

	/**
  	 @param lastIndex the lastIndex to set
	*/
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	/**
     @return the recordCountPerPage
	*/
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	/**
  	 @param recordCountPerPage the recordCountPerPage to set
	*/
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
		
	/**
     @return the row_num
	*/
	public int getRow_num() {
		return row_num;
	}

	/**
  	 @param row_num the row_num to set
	*/
	public void setRow_num(int row_num) {
		this.row_num = row_num;
	}
	/**
     @return the page limit
	*/
	public int getPageLimit(){
		return (pageIndex - 1) * pageSize;
	}

	/* (non-Javadoc)
	 @see java.lang.Object#toString()
	*/
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("searchCondition :").append(searchCondition).append(", ");
		sb.append("searchKeyword :").append(searchKeyword).append(", ");
		sb.append("searchUseYn :").append(searchUseYn).append(", ");
		sb.append("pageIndex :").append(pageIndex).append(", ");
		sb.append("pageUnit :").append(pageUnit).append(", ");
		sb.append("pageSize :").append(pageSize).append(", ");
		sb.append("firstIndex :").append(firstIndex).append(", ");
		sb.append("lastIndex :").append(lastIndex).append(", ");
		sb.append("recordCountPerPage :").append(recordCountPerPage).append(", ");
		sb.append("row_num :").append(row_num);
		return sb.toString();
	}
	
}
