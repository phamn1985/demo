package CoreFramework;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestNGResultReader {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<ResultObject> a = returnTestSuiteResultFromXML(ExternalFileConfiguration.xmlresultDirectory,
				ExternalFileConfiguration.defaultsuiteName);
		ExcelFileWriter.updateTestSuiteResultToExcelFile(ExternalFileConfiguration.testplanFile,
				ExternalFileConfiguration.testplandefaultSheet, ExternalFileConfiguration.xmlresultDirectory,
				ExternalFileConfiguration.defaultsuiteName);
	}

	public static List<ResultObject> returnTestSuiteResultFromXML(String orgxmlpath, String suitename) {
		List<ResultObject> result = new ArrayList<ResultObject>();
		String xmlpath = orgxmlpath.replace("\\", "/");
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlpath);
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression suittest = xpath.compile("//suite[@name = '" + suitename + "']//test");
			NodeList testnodes = (NodeList) suittest.evaluate(doc, XPathConstants.NODESET);
			for (int temp = 0; temp < testnodes.getLength(); temp++) {
				String strTestResult = "PASS";
				Node nNode = testnodes.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					ResultObject resultobj = new ResultObject();
					Element eElement = (Element) nNode;
					resultobj.testname = eElement.getAttribute("name");
					NodeList testclasses = (NodeList) nNode.getChildNodes();

					for (int i = 0; i < testclasses.getLength(); i++) {
						Node testclass = testclasses.item(i);
						if (testclass.getNodeType() == Node.ELEMENT_NODE) {
							eElement = (Element) testclass;
							resultobj.testclass = eElement.getAttribute("name");
							NodeList testmethods = (NodeList) testclass.getChildNodes();
							int duration = 0;
							int z = 0;
							for (int x = 0; x < testmethods.getLength(); x++) {

								Node testmethod = testmethods.item(x);
								if (testmethod.getNodeType() == Node.ELEMENT_NODE) {
									eElement = (Element) testmethod;
									String tempResult = eElement.getAttribute("status");
									duration = duration + Integer.parseInt(eElement.getAttribute("duration-ms"));
									if (z < 1) {
										// resultobj.starttime =
										// eElement.getAttribute("started-at");
										String startstamp = eElement.getAttribute("started-at");
										String[] startparts = startstamp.split("T");

										resultobj.startdate = startparts[0];
										resultobj.starttime = startparts[1].replace("Z", "");

									}
									if (z < testmethods.getLength()) {
										String endstamp = eElement.getAttribute("finished-at");
										String[] endparts = endstamp.split("T");

										resultobj.enddate = endparts[0];
										resultobj.endtime = endparts[1].replace("Z", "");
									}

									if (tempResult.equalsIgnoreCase("FAIL")) {
										strTestResult = tempResult;

									}

									z++;
								}
							}
							resultobj.duration = DatesUtility.convertMillisecondsToHHMMSS(Integer.toString(duration));
							resultobj.result = strTestResult;
							result.add(resultobj);

						}
					}

				}

			}
		} catch (Exception e) {

		}
		return result;
	}

}
