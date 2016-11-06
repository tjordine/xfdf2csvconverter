package org.dunkentec.xfdf2csvconverter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

public class Converter {
	
	private static final Logger log = LogManager.getLogger(Converter.class);
	private XfdfReader reader;
	private CsvWriter writer;
	
	public Converter(){
		reader = new XfdfReader();
		writer = new CsvWriter();
	}
	
	public void convertXfdfToCsv(String inDir, String outDir) throws ParserConfigurationException, SAXException, IOException{
		File dir = new File(inDir);
		String dirList [] = dir.list();
		
		if(dirList != null && dirList.length > 0){
			
			for(String fileName : dirList){
				
				if(fileName.contains(".xfdf")){
					log.info("File name: " + fileName);
					convertSingleFileXfdfToCsv(inDir + "/" + fileName, outDir);
				}
			}
		}
	}
	
	public void convertSingleFileXfdfToCsv(String inFile, String outDir) throws ParserConfigurationException, SAXException, IOException{
		log.info("Starting transformation");
		
		Collection<Entry> entries = reader.readXfdfFromFile(inFile);
		
		String rawFileName = new File(inFile).getName().replaceFirst("[.][^.]+$", "");
		log.debug("Raw file name: " + rawFileName);
		
		writer.writeEntriesToCsv(entries, outDir + "/" + rawFileName + ".csv");
		
		log.info("Transformation finished");
	}

}
