package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.NovelClassDAO;
import com.mobileserver.domain.NovelClass;

import org.json.JSONStringer;

public class NovelClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����С˵���ҵ������*/
	private NovelClassDAO novelClassDAO = new NovelClassDAO();

	/*Ĭ�Ϲ��캯��*/
	public NovelClassServlet() {
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
			/*��ȡ��ѯС˵���Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ��С˵����ѯ*/
			List<NovelClass> novelClassList = novelClassDAO.QueryNovelClass();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<NovelClasss>").append("\r\n");
			for (int i = 0; i < novelClassList.size(); i++) {
				sb.append("	<NovelClass>").append("\r\n")
				.append("		<classId>")
				.append(novelClassList.get(i).getClassId())
				.append("</classId>").append("\r\n")
				.append("		<className>")
				.append(novelClassList.get(i).getClassName())
				.append("</className>").append("\r\n")
				.append("	</NovelClass>").append("\r\n");
			}
			sb.append("</NovelClasss>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(NovelClass novelClass: novelClassList) {
				  stringer.object();
			  stringer.key("classId").value(novelClass.getClassId());
			  stringer.key("className").value(novelClass.getClassName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���С˵��𣺻�ȡС˵���������������浽�½���С˵������ */ 
			NovelClass novelClass = new NovelClass();
			int classId = Integer.parseInt(request.getParameter("classId"));
			novelClass.setClassId(classId);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			novelClass.setClassName(className);

			/* ����ҵ���ִ����Ӳ��� */
			String result = novelClassDAO.AddNovelClass(novelClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��С˵��𣺻�ȡС˵���ķ���id*/
			int classId = Integer.parseInt(request.getParameter("classId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = novelClassDAO.DeleteNovelClass(classId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����С˵���֮ǰ�ȸ���classId��ѯĳ��С˵���*/
			int classId = Integer.parseInt(request.getParameter("classId"));
			NovelClass novelClass = novelClassDAO.GetNovelClass(classId);

			// �ͻ��˲�ѯ��С˵�����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("classId").value(novelClass.getClassId());
			  stringer.key("className").value(novelClass.getClassName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����С˵��𣺻�ȡС˵���������������浽�½���С˵������ */ 
			NovelClass novelClass = new NovelClass();
			int classId = Integer.parseInt(request.getParameter("classId"));
			novelClass.setClassId(classId);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			novelClass.setClassName(className);

			/* ����ҵ���ִ�и��²��� */
			String result = novelClassDAO.UpdateNovelClass(novelClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
