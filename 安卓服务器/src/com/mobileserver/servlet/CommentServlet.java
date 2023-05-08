package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.CommentDAO;
import com.mobileserver.domain.Comment;

import org.json.JSONStringer;

public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*��������ҵ������*/
	private CommentDAO commentDAO = new CommentDAO();

	/*Ĭ�Ϲ��캯��*/
	public CommentServlet() {
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
			/*��ȡ��ѯ���۵Ĳ�����Ϣ*/
			int novelObj = 0;
			if (request.getParameter("novelObj") != null)
				novelObj = Integer.parseInt(request.getParameter("novelObj"));
			String content = request.getParameter("content");
			content = content == null ? "" : new String(request.getParameter(
					"content").getBytes("iso-8859-1"), "UTF-8");
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String commentTime = request.getParameter("commentTime");
			commentTime = commentTime == null ? "" : new String(request.getParameter(
					"commentTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�����۲�ѯ*/
			List<Comment> commentList = commentDAO.QueryComment(novelObj,content,userObj,commentTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Comments>").append("\r\n");
			for (int i = 0; i < commentList.size(); i++) {
				sb.append("	<Comment>").append("\r\n")
				.append("		<commentId>")
				.append(commentList.get(i).getCommentId())
				.append("</commentId>").append("\r\n")
				.append("		<novelObj>")
				.append(commentList.get(i).getNovelObj())
				.append("</novelObj>").append("\r\n")
				.append("		<content>")
				.append(commentList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<userObj>")
				.append(commentList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<commentTime>")
				.append(commentList.get(i).getCommentTime())
				.append("</commentTime>").append("\r\n")
				.append("	</Comment>").append("\r\n");
			}
			sb.append("</Comments>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Comment comment: commentList) {
				  stringer.object();
			  stringer.key("commentId").value(comment.getCommentId());
			  stringer.key("novelObj").value(comment.getNovelObj());
			  stringer.key("content").value(comment.getContent());
			  stringer.key("userObj").value(comment.getUserObj());
			  stringer.key("commentTime").value(comment.getCommentTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ������ۣ���ȡ���۲������������浽�½������۶��� */ 
			Comment comment = new Comment();
			int commentId = Integer.parseInt(request.getParameter("commentId"));
			comment.setCommentId(commentId);
			int novelObj = Integer.parseInt(request.getParameter("novelObj"));
			comment.setNovelObj(novelObj);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			comment.setContent(content);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			comment.setUserObj(userObj);
			String commentTime = new String(request.getParameter("commentTime").getBytes("iso-8859-1"), "UTF-8");
			comment.setCommentTime(commentTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = commentDAO.AddComment(comment);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����ۣ���ȡ���۵�����id*/
			int commentId = Integer.parseInt(request.getParameter("commentId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = commentDAO.DeleteComment(commentId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*��������֮ǰ�ȸ���commentId��ѯĳ������*/
			int commentId = Integer.parseInt(request.getParameter("commentId"));
			Comment comment = commentDAO.GetComment(commentId);

			// �ͻ��˲�ѯ�����۶��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("commentId").value(comment.getCommentId());
			  stringer.key("novelObj").value(comment.getNovelObj());
			  stringer.key("content").value(comment.getContent());
			  stringer.key("userObj").value(comment.getUserObj());
			  stringer.key("commentTime").value(comment.getCommentTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �������ۣ���ȡ���۲������������浽�½������۶��� */ 
			Comment comment = new Comment();
			int commentId = Integer.parseInt(request.getParameter("commentId"));
			comment.setCommentId(commentId);
			int novelObj = Integer.parseInt(request.getParameter("novelObj"));
			comment.setNovelObj(novelObj);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			comment.setContent(content);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			comment.setUserObj(userObj);
			String commentTime = new String(request.getParameter("commentTime").getBytes("iso-8859-1"), "UTF-8");
			comment.setCommentTime(commentTime);

			/* ����ҵ���ִ�и��²��� */
			String result = commentDAO.UpdateComment(comment);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
