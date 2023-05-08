package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.NovelDAO;
import com.mobileserver.domain.Novel;

import org.json.JSONStringer;

public class NovelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造小说业务层对象*/
	private NovelDAO novelDAO = new NovelDAO();

	/*默认构造函数*/
	public NovelServlet() {
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
			/*获取查询小说的参数信息*/
			int novelClassObj = 0;
			if (request.getParameter("novelClassObj") != null)
				novelClassObj = Integer.parseInt(request.getParameter("novelClassObj"));
			String novelName = request.getParameter("novelName");
			novelName = novelName == null ? "" : new String(request.getParameter(
					"novelName").getBytes("iso-8859-1"), "UTF-8");
			String author = request.getParameter("author");
			author = author == null ? "" : new String(request.getParameter(
					"author").getBytes("iso-8859-1"), "UTF-8");
			String publish = request.getParameter("publish");
			publish = publish == null ? "" : new String(request.getParameter(
					"publish").getBytes("iso-8859-1"), "UTF-8");
			Timestamp publishDate = null;
			if (request.getParameter("publishDate") != null)
				publishDate = Timestamp.valueOf(request.getParameter("publishDate"));
			String tjFlag = request.getParameter("tjFlag");
			tjFlag = tjFlag == null ? "" : new String(request.getParameter(
					"tjFlag").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行小说查询*/
			List<Novel> novelList = novelDAO.QueryNovel(novelClassObj,novelName,author,publish,publishDate,tjFlag);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Novels>").append("\r\n");
			for (int i = 0; i < novelList.size(); i++) {
				sb.append("	<Novel>").append("\r\n")
				.append("		<novelId>")
				.append(novelList.get(i).getNovelId())
				.append("</novelId>").append("\r\n")
				.append("		<novelClassObj>")
				.append(novelList.get(i).getNovelClassObj())
				.append("</novelClassObj>").append("\r\n")
				.append("		<novelName>")
				.append(novelList.get(i).getNovelName())
				.append("</novelName>").append("\r\n")
				.append("		<novelPhoto>")
				.append(novelList.get(i).getNovelPhoto())
				.append("</novelPhoto>").append("\r\n")
				.append("		<author>")
				.append(novelList.get(i).getAuthor())
				.append("</author>").append("\r\n")
				.append("		<publish>")
				.append(novelList.get(i).getPublish())
				.append("</publish>").append("\r\n")
				.append("		<publishDate>")
				.append(novelList.get(i).getPublishDate())
				.append("</publishDate>").append("\r\n")
				.append("		<novelPageNum>")
				.append(novelList.get(i).getNovelPageNum())
				.append("</novelPageNum>").append("\r\n")
				.append("		<wordsNum>")
				.append(novelList.get(i).getWordsNum())
				.append("</wordsNum>").append("\r\n")
				.append("		<novelFile>")
				.append(novelList.get(i).getNovelFile())
				.append("</novelFile>").append("\r\n")
				.append("		<tjFlag>")
				.append(novelList.get(i).getTjFlag())
				.append("</tjFlag>").append("\r\n")
				.append("		<readCount>")
				.append(novelList.get(i).getReadCount())
				.append("</readCount>").append("\r\n")
				.append("		<novelDesc>")
				.append(novelList.get(i).getNovelDesc())
				.append("</novelDesc>").append("\r\n")
				.append("	</Novel>").append("\r\n");
			}
			sb.append("</Novels>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Novel novel: novelList) {
				  stringer.object();
			  stringer.key("novelId").value(novel.getNovelId());
			  stringer.key("novelClassObj").value(novel.getNovelClassObj());
			  stringer.key("novelName").value(novel.getNovelName());
			  stringer.key("novelPhoto").value(novel.getNovelPhoto());
			  stringer.key("author").value(novel.getAuthor());
			  stringer.key("publish").value(novel.getPublish());
			  stringer.key("publishDate").value(novel.getPublishDate());
			  stringer.key("novelPageNum").value(novel.getNovelPageNum());
			  stringer.key("wordsNum").value(novel.getWordsNum());
			  stringer.key("novelFile").value(novel.getNovelFile());
			  stringer.key("tjFlag").value(novel.getTjFlag());
			  stringer.key("readCount").value(novel.getReadCount());
			  stringer.key("novelDesc").value(novel.getNovelDesc());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加小说：获取小说参数，参数保存到新建的小说对象 */ 
			Novel novel = new Novel();
			int novelId = Integer.parseInt(request.getParameter("novelId"));
			novel.setNovelId(novelId);
			int novelClassObj = Integer.parseInt(request.getParameter("novelClassObj"));
			novel.setNovelClassObj(novelClassObj);
			String novelName = new String(request.getParameter("novelName").getBytes("iso-8859-1"), "UTF-8");
			novel.setNovelName(novelName);
			String novelPhoto = new String(request.getParameter("novelPhoto").getBytes("iso-8859-1"), "UTF-8");
			novel.setNovelPhoto(novelPhoto);
			String author = new String(request.getParameter("author").getBytes("iso-8859-1"), "UTF-8");
			novel.setAuthor(author);
			String publish = new String(request.getParameter("publish").getBytes("iso-8859-1"), "UTF-8");
			novel.setPublish(publish);
			Timestamp publishDate = Timestamp.valueOf(request.getParameter("publishDate"));
			novel.setPublishDate(publishDate);
			int novelPageNum = Integer.parseInt(request.getParameter("novelPageNum"));
			novel.setNovelPageNum(novelPageNum);
			int wordsNum = Integer.parseInt(request.getParameter("wordsNum"));
			novel.setWordsNum(wordsNum);
			String novelFile = new String(request.getParameter("novelFile").getBytes("iso-8859-1"), "UTF-8");
			novel.setNovelFile(novelFile);
			String tjFlag = new String(request.getParameter("tjFlag").getBytes("iso-8859-1"), "UTF-8");
			novel.setTjFlag(tjFlag);
			int readCount = Integer.parseInt(request.getParameter("readCount"));
			novel.setReadCount(readCount);
			String novelDesc = new String(request.getParameter("novelDesc").getBytes("iso-8859-1"), "UTF-8");
			novel.setNovelDesc(novelDesc);

			/* 调用业务层执行添加操作 */
			String result = novelDAO.AddNovel(novel);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除小说：获取小说的小说id*/
			int novelId = Integer.parseInt(request.getParameter("novelId"));
			/*调用业务逻辑层执行删除操作*/
			String result = novelDAO.DeleteNovel(novelId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新小说之前先根据novelId查询某个小说*/
			int novelId = Integer.parseInt(request.getParameter("novelId"));
			Novel novel = novelDAO.GetNovel(novelId);

			// 客户端查询的小说对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("novelId").value(novel.getNovelId());
			  stringer.key("novelClassObj").value(novel.getNovelClassObj());
			  stringer.key("novelName").value(novel.getNovelName());
			  stringer.key("novelPhoto").value(novel.getNovelPhoto());
			  stringer.key("author").value(novel.getAuthor());
			  stringer.key("publish").value(novel.getPublish());
			  stringer.key("publishDate").value(novel.getPublishDate());
			  stringer.key("novelPageNum").value(novel.getNovelPageNum());
			  stringer.key("wordsNum").value(novel.getWordsNum());
			  stringer.key("novelFile").value(novel.getNovelFile());
			  stringer.key("tjFlag").value(novel.getTjFlag());
			  stringer.key("readCount").value(novel.getReadCount());
			  stringer.key("novelDesc").value(novel.getNovelDesc());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新小说：获取小说参数，参数保存到新建的小说对象 */ 
			Novel novel = new Novel();
			int novelId = Integer.parseInt(request.getParameter("novelId"));
			novel.setNovelId(novelId);
			int novelClassObj = Integer.parseInt(request.getParameter("novelClassObj"));
			novel.setNovelClassObj(novelClassObj);
			String novelName = new String(request.getParameter("novelName").getBytes("iso-8859-1"), "UTF-8");
			novel.setNovelName(novelName);
			String novelPhoto = new String(request.getParameter("novelPhoto").getBytes("iso-8859-1"), "UTF-8");
			novel.setNovelPhoto(novelPhoto);
			String author = new String(request.getParameter("author").getBytes("iso-8859-1"), "UTF-8");
			novel.setAuthor(author);
			String publish = new String(request.getParameter("publish").getBytes("iso-8859-1"), "UTF-8");
			novel.setPublish(publish);
			Timestamp publishDate = Timestamp.valueOf(request.getParameter("publishDate"));
			novel.setPublishDate(publishDate);
			int novelPageNum = Integer.parseInt(request.getParameter("novelPageNum"));
			novel.setNovelPageNum(novelPageNum);
			int wordsNum = Integer.parseInt(request.getParameter("wordsNum"));
			novel.setWordsNum(wordsNum);
			String novelFile = new String(request.getParameter("novelFile").getBytes("iso-8859-1"), "UTF-8");
			novel.setNovelFile(novelFile);
			String tjFlag = new String(request.getParameter("tjFlag").getBytes("iso-8859-1"), "UTF-8");
			novel.setTjFlag(tjFlag);
			int readCount = Integer.parseInt(request.getParameter("readCount"));
			novel.setReadCount(readCount);
			String novelDesc = new String(request.getParameter("novelDesc").getBytes("iso-8859-1"), "UTF-8");
			novel.setNovelDesc(novelDesc);

			/* 调用业务层执行更新操作 */
			String result = novelDAO.UpdateNovel(novel);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
