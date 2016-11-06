package org.dunkentec.xfdf2csvconverter;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class XfdfReaderTest {

	@Test
	public void testXfdfReader() throws ParserConfigurationException, SAXException, IOException {
		Collection<Entry> expected = new LinkedList<Entry>();
		expected.add(new Entry("Christian", "Flächenmessung", "0", "Polygon", "45,05 m²"));
		expected.add(new Entry("Christian", "Umfangsmessung", "0", "Polyline", "19,99 m"));
		expected.add(new Entry("Christian", "Entfernungsmessung", "0", "Line", "8,06 m"));
		
		XfdfReader reader = new XfdfReader();
		Collection<Entry> result = reader.readXfdfFromFile("src/test/java/org/dunkentec/xfdf2csvconverter/Info.xfdf");
		
		assertEquals(expected, result);
	}
	
	@Test(expected = IOException.class)
	public void testXfdfReaderFileNotAvailable() throws ParserConfigurationException, SAXException, IOException{
		XfdfReader reader = new XfdfReader();
		reader.readXfdfFromFile("notOnDisk.xfdf");
		
	}

}
