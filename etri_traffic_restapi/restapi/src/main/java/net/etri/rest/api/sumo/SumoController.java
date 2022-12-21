package net.etri.rest.api.sumo;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.etri.rest.api.AbstractController;
import net.etri.rest.api.sumo.service.Connections;
import net.etri.rest.api.sumo.service.SumoService;
import net.etri.rest.api.sumo.service.TlLogics;

/**
<p>클래스 설명 : Sumo관련 요청에 대한 처리를 위한 컨트롤러 클래스</p>
<p>SumoController</p>
<pre>
 net.etri.rest.api.sumo
        └ SumoController.java
</pre>
**/
@RestController("sumoController")
public class SumoController extends AbstractController {

	Logger log = LoggerFactory.getLogger(SumoController.class);
			
	@Resource(name="sumoService")
	private SumoService service;
	
	@RequestMapping(value="/downConXml")
	public void downConXml(HttpServletResponse response) throws IOException, JAXBException {
		Connections cons = new Connections();
		cons.setCon(service.selectConnection(null));		
		ConXmlResponse(cons, response ); 
	}
	
	public void ConXmlResponse(Connections cons, HttpServletResponse response) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 SUMO용  Connections XML반환 
        response.setHeader("Content-Disposition", "attachment; filename=\""+ "con.xml" + "\";");
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		
		JAXBContext ctx = JAXBContext.newInstance(Connections.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://sumo.dlr.de/xsd/connections_file.xsd");
        
        m.marshal(cons, response.getWriter());
	}	
	
	@RequestMapping(value="/downTlXml")
	public void downTlXml(HttpServletResponse response) throws IOException, JAXBException, NotFoundException {		
		TlLogics tls = new TlLogics();
		tls.setTlLogics(service.selectTlLogic(null));	
		tls.setCon(service.selectTLtoConnection(tls.getTlLogics()));
		TlLogicXmlResponse(tls, response ); 
	}
	
	public void TlLogicXmlResponse(TlLogics tlLogics, HttpServletResponse response) throws JAXBException, IOException{
		// TODO : HttpServletResponse를 통한 SUMO용 TlLogics XML반환 
        response.setHeader("Content-Disposition", "attachment; filename=\""+ "tll.xml" + "\";");
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		
		JAXBContext ctx = JAXBContext.newInstance(TlLogics.class);		
		Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);        
        m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://sumo.dlr.de/xsd/tllogic_file.xsd");
        
        m.marshal(tlLogics, response.getWriter());
	}	
}
