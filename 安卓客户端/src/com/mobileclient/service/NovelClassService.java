package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.NovelClass;
import com.mobileclient.util.HttpUtil;

/*小说类别管理业务逻辑层*/
public class NovelClassService {
	/* 添加小说类别 */
	public String AddNovelClass(NovelClass novelClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", novelClass.getClassId() + "");
		params.put("className", novelClass.getClassName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NovelClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询小说类别 */
	public List<NovelClass> QueryNovelClass(NovelClass queryConditionNovelClass) throws Exception {
		String urlString = HttpUtil.BASE_URL + "NovelClassServlet?action=query";
		if(queryConditionNovelClass != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		NovelClassListHandler novelClassListHander = new NovelClassListHandler();
		xr.setContentHandler(novelClassListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<NovelClass> novelClassList = novelClassListHander.getNovelClassList();
		return novelClassList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<NovelClass> novelClassList = new ArrayList<NovelClass>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				NovelClass novelClass = new NovelClass();
				novelClass.setClassId(object.getInt("classId"));
				novelClass.setClassName(object.getString("className"));
				novelClassList.add(novelClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return novelClassList;
	}

	/* 更新小说类别 */
	public String UpdateNovelClass(NovelClass novelClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", novelClass.getClassId() + "");
		params.put("className", novelClass.getClassName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NovelClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除小说类别 */
	public String DeleteNovelClass(int classId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", classId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NovelClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "小说类别信息删除失败!";
		}
	}

	/* 根据分类id获取小说类别对象 */
	public NovelClass GetNovelClass(int classId)  {
		List<NovelClass> novelClassList = new ArrayList<NovelClass>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", classId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NovelClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				NovelClass novelClass = new NovelClass();
				novelClass.setClassId(object.getInt("classId"));
				novelClass.setClassName(object.getString("className"));
				novelClassList.add(novelClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = novelClassList.size();
		if(size>0) return novelClassList.get(0); 
		else return null; 
	}
}
