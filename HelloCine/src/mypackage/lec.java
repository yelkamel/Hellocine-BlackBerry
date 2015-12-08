package mypackage;

import java.io.InputStream;
import java.util.Date;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class lec {
	
    public Document document = null;

    //protected Log log = new Log("XmlManager");

    public lec(String file) 
    {
       FileConnection fc = null;
       InputStream inputStream = null;
       try {
           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
           DocumentBuilder builder = factory.newDocumentBuilder();
           fc = (FileConnection)Connector.open(file, Connector.READ);
           inputStream = fc.openInputStream();  
           document = builder.parse(inputStream);
       } catch (Exception e) {
           //log.error("builder.parse", e);   
       } finally {
           try {
        	   if (fc!=null)
            	   fc.close();
               if (inputStream!=null)
            	   inputStream.close();
           } catch (Exception e) {
               //log.error("close", e);
           } 
       }
    }

    public String readApiString(String tag, int i)
    {       
    	if (document == null)
    	{
    		Dialog.alert("erreur : le document est null.");
    	}
        Element root = document.getDocumentElement();
        NodeList list = root.getElementsByTagName(tag);
        if (list.item(i) == null)
        	return "";
        return(getCharacterDataFromElement((Element)list.item(i)));
    }
    public static String getCharacterDataFromElement(Element e)
    {
    	  Node child = e.getFirstChild();
    	  if (child instanceof CharacterData)
    	  {
    	    CharacterData cd = (CharacterData) child;
    	    return cd.getData();
    	  }
    	  return "";
    	}
    
    public static String convertTimestamp(String timestamp, int type)
    {
    	long date = Long.parseLong(timestamp);
    	date = date * 1000L;
    	Date dateObj = new Date(date);

    	String res = "";
    	DateFormat day = new SimpleDateFormat("dd/MM/yyyy");
    	DateFormat hour = new SimpleDateFormat("HH:mm");

    	if (type == 0) 
    	{
    	res = "Le ";
    	res += day.format(dateObj) + " de " + hour.format(dateObj);
    	} 
    	else 
    	{
    	res = " à ";
    	res += hour.format(dateObj);
    	}

    	return res;
    }
}