package org.dunkentec.xfdf2csvconverter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XfdfReader {
	
	private static final Logger log = LogManager.getLogger(XfdfReader.class);
	
	public Collection<Entry> readXfdfFromFile(String fileName) throws ParserConfigurationException, SAXException, IOException{
		Collection<Entry> entries = new LinkedList<Entry>();
		
		File inputFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(inputFile);
		
		NodeList polygons = document.getElementsByTagName("polygon");
		for (int currentIndex = 0; currentIndex < polygons.getLength(); currentIndex++) {
			Node currentItem = polygons.item(currentIndex);
			Element currentElement = (Element)currentItem;
			addEntry(entries, currentElement, "Polygon");
		}
		
		NodeList polylines = document.getElementsByTagName("polyline");
		for (int currentIndex = 0; currentIndex < polylines.getLength(); currentIndex++) {
			Node currentItem = polylines.item(currentIndex);
			Element currentElement = (Element)currentItem;
			addEntry(entries, currentElement, "Polyline");
		}
		
		NodeList lines = document.getElementsByTagName("line");
		for (int currentIndex = 0; currentIndex < lines.getLength(); currentIndex++) {
			Node currentItem = lines.item(currentIndex);
			Element currentElement = (Element)currentItem;
			addEntry(entries, currentElement, "Line");
		}
		
		return entries;
	}
	
	private void addEntry(Collection<Entry> entries, Element element, String type){
		String title = element.getAttribute("title");
		String subject = element.getAttribute("subject");
		String page = element.getAttribute("page");
		String contents = "";
		if(element.getElementsByTagName("contents") != null){
			contents = element.getElementsByTagName("contents").item(0).getTextContent();
		}
		
		log.info("Title: " + title);
		log.info("Subject: " + subject);
		log.info("Page: " + page);
		log.info("Type: " + type);
		log.info("Contents: " + contents);
		
		entries.add(new Entry(title, subject, page, type, contents));
	}

}
