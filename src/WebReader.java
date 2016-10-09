

import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.net.URL;
import java.net.URLConnection;

public class WebReader {

    public static double getBid(String pair) {
        //System.out.println("The pair received in Webreader is: "+pair);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                URL url = new URL("http://rates.fxcm.com/RatesXML");
                URLConnection conn = url.openConnection();
                Document doc = builder.parse(conn.getInputStream());
                NodeList rateList = doc.getElementsByTagName("Rate");
                for (int i = 0; i < rateList.getLength(); i++) {
                    Node r = rateList.item(i);
                    if (r.getNodeType() == Node.ELEMENT_NODE) {
                            Element Rate = (Element) r;
                            String Symbol = Rate.getAttribute("Symbol");
                            //System.out.println("Symbol is :"+Symbol);
                            if(Symbol.equals(pair))
                            {
                                NodeList nameList = Rate.getChildNodes();
                                for (int j = 0; j < nameList.getLength(); j++) {
                                        Node n = nameList.item(j);
                                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                                                Element name = (Element) n;
                                                //System.out.println("The tag compared is "+name.getTagName());
                                                if(name.getTagName()=="Bid")
                                                {
                                                    return Double.parseDouble(name.getTextContent());
                                                }
                                            }
        
                                    }
                            }
                        }
                    }

                
            } catch (ParserConfigurationException e) {

                e.printStackTrace();
            } catch (SAXException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            return -1.0;
        
        }       
    }

