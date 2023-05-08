package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.CollectionDAO;
import com.mobileserver.domain.Collection;

import org.json.JSONStringer;

public class CollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����ղ�ҵ������*/
	private CollectionDAO collectionDAO = new CollectionDAO();

	/*Ĭ�Ϲ��캯��*/
	public CollectionServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ�ղصĲ�����Ϣ*/
			int novelObj = 0;
			if (request.getParameter("novelObj") != null)
				novelObj = Integer.parseInt(request.getParameter("novelObj"));
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String collectTime = request.getParameter("collectTime");
			collectTime = collectTime == null ? "" : new String(request.getParameter(
					"collectTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ���ղز�ѯ*/
			List<Collection> collectionList = collectionDAO.QueryCollection(novelObj,userObj,collectTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Collections>").append("\r\n");
			for (int i = 0; i < collectionList.size(); i++) {
				sb.append("	<Collection>").append("\r\n")
				.append("		<collectId>")
				.append(collectionList.get(i).getCollectId())
				.append("</collectId>").append("\r\n")
				.append("		<novelObj>")
				.append(collectionList.get(i).getNovelObj())
				.append("</novelObj>").append("\r\n")
				.append("		<userObj>")
				.append(collectionList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<collectTime>")
				.append(collectionList.get(i).getCollectTime())
				.append("</collectTime>").append("\r\n")
				.append("	</Collection>").append("\r\n");
			}
			sb.append("</Collections>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Collection collection: collectionList) {
				  stringer.object();
			  stringer.key("collectId").value(collection.getCollectId());
			  stringer.key("novelObj").value(collection.getNovelObj());
			  stringer.key("userObj").value(collection.getUserObj());
			  stringer.key("collectTime").value(collection.getCollectTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ����ղأ���ȡ�ղز������������浽�½����ղض��� */ 
			Collection collection = new Collection();
			int collectId = Integer.parseInt(request.getParameter("collectId"));
			collection.setCollectId(collectId);
			int novelObj = Integer.parseInt(request.getParameter("novelObj"));
			collection.setNovelObj(novelObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			collection.setUserObj(userObj);
			String collectTime = new String(request.getParameter("collectTime").getBytes("iso-8859-1"), "UTF-8");
			collection.setCollectTime(collectTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = collectionDAO.AddCollection(collection);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���ղأ���ȡ�ղص��ղ�id*/
			int collectId = Integer.parseInt(request.getParameter("collectId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = collectionDAO.DeleteCollection(collectId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�����ղ�֮ǰ�ȸ���collectId��ѯĳ���ղ�*/
			int collectId = Integer.parseInt(request.getParameter("collectId"));
			Collection collection = collectionDAO.GetCollection(collectId);

			// �ͻ��˲�ѯ���ղض��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("collectId").value(collection.getCollectId());
			  stringer.key("novelObj").value(collection.getNovelObj());
			  stringer.key("userObj").value(collection.getUserObj());
			  stringer.key("collectTime").value(collection.getCollectTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �����ղأ���ȡ�ղز������������浽�½����ղض��� */ 
			Collection collection = new Collection();
			int collectId = Integer.parseInt(request.getParameter("collectId"));
			collection.setCollectId(collectId);
			int novelObj = Integer.parseInt(request.getParameter("novelObj"));
			collection.setNovelObj(novelObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			collection.setUserObj(userObj);
			String collectTime = new String(request.getParameter("collectTime").getBytes("iso-8859-1"), "UTF-8");
			collection.setCollectTime(collectTime);

			/* ����ҵ���ִ�и��²��� */
			String result = collectionDAO.UpdateCollection(collection);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
