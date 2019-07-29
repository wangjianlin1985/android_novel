package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Novel;
public class NovelListHandler extends DefaultHandler {
	private List<Novel> novelList = null;
	private Novel novel;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (novel != null) { 
            String valueString = new String(ch, start, length); 
            if ("novelId".equals(tempString)) 
            	novel.setNovelId(new Integer(valueString).intValue());
            else if ("novelClassObj".equals(tempString)) 
            	novel.setNovelClassObj(new Integer(valueString).intValue());
            else if ("novelName".equals(tempString)) 
            	novel.setNovelName(valueString); 
            else if ("novelPhoto".equals(tempString)) 
            	novel.setNovelPhoto(valueString); 
            else if ("author".equals(tempString)) 
            	novel.setAuthor(valueString); 
            else if ("publish".equals(tempString)) 
            	novel.setPublish(valueString); 
            else if ("publishDate".equals(tempString)) 
            	novel.setPublishDate(Timestamp.valueOf(valueString));
            else if ("novelPageNum".equals(tempString)) 
            	novel.setNovelPageNum(new Integer(valueString).intValue());
            else if ("wordsNum".equals(tempString)) 
            	novel.setWordsNum(new Integer(valueString).intValue());
            else if ("novelFile".equals(tempString)) 
            	novel.setNovelFile(valueString); 
            else if ("tjFlag".equals(tempString)) 
            	novel.setTjFlag(valueString); 
            else if ("readCount".equals(tempString)) 
            	novel.setReadCount(new Integer(valueString).intValue());
            else if ("novelDesc".equals(tempString)) 
            	novel.setNovelDesc(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Novel".equals(localName)&&novel!=null){
			novelList.add(novel);
			novel = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		novelList = new ArrayList<Novel>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Novel".equals(localName)) {
            novel = new Novel(); 
        }
        tempString = localName; 
	}

	public List<Novel> getNovelList() {
		return this.novelList;
	}
}
