package org.dunkentec.xfdf2csvconverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CsvWriter {
	
	private static final Logger log = LogManager.getLogger(CsvWriter.class);
	
	public void writeEntriesToCsv(Collection<Entry> entries, String outFile) throws IOException{
		StringBuffer output = new StringBuffer();
		output.append("title;subject;page;type;contents\n");
		
		for(Entry entry : entries){
			output.append(entry);
		}
		
		log.info("Writing file");
		Files.write(Paths.get(outFile), output.toString().getBytes());
		log.info("File written");
	}

}
