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

	/*构造收藏业务层对象*/
	private CollectionDAO collectionDAO = new CollectionDAO();

	/*默认构造函数*/
	public CollectionServlet() {
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
			/*获取查询收藏的参数信息*/
			int novelObj = 0;
			if (request.getParameter("novelObj") != null)
				novelObj = Integer.parseInt(request.getParameter("novelObj"));
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String collectTime = request.getParameter("collectTime");
			collectTime = collectTime == null ? "" : new String(request.getParameter(
					"collectTime").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行收藏查询*/
			List<Collection> collectionList = collectionDAO.QueryCollection(novelObj,userObj,collectTime);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加收藏：获取收藏参数，参数保存到新建的收藏对象 */ 
			Collection collection = new Collection();
			int collectId = Integer.parseInt(request.getParameter("collectId"));
			collection.setCollectId(collectId);
			int novelObj = Integer.parseInt(request.getParameter("novelObj"));
			collection.setNovelObj(novelObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			collection.setUserObj(userObj);
			String collectTime = new String(request.getParameter("collectTime").getBytes("iso-8859-1"), "UTF-8");
			collection.setCollectTime(collectTime);

			/* 调用业务层执行添加操作 */
			String result = collectionDAO.AddCollection(collection);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除收藏：获取收藏的收藏id*/
			int collectId = Integer.parseInt(request.getParameter("collectId"));
			/*调用业务逻辑层执行删除操作*/
			String result = collectionDAO.DeleteCollection(collectId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新收藏之前先根据collectId查询某个收藏*/
			int collectId = Integer.parseInt(request.getParameter("collectId"));
			Collection collection = collectionDAO.GetCollection(collectId);

			// 客户端查询的收藏对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新收藏：获取收藏参数，参数保存到新建的收藏对象 */ 
			Collection collection = new Collection();
			int collectId = Integer.parseInt(request.getParameter("collectId"));
			collection.setCollectId(collectId);
			int novelObj = Integer.parseInt(request.getParameter("novelObj"));
			collection.setNovelObj(novelObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			collection.setUserObj(userObj);
			String collectTime = new String(request.getParameter("collectTime").getBytes("iso-8859-1"), "UTF-8");
			collection.setCollectTime(collectTime);

			/* 调用业务层执行更新操作 */
			String result = collectionDAO.UpdateCollection(collection);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
