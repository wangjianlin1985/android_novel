package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Collection;
public class CollectionListHandler extends DefaultHandler {
	private List<Collection> collectionList = null;
	private Collection collection;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (collection != null) { 
            String valueString = new String(ch, start, length); 
            if ("collectId".equals(tempString)) 
            	collection.setCollectId(new Integer(valueString).intValue());
            else if ("novelObj".equals(tempString)) 
            	collection.setNovelObj(new Integer(valueString).intValue());
            else if ("userObj".equals(tempString)) 
            	collection.setUserObj(valueString); 
            else if ("collectTime".equals(tempString)) 
            	collection.setCollectTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Collection".equals(localName)&&collection!=null){
			collectionList.add(collection);
			collection = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		collectionList = new ArrayList<Collection>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Collection".equals(localName)) {
            collection = new Collection(); 
        }
        tempString = localName; 
	}

	public List<Collection> getCollectionList() {
		return this.collectionList;
	}
}
