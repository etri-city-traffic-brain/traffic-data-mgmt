/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file XmlManager.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 5. 23.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.stereotype.Service;

import net.solufine.rest.api.filter.XmlFIleFilter;

/**
  <p>클래스 설명 : XML변환 및 반환을 처리하기 위한 클래스</p>
  <p>XmlManager</p>
  <pre>
   net.solufine.rest.api.xml
          └ XmlManager.java
  </pre>
  @author  redkaras 
  @since  2018. 5. 23.
  @version 1.0
**/
@Service("xmlManager")
public class XmlManager {

	public void NodeXmlResponse(Nodes nodes, HttpServletResponse response) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 NODE XML반환 
        response.setHeader("Content-Disposition", "attachment; filename=\""+ "node.xml" + "\";");
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		
		JAXBContext ctx = JAXBContext.newInstance(Nodes.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://sumo.dlr.de/xsd/nodes_file.xsd");
        
        m.marshal(nodes, response.getWriter());
	}	
	
	public void NodeToFile(Nodes nodes, File file) throws JAXBException, IOException{
		// TODO : File을 통한 NODE XML반환 	
		JAXBContext ctx = JAXBContext.newInstance(Nodes.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://sumo.dlr.de/xsd/nodes_file.xsd");
        
        m.marshal(nodes, file);
	}
	
	public void EdgeXmlResponse(Edges edges, HttpServletResponse response) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 Edges XML반환 
        response.setHeader("Content-Disposition", "attachment; filename=\""+ "edge.xml" + "\";");
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		
		JAXBContext ctx = JAXBContext.newInstance(Edges.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://sumo.dlr.de/xsd/edges_file.xsd");
        
        m.marshal(edges, response.getWriter());
	}	
	
	public void EdgeToFile(Edges edges, File file) throws JAXBException, IOException{
		// TODO : File을 통한 Edges XML반환 	
		JAXBContext ctx = JAXBContext.newInstance(Edges.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://sumo.dlr.de/xsd/edges_file.xsd");
        
        m.marshal(edges, file);
	}
	
	public void ConXmlResponse(Connections cons, HttpServletResponse response) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 Connections XML반환 
        response.setHeader("Content-Disposition", "attachment; filename=\""+ "connection.xml" + "\";");
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		
		JAXBContext ctx = JAXBContext.newInstance(Connections.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://sumo.dlr.de/xsd/connections_file.xsd");
        
        m.marshal(cons, response.getWriter());
	}	
	
	public void ConToFile(Connections cons, File file) throws JAXBException, IOException{
		// TODO : File을 통한 Connections XML반환 	
		JAXBContext ctx = JAXBContext.newInstance(Connections.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://sumo.dlr.de/xsd/connections_file.xsd");
        
        m.marshal(cons, file);
	}
	
	public void TlLogicXmlResponse(TlLogics tlLogics, HttpServletResponse response) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 Connections XML반환 
        response.setHeader("Content-Disposition", "attachment; filename=\""+ "tll.xml" + "\";");
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		
		JAXBContext ctx = JAXBContext.newInstance(TlLogics.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://sumo.dlr.de/xsd/tllogic_file.xsd");
        
        m.marshal(tlLogics, response.getWriter());
	}	
	
	public void TlLogicToFile(TlLogics tlLogics, File file) throws JAXBException, IOException{
		// TODO : File을 통한 TlLogics XML반환 	
		JAXBContext ctx = JAXBContext.newInstance(TlLogics.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://sumo.dlr.de/xsd/tllogic_file.xsd");
        
        m.marshal(tlLogics, file);
	}
	
	public void TrafficSignalXmlResponse(TrafficSignals trafficSignals, HttpServletResponse response) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 TrafficSignal XML반환 
        response.setHeader("Content-Disposition", "attachment; filename=\""+ "tss.xml" + "\";");
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		
		JAXBContext ctx = JAXBContext.newInstance(TrafficSignals.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
       
        m.marshal(trafficSignals, response.getWriter());
	}	
	
	public void TrafficSignalToFile(TrafficSignals trafficSignalSystem, File file) throws JAXBException, IOException{
		// TODO : File을 통한 TrafficSignals XML반환 	
		JAXBContext ctx = JAXBContext.newInstance(TrafficSignals.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        m.marshal(trafficSignalSystem, file);
	}
	
	public void RouteXmlResponse(Routes route, HttpServletResponse response ) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 궤적 XML반환 
        response.setHeader("Content-Disposition", "attachment; filename=\""+ "route.xml" + "\";");
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		
		JAXBContext ctx = JAXBContext.newInstance(Routes.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
       
        m.marshal(route, response.getWriter());
	}	
	
	public void ScenearioXmlResponse(Scenarios sc, HttpServletResponse response) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 Scenearios XML반환 
        response.setHeader("Content-Disposition", "attachment; filename=\""+ "scenario.xml" + "\";");
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		
		JAXBContext ctx = JAXBContext.newInstance(Scenarios.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);        
        
        m.marshal(sc, response.getWriter());
	}	
	
	public void ScenearioToFile(Scenarios sc, File file) throws JAXBException, IOException{
		// TODO : File을 통한 Scenearios XML반환 	
		JAXBContext ctx = JAXBContext.newInstance(Scenarios.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);        
        m.marshal(sc, file);
	}
	
	public void ScenearioXmlResponse(Scenarios sc, HttpServletResponse response, String path) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 sceneario.zip 반환 
		long currentTime = System.currentTimeMillis();
		File dir = new File(path + File.separator + currentTime);
		if(!dir.isDirectory()){
			dir.mkdir();
		}
		
		NodeToFile(sc.getNodes(), new File(path + File.separator + currentTime +  File.separator + "node.xml"));
		EdgeToFile(sc.getEdges(), new File(path + File.separator + currentTime +  File.separator + "edge.xml"));
		ConToFile(sc.getConnections(), new File(path + File.separator + currentTime +  File.separator + "connection.xml"));
		TrafficSignalToFile(sc.getTrafficSignalSystem(), new File(path + File.separator + currentTime +  File.separator + "tss.xml"));
		
		XmlResponseZip(response, path, "scenario.zip", currentTime);
	}	
	
	public void ResponseMapZip(Scenarios sc, HttpServletResponse response, String path) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 map.zip 반환 
		long currentTime = System.currentTimeMillis();
		File dir = new File(path + File.separator + currentTime);
		if(!dir.isDirectory()){
			dir.mkdir();
		}
						
		NodeToFile(sc.getNodes(), new File(path + File.separator + currentTime +  File.separator + "node.xml"));
		EdgeToFile(sc.getEdges(), new File(path + File.separator + currentTime +  File.separator + "edge.xml"));
		ConToFile(sc.getConnections(), new File(path + File.separator + currentTime +  File.separator + "connection.xml"));
		
		XmlResponseZip(response, path, "map.zip", currentTime);
	}
	
	public void XmlResponseZip(HttpServletResponse response, String path, String zipName, long currentTime) throws IOException{
		
		File dir = new File(path + File.separator + currentTime);
		if(!dir.isDirectory()){
			dir.mkdir();
		}
		XmlFIleFilter filter = new XmlFIleFilter();		
		String[] zipFileList = dir.list(filter);
		
		response.setHeader("Content-Disposition", "attachment; filename=\""+ zipName + "\";");
		response.setContentType("application/zip");
		response.setCharacterEncoding("UTF-8");
		
		int size = 1024;
		byte[] buf = new byte[size];
		FileInputStream fis = null;
		ZipArchiveOutputStream zos = null;
		BufferedInputStream bis = null;
		
		try{
			zos = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(path + File.separator + currentTime +  File.separator + zipName)));
			for(int i = 0; i < zipFileList.length; i++){
				zos.setEncoding("UTF-8");
				fis = new FileInputStream(path + File.separator + currentTime +  File.separator + zipFileList[i]);
				bis = new BufferedInputStream(fis, size);
				
				zos.putArchiveEntry(new ZipArchiveEntry(zipFileList[i]));
				int len;
				while((len = bis.read(buf, 0, size)) != -1){
					zos.write(buf, 0, len);
				}
				
				bis.close();
				fis.close();
				zos.closeArchiveEntry();
			}
			zos.close();
		}catch(FileNotFoundException fe){
			fe.printStackTrace();
		}finally{
			if( zos != null){ 
				zos.close();
			}
			if( fis != null){
				fis.close();
			}
			if( bis != null){
				bis.close();
			}
		}
		
		File zip = new File(path + File.separator + currentTime +  File.separator + zipName);
		response.setContentLengthLong(zip.length());
		
		fis = new FileInputStream(path + File.separator + currentTime +  File.separator + zipName);
		bis = new BufferedInputStream(fis, size);
		
		int len;
		while((len = bis.read(buf, 0, size)) != -1){
			response.getOutputStream().write(buf, 0, len);
		}
		
		if( fis != null){
			fis.close();
		}
		if( bis != null){
			bis.close();
		}
		
		for(int i = 0; i < zipFileList.length; i++){
			File f = new File(path + File.separator + currentTime +  File.separator + zipFileList[i]);
			f.delete();
		}
		response.flushBuffer();
	}
	
	
	public void WeatherXmlResponse(Weathers weather, HttpServletResponse response) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 Weather XML반환 
        response.setHeader("Content-Disposition", "attachment; filename=\""+ "weather.xml" + "\";");
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		
		JAXBContext ctx = JAXBContext.newInstance(Weathers.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                
        m.marshal(weather, response.getWriter());
	}	
	
	public void UnexpectedXmlResponse(Unexpecteds unexpected, HttpServletResponse response) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 Event XML반환 
        response.setHeader("Content-Disposition", "attachment; filename=\""+ "event.xml" + "\";");
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		
		JAXBContext ctx = JAXBContext.newInstance(Unexpecteds.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                
        m.marshal(unexpected, response.getWriter());
	}	
	
	public Nodes XmlToNode(File xml) throws JAXBException{
		// TODO : 업로드된 XML파일을 통한 NODE 객체생성
		JAXBContext ctx = JAXBContext.newInstance(Nodes.class);
		Unmarshaller um = ctx.createUnmarshaller();
		Nodes nodes = (Nodes) um.unmarshal(xml);
		
        return nodes;
	}
	
	public Edges XmlToEdge(File xml) throws JAXBException{
		// TODO : 업로드된 XML파일을 통한 Edge 객체생성
		JAXBContext ctx = JAXBContext.newInstance(Edges.class);
		Unmarshaller um = ctx.createUnmarshaller();
		Edges edge = (Edges) um.unmarshal(xml);
		if( edge != null){
			if( edge.getEdge() != null){
				edge.getEdge().forEach((item) -> {
					item.setShape(item.getShape());
		        });
			}
		}
		
        return edge;
	}
	
	public Connections XmlToCon(File xml) throws JAXBException{
		// TODO : 업로드된 XML파일을 통한 Connection 객체생성
		JAXBContext ctx = JAXBContext.newInstance(Connections.class);
		Unmarshaller um = ctx.createUnmarshaller();
		Connections con = (Connections) um.unmarshal(xml);
		
        return con;
	}
	
	public TlLogics XmlToTl(File xml) throws JAXBException{
		// TODO : 업로드된 XML파일을 통한 Traffic Logic 객체생성
		JAXBContext ctx = JAXBContext.newInstance(TlLogics.class);
		Unmarshaller um = ctx.createUnmarshaller();
		TlLogics tls = (TlLogics) um.unmarshal(xml);
		
        return tls;
	}
	
	public void PartitionXmlResponse(PartitionScenarios partitions, HttpServletResponse response, String path) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 PartitionScenarios XML반환
		
		long currentTime = System.currentTimeMillis();
		File dir = new File(path + File.separator + currentTime);
		if(!dir.isDirectory()){
			dir.mkdir();
		}
		
		if( partitions.getPartition().size() == 1 ){
			NodeToFile(partitions.getPartition().get(0).getNodes(), new File(path + File.separator + currentTime +  File.separator + "node.xml"));
			EdgeToFile(partitions.getPartition().get(0).getEdges(), new File(path + File.separator + currentTime +  File.separator + "edge.xml"));
			ConToFile(partitions.getPartition().get(0).getConnections(), new File(path + File.separator + currentTime +  File.separator + "connection.xml"));
			TrafficSignalToFile(partitions.getPartition().get(0).getTrafficSignalSystem(), new File(path + File.separator + currentTime +  File.separator + "tss.xml"));
		}else{
			for(int i = 0; i < partitions.getPartition().size(); i++ ){
				if( partitions.getPartition().get(i).getNodes() == null)
					continue;
				
				NodeToFile(partitions.getPartition().get(i).getNodes(), new File(path + File.separator + currentTime +  File.separator + "sub" + i + ".node.xml"));
				EdgeToFile(partitions.getPartition().get(i).getEdges(), new File(path + File.separator + currentTime +  File.separator +  "sub" + i + ".edge.xml"));
				ConToFile(partitions.getPartition().get(i).getConnections(), new File(path + File.separator + currentTime +  File.separator + "sub" + i + ".connection.xml"));
				TrafficSignalToFile(partitions.getPartition().get(i).getTrafficSignalSystem(), new File(path + File.separator + currentTime +  File.separator + "sub" + i + ".tss.xml"));				
			}
		}
		
		XmlResponseZip(response, path, "scenario.zip", currentTime);
	}	
	
	public void PartitionXmlMapResponse(PartitionScenarios partitions, HttpServletResponse response, String path) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 PartitionScenarios XML반환
		
		long currentTime = System.currentTimeMillis();
		File dir = new File(path + File.separator + currentTime);
		if(!dir.isDirectory()){
			dir.mkdir();
		}
		
		if( partitions.getPartition().size() == 1 ){
			NodeToFile(partitions.getPartition().get(0).getNodes(), new File(path + File.separator + currentTime +  File.separator + "node.xml"));
			EdgeToFile(partitions.getPartition().get(0).getEdges(), new File(path + File.separator + currentTime +  File.separator + "edge.xml"));
			ConToFile(partitions.getPartition().get(0).getConnections(), new File(path + File.separator + currentTime +  File.separator + "connection.xml"));
		}else{
			for(int i = 0; i < partitions.getPartition().size(); i++ ){
				NodeToFile(partitions.getPartition().get(i).getNodes(), new File(path + File.separator + currentTime +  File.separator + "sub" + i + ".node.xml"));
				EdgeToFile(partitions.getPartition().get(i).getEdges(), new File(path + File.separator + currentTime +  File.separator +  "sub" + i + ".edge.xml"));
				ConToFile(partitions.getPartition().get(i).getConnections(), new File(path + File.separator + currentTime +  File.separator + "sub" + i + ".connection.xml"));
			}
		}
		
		XmlResponseZip(response, path, "map.zip", currentTime);		
	}	
}


