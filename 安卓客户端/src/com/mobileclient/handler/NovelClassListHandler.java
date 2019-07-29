package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.NovelClass;
public class NovelClassListHandler extends DefaultHandler {
	private List<NovelClass> novelClassList = null;
	private NovelClass novelClass;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (novelClass != null) { 
            String valueString = new String(ch, start, length); 
            if ("classId".equals(tempString)) 
            	novelClass.setClassId(new Integer(valueString).intValue());
            else if ("className".equals(tempString)) 
            	novelClass.setClassName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("NovelClass".equals(localName)&&novelClass!=null){
			novelClassList.add(novelClass);
			novelClass = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		novelClassList = new ArrayList<NovelClass>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("NovelClass".equals(localName)) {
            novelClass = new NovelClass(); 
        }
        tempString = localName; 
	}

	public List<NovelClass> getNovelClassList() {
		return this.novelClassList;
	}
}
