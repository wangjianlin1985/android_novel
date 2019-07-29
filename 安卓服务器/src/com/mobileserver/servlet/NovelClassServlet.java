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

	/*构造小说类别业务层对象*/
	private NovelClassDAO novelClassDAO = new NovelClassDAO();

	/*默认构造函数*/
	public NovelClassServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询小说类别的参数信息*/

			/*调用业务逻辑层执行小说类别查询*/
			List<NovelClass> novelClassList = novelClassDAO.QueryNovelClass();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加小说类别：获取小说类别参数，参数保存到新建的小说类别对象 */ 
			NovelClass novelClass = new NovelClass();
			int classId = Integer.parseInt(request.getParameter("classId"));
			novelClass.setClassId(classId);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			novelClass.setClassName(className);

			/* 调用业务层执行添加操作 */
			String result = novelClassDAO.AddNovelClass(novelClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除小说类别：获取小说类别的分类id*/
			int classId = Integer.parseInt(request.getParameter("classId"));
			/*调用业务逻辑层执行删除操作*/
			String result = novelClassDAO.DeleteNovelClass(classId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新小说类别之前先根据classId查询某个小说类别*/
			int classId = Integer.parseInt(request.getParameter("classId"));
			NovelClass novelClass = novelClassDAO.GetNovelClass(classId);

			// 客户端查询的小说类别对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新小说类别：获取小说类别参数，参数保存到新建的小说类别对象 */ 
			NovelClass novelClass = new NovelClass();
			int classId = Integer.parseInt(request.getParameter("classId"));
			novelClass.setClassId(classId);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			novelClass.setClassName(className);

			/* 调用业务层执行更新操作 */
			String result = novelClassDAO.UpdateNovelClass(novelClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
