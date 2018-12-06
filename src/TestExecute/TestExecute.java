package TestExecute;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import CoreFramework.XMLFileWriter;

public class TestExecute {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String par = args[0];
		XmlSuite suite = XMLFileWriter.createXMLTestSuite(par);
		TestNG tng = new TestNG();
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		tng.setXmlSuites(suites);
		tng.run();
	}

}
