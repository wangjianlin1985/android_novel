package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Novel;
import com.mobileclient.util.HttpUtil;

/*小说管理业务逻辑层*/
public class NovelService {
	/* 添加小说 */
	public String AddNovel(Novel novel) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("novelId", novel.getNovelId() + "");
		params.put("novelClassObj", novel.getNovelClassObj() + "");
		params.put("novelName", novel.getNovelName());
		params.put("novelPhoto", novel.getNovelPhoto());
		params.put("author", novel.getAuthor());
		params.put("publish", novel.getPublish());
		params.put("publishDate", novel.getPublishDate().toString());
		params.put("novelPageNum", novel.getNovelPageNum() + "");
		params.put("wordsNum", novel.getWordsNum() + "");
		params.put("novelFile", novel.getNovelFile());
		params.put("tjFlag", novel.getTjFlag());
		params.put("readCount", novel.getReadCount() + "");
		params.put("novelDesc", novel.getNovelDesc());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NovelServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询小说 */
	public List<Novel> QueryNovel(Novel queryConditionNovel) throws Exception {
		String urlString = HttpUtil.BASE_URL + "NovelServlet?action=query";
		if(queryConditionNovel != null) {
			urlString += "&novelClassObj=" + queryConditionNovel.getNovelClassObj();
			urlString += "&novelName=" + URLEncoder.encode(queryConditionNovel.getNovelName(), "UTF-8") + "";
			urlString += "&author=" + URLEncoder.encode(queryConditionNovel.getAuthor(), "UTF-8") + "";
			urlString += "&publish=" + URLEncoder.encode(queryConditionNovel.getPublish(), "UTF-8") + "";
			if(queryConditionNovel.getPublishDate() != null) {
				urlString += "&publishDate=" + URLEncoder.encode(queryConditionNovel.getPublishDate().toString(), "UTF-8");
			}
			urlString += "&tjFlag=" + URLEncoder.encode(queryConditionNovel.getTjFlag(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		NovelListHandler novelListHander = new NovelListHandler();
		xr.setContentHandler(novelListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Novel> novelList = novelListHander.getNovelList();
		return novelList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Novel> novelList = new ArrayList<Novel>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Novel novel = new Novel();
				novel.setNovelId(object.getInt("novelId"));
				novel.setNovelClassObj(object.getInt("novelClassObj"));
				novel.setNovelName(object.getString("novelName"));
				novel.setNovelPhoto(object.getString("novelPhoto"));
				novel.setAuthor(object.getString("author"));
				novel.setPublish(object.getString("publish"));
				novel.setPublishDate(Timestamp.valueOf(object.getString("publishDate")));
				novel.setNovelPageNum(object.getInt("novelPageNum"));
				novel.setWordsNum(object.getInt("wordsNum"));
				novel.setNovelFile(object.getString("novelFile"));
				novel.setTjFlag(object.getString("tjFlag"));
				novel.setReadCount(object.getInt("readCount"));
				novel.setNovelDesc(object.getString("novelDesc"));
				novelList.add(novel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return novelList;
	}

	/* 更新小说 */
	public String UpdateNovel(Novel novel) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("novelId", novel.getNovelId() + "");
		params.put("novelClassObj", novel.getNovelClassObj() + "");
		params.put("novelName", novel.getNovelName());
		params.put("novelPhoto", novel.getNovelPhoto());
		params.put("author", novel.getAuthor());
		params.put("publish", novel.getPublish());
		params.put("publishDate", novel.getPublishDate().toString());
		params.put("novelPageNum", novel.getNovelPageNum() + "");
		params.put("wordsNum", novel.getWordsNum() + "");
		params.put("novelFile", novel.getNovelFile());
		params.put("tjFlag", novel.getTjFlag());
		params.put("readCount", novel.getReadCount() + "");
		params.put("novelDesc", novel.getNovelDesc());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NovelServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除小说 */
	public String DeleteNovel(int novelId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("novelId", novelId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NovelServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "小说信息删除失败!";
		}
	}

	/* 根据小说id获取小说对象 */
	public Novel GetNovel(int novelId)  {
		List<Novel> novelList = new ArrayList<Novel>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("novelId", novelId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NovelServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Novel novel = new Novel();
				novel.setNovelId(object.getInt("novelId"));
				novel.setNovelClassObj(object.getInt("novelClassObj"));
				novel.setNovelName(object.getString("novelName"));
				novel.setNovelPhoto(object.getString("novelPhoto"));
				novel.setAuthor(object.getString("author"));
				novel.setPublish(object.getString("publish"));
				novel.setPublishDate(Timestamp.valueOf(object.getString("publishDate")));
				novel.setNovelPageNum(object.getInt("novelPageNum"));
				novel.setWordsNum(object.getInt("wordsNum"));
				novel.setNovelFile(object.getString("novelFile"));
				novel.setTjFlag(object.getString("tjFlag"));
				novel.setReadCount(object.getInt("readCount"));
				novel.setNovelDesc(object.getString("novelDesc"));
				novelList.add(novel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = novelList.size();
		if(size>0) return novelList.get(0); 
		else return null; 
	}
}
