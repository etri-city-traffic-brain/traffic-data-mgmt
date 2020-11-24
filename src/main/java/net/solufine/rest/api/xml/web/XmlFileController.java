/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 * 
 * @project rest.api
 * @package net.solufine.rest.api.xml.web
 * @file XmlFileController.java
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 * 	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2019. 4. 30.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.solufine.rest.api.AbstractController;
import net.solufine.rest.api.repository.FileVO;
import net.solufine.rest.api.repository.RequestVO;
import net.solufine.rest.api.type.IncludeType;
import net.solufine.rest.api.type.RequestType;
import net.solufine.rest.api.type.RouteType;
import net.solufine.rest.api.type.XmlType;
import net.solufine.rest.api.xml.Connections;
import net.solufine.rest.api.xml.Edges;
import net.solufine.rest.api.xml.Nodes;
import net.solufine.rest.api.xml.TlLogic;
import net.solufine.rest.api.xml.TlLogics;
import net.solufine.rest.api.xml.XmlController;
import net.solufine.rest.api.xml.XmlManager;
import net.solufine.rest.api.xml.service.XmlService;

/**
  <p>클래스 설명 : </p>
  <p>XmlFileController</p>
  <pre>
   net.solufine.rest.api.xml.web
          └ XmlFileController.java
  </pre>
  @author  redkaras 
  @since  2019. 4. 30.
  @version 1.0
**/
@RestController
public class XmlFileController extends AbstractController {

Logger log = LoggerFactory.getLogger(XmlController.class);
	
	@Value("#{props['upload.store.path']}")
	private String storePath;
	@Value("#{props['download.store.path']}")
	private String downPath;
	
	@Resource(name="xmlManager")
	private XmlManager manager;
	
	@Resource(name="xmlService")
	private XmlService service;
	
	@RequestMapping(value="/upNodeXml")
	public void upNodeXml(MultipartFile uploadFile, HttpServletResponse response) throws IOException {
		if(uploadFile == null ){ response.getWriter().write("The upload file is an Null. [ Fail ]"); return;}
		if(uploadFile.isEmpty() ){ response.getWriter().write("The upload file is an empty file. [ Fail ]"); return;}
		if( !uploadFile.getContentType().equals("text/xml") ){ response.getWriter().write("The upload file is not an XML file. [ Fail ]"); return; }
	
		CheckDirectory(storePath);
		FileVO vo = getFileVO(uploadFile, XmlType.NODE);		
		File xml = new File( storePath + vo.getFile_name());
		uploadFile.transferTo(xml);
		Nodes nodes;
		try {
			nodes = manager.XmlToNode(xml);
			xml.delete();
		} catch (JAXBException e) {
			response.getWriter().write( e.getMessage() + " [ Fail ]"); 
			return;
		}	
		
		if(service.updateNode(nodes.getNode()) != -1){
			service.insertUploadHistory(vo);
		}
		
		response.getWriter().write("Success");
    }
	
	@RequestMapping(value="/downNodeXml")
	public void downNodeXml(HttpServletResponse response) throws IOException, JAXBException {
		Nodes nodes = new Nodes();
		RequestVO vo = new RequestVO();
		vo.setRequestType(RequestType.UNKONWN);
		nodes.setNode( service.selectNode(vo) );
		manager.NodeXmlResponse( nodes, response ); 
	}
	
	@RequestMapping(value="/downEdgeXml")
	public void downEdgeXml(HttpServletResponse response) throws IOException, JAXBException {
		Edges edges = new Edges();
		edges.setEdge(service.selectEdge(null));		
		manager.EdgeXmlResponse(edges, response ); 
	}
	/*
	@RequestMapping(value="/downConXml")
	public void downConXml(HttpServletResponse response) throws IOException, JAXBException {
		Connections cons = new Connections();
		cons.setCon(service.selectConnection(null));		
		manager.ConXmlResponse(cons, response ); 
	}
	
	@RequestMapping(value="/downTlXml")
	public void downTlXml(HttpServletResponse response) throws IOException, JAXBException {
		TlLogics tls = new TlLogics();
		tls.setTlLogics(service.selectTlLogic(null));	
		if(tls.getTlLogics() != null && !tls.getTlLogics().isEmpty()){
			tls.setCon(service.selectConnection3(tls.getTlLogics()));
		}
		manager.TlLogicXmlResponse(tls, response ); 
	}
	*/
	@RequestMapping(value="/upEdgeXml")
	public void upEdgeXml(MultipartFile uploadFile, HttpServletResponse response) throws IOException {
		if(uploadFile == null ){ response.getWriter().write("The upload file is an Null. [ Fail ]"); return;}
		if(uploadFile.isEmpty() ){ response.getWriter().write("The upload file is an empty file. [ Fail ]"); return;}
		if( !uploadFile.getContentType().equals("text/xml") ){ response.getWriter().write("The upload file is not an XML file. [ Fail ]"); return; }
		
		CheckDirectory(storePath);
		
		FileVO vo = getFileVO(uploadFile, XmlType.EDGE);				
		File xml = new File( storePath + vo.getFile_name() );
		uploadFile.transferTo(xml);
		Edges edge;
		try {
			edge = manager.XmlToEdge(xml);
			xml.delete();
		} catch (JAXBException e) {
			response.getWriter().write( e.getMessage() + " [ Fail ]"); 
			return;
		}	
		
		if(service.updateEdge(edge.getEdge()) != -1){
			service.insertUploadHistory(vo);
		}
		response.getWriter().write("Success");
    }
	
	@RequestMapping(value="/upConXml")
	public void upConXml(MultipartFile uploadFile, HttpServletResponse response) throws IOException {
		if(uploadFile == null ){ response.getWriter().write("The upload file is an Null. [ Fail ]"); return;}
		if(uploadFile.isEmpty() ){ response.getWriter().write("The upload file is an empty file. [ Fail ]"); return;}
		if( !uploadFile.getContentType().equals("text/xml") ){ response.getWriter().write("The upload file is not an XML file. [ Fail ]"); return; }

		CheckDirectory(storePath);
		
		FileVO vo = getFileVO(uploadFile, XmlType.CONNECTION);
		File xml = new File( storePath + vo.getFile_name() );
		uploadFile.transferTo(xml);
		Connections con;
		try {
			con = manager.XmlToCon(xml);
			xml.delete();
		} catch (JAXBException e) {
			response.getWriter().write( e.getMessage() + " [ Fail ]"); 
			return;
		}	
		
		if(service.updateConnection(con.getCon()) != -1){
			service.insertUploadHistory(vo);
		}
		response.getWriter().write("Success");
    }
	
	@RequestMapping(value="/upTlXml")
	public void upTlXml(MultipartFile uploadFile, HttpServletResponse response) throws IOException {
		if(uploadFile == null ){ response.getWriter().write("The upload file is an Null. [ Fail ]"); return;}
		if(uploadFile.isEmpty() ){ response.getWriter().write("The upload file is an empty file. [ Fail ]"); return;}
		if( !uploadFile.getContentType().equals("text/xml") ){ response.getWriter().write("The upload file is not an XML file. [ Fail ]"); return; }
		
		CheckDirectory(storePath);
		
		FileVO vo = getFileVO(uploadFile, XmlType.TLLOGIC);
		File xml = new File( storePath + vo.getFile_name() );
		uploadFile.transferTo(xml);
		TlLogics tls;
		try {
			tls = manager.XmlToTl(xml);
			xml.delete();
		} catch (JAXBException e) {
			response.getWriter().write( e.getMessage() + " [ Fail ]"); 
			return;
		}	
		
		if(service.updatetTlLogic(tls) != -1){
			service.insertUploadHistory(vo);
		}
		response.getWriter().write("Success");
    }
	
	@RequestMapping(value="/filterTlXml")
	public void filterTlXml(MultipartFile uploadFile, HttpServletResponse response) throws IOException, JAXBException {
		if(uploadFile == null ){ response.getWriter().write("The upload file is an Null. [ Fail ]"); return;}
		if(uploadFile.isEmpty() ){ response.getWriter().write("The upload file is an empty file. [ Fail ]"); return;}
		if( !uploadFile.getContentType().equals("text/xml") ){ response.getWriter().write("The upload file is not an XML file. [ Fail ]"); return; }
		
		CheckDirectory(storePath);
		
		FileVO vo = getFileVO(uploadFile, XmlType.TLLOGIC);
		File xml = new File( storePath + vo.getFile_name() );
		uploadFile.transferTo(xml);
		TlLogics tls;
		try {
			tls = manager.XmlToTl(xml);
			xml.delete();
		} catch (JAXBException e) {
			response.getWriter().write( e.getMessage() + " [ Fail ]"); 
			return;
		}	
		
		RequestVO req = new RequestVO(IncludeType.TSS, "", "", "", "", "11250", "", 1, true, RouteType.NOT_INCLUDE, false, false, RequestType.SCENARIO_REGION);
		List<TlLogic> tl = service.selectTlLogic(req);
		int count = tls.getTlLogics().size();
		for( int i = count - 1; i > -1; i--) {
			boolean bExist = false;
			String id = "";
			
			if( tls.getTlLogics().get(i).getId().contains("cluster") ) {				
				id =  tls.getTlLogics().get(i).getId(); 	
			}else {			
				for( int j = 0; j < tl.size(); j++) {
					if( tl.get(j).getId().equals(tls.getTlLogics().get(i).getId()) ) {
						bExist = true;
						id =  tls.getTlLogics().get(i).getId(); 
						break;
					}
				}
			}
			
			if(!bExist) {
				tls.getTlLogics().remove(i);
				int conCount = tls.getCon().size();
				for( int c = conCount - 1; c > -1 ; c--) {
					if( tls.getCon().get(c).getTl().equals(id) ) {
						tls.getCon().remove(c);
					}
				}
			}
		}
			
		manager.TlLogicToFile(tls, new File( storePath + "filter.xml"));
    }
	
	@RequestMapping(value="/upLoadSignalExcel")
	public void upLoadSignalExcel(MultipartFile uploadFile, HttpServletResponse response) throws IOException {
		if(uploadFile == null ){ response.getWriter().write("The upload file is an Null. [ Fail ]"); return;}
		if(uploadFile.isEmpty() ){ response.getWriter().write("The upload file is an empty file. [ Fail ]"); return;}
		if( !uploadFile.getContentType().equals("application/vnd.ms-excel") && 
			!uploadFile.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){ response.getWriter().write("The upload file is not an Excel file. [ Fail ]"); return; }

		CheckDirectory(storePath);
		
		FileVO vo = getFileVO(uploadFile, XmlType.TLLOGIC);
		File excel = new File( storePath + vo.getFile_name() );
		uploadFile.transferTo(excel);
		
		if(service.ExcelToDatabase(excel)){
			excel.delete();
			response.getWriter().write("Success");
		}else{
			excel.delete();
			response.getWriter().write("Fail");
		}		
	}
	
}
