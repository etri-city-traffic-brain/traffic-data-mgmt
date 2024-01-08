package net.etri.rest.api;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.etri.rest.api.type.XmlType;
import org.springframework.web.multipart.MultipartFile;

import net.etri.rest.api.repository.FileVO;

/**
  <p>클래스 설명 : 사용자정의 컨트롤러의 추상 클래스</p>
  <p>AbstractController</p>
  <pre>
   net.etri.rest.api
          └ AbstractController.java
  </pre>
 **/
public class AbstractController {	
		
	/**
	  FileVO 객체 반환
	  @param uploadFile 업로드된 파일 객체
	  @param xmltype 대상 XML유형
      @return FileVO 업로드된 파일 객체를 파일VO 객체로 반환
	*/	
	protected FileVO getFileVO(MultipartFile uploadFile, XmlType xmltype){
		FileVO vo = new FileVO();
		vo.setFile_name(getUploadFileRename(uploadFile.getOriginalFilename()));
		vo.setFile_size(uploadFile.getSize());
		vo.setReg_date(getCurrentDatetime());
		vo.setXml_type(xmltype);
		return vo;
	}
	/**
	   현재시간 반환	  
      @return String 현재시간(yyyyMMddHHmmss) 문자열 반환
	*/	
	protected String getCurrentDatetime(){
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	/**
	   업로드파일명 반환
	  @param name 업로드된 오리지널 파일명	  
      @return String 변경된 파일명 반환
	*/	
	protected String getUploadFileRename(String name){
		return getCurrentDatetime() +  "_" + name;
	}
	/**
	   업로드파일명을 원복하여 반환
	  @param name 변경된 파일명	  
      @return String 업뢰드된 오리지널 파일명으로 원복
	*/	
	protected String getUploadFileRevers(String name){
		return name.substring(name.indexOf("_") + 1);
	}
	
	protected void CheckDirectory(String path){
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdir();
			return;
		}
		
		if(!dir.isDirectory()){
			dir.mkdir();
			return;
		}
	}
	
	public void alertMessage(HttpServletResponse response , String url , String message) throws IOException{
	  // 인서트 작업, 업데이트 작업, 삭제작업....
	  //utf-8 인코딩
	  response.setContentType("text/html;charset=utf-8");
      java.io.PrintWriter out = response.getWriter();
      out.println("<form name='frm' action='"+url+"' method='post'>");
      out.println("</form>");	      
      out.println("<script> alert('"+ message+"');frm.submit();</script>");
	}
}
