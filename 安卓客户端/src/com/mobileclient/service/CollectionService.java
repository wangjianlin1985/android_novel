package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Collection;
import com.mobileclient.util.HttpUtil;

/*收藏管理业务逻辑层*/
public class CollectionService {
	/* 添加收藏 */
	public String AddCollection(Collection collection) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("collectId", collection.getCollectId() + "");
		params.put("novelObj", collection.getNovelObj() + "");
		params.put("userObj", collection.getUserObj());
		params.put("collectTime", collection.getCollectTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CollectionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询收藏 */
	public List<Collection> QueryCollection(Collection queryConditionCollection) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CollectionServlet?action=query";
		if(queryConditionCollection != null) {
			urlString += "&novelObj=" + queryConditionCollection.getNovelObj();
			urlString += "&userObj=" + URLEncoder.encode(queryConditionCollection.getUserObj(), "UTF-8") + "";
			urlString += "&collectTime=" + URLEncoder.encode(queryConditionCollection.getCollectTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		CollectionListHandler collectionListHander = new CollectionListHandler();
		xr.setContentHandler(collectionListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Collection> collectionList = collectionListHander.getCollectionList();
		return collectionList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Collection> collectionList = new ArrayList<Collection>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Collection collection = new Collection();
				collection.setCollectId(object.getInt("collectId"));
				collection.setNovelObj(object.getInt("novelObj"));
				collection.setUserObj(object.getString("userObj"));
				collection.setCollectTime(object.getString("collectTime"));
				collectionList.add(collection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collectionList;
	}

	/* 更新收藏 */
	public String UpdateCollection(Collection collection) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("collectId", collection.getCollectId() + "");
		params.put("novelObj", collection.getNovelObj() + "");
		params.put("userObj", collection.getUserObj());
		params.put("collectTime", collection.getCollectTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CollectionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除收藏 */
	public String DeleteCollection(int collectId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("collectId", collectId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CollectionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "收藏信息删除失败!";
		}
	}

	/* 根据收藏id获取收藏对象 */
	public Collection GetCollection(int collectId)  {
		List<Collection> collectionList = new ArrayList<Collection>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("collectId", collectId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CollectionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Collection collection = new Collection();
				collection.setCollectId(object.getInt("collectId"));
				collection.setNovelObj(object.getInt("novelObj"));
				collection.setUserObj(object.getString("userObj"));
				collection.setCollectTime(object.getString("collectTime"));
				collectionList.add(collection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = collectionList.size();
		if(size>0) return collectionList.get(0); 
		else return null; 
	}
}
