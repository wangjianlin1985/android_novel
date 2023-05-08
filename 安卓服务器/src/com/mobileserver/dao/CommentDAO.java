package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Comment;
import com.mobileserver.util.DB;

public class CommentDAO {

	public List<Comment> QueryComment(int novelObj,String content,String userObj,String commentTime) {
		List<Comment> commentList = new ArrayList<Comment>();
		DB db = new DB();
		String sql = "select * from Comment where 1=1";
		if (novelObj != 0)
			sql += " and novelObj=" + novelObj;
		if (!content.equals(""))
			sql += " and content like '%" + content + "%'";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!commentTime.equals(""))
			sql += " and commentTime like '%" + commentTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setCommentId(rs.getInt("commentId"));
				comment.setNovelObj(rs.getInt("novelObj"));
				comment.setContent(rs.getString("content"));
				comment.setUserObj(rs.getString("userObj"));
				comment.setCommentTime(rs.getString("commentTime"));
				commentList.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return commentList;
	}
	/* �������۶��󣬽������۵����ҵ�� */
	public String AddComment(Comment comment) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в��������� */
			String sqlString = "insert into Comment(novelObj,content,userObj,commentTime) values (";
			sqlString += comment.getNovelObj() + ",";
			sqlString += "'" + comment.getContent() + "',";
			sqlString += "'" + comment.getUserObj() + "',";
			sqlString += "'" + comment.getCommentTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������ */
	public String DeleteComment(int commentId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Comment where commentId=" + commentId;
			db.executeUpdate(sqlString);
			result = "����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ��������id��ȡ������ */
	public Comment GetComment(int commentId) {
		Comment comment = null;
		DB db = new DB();
		String sql = "select * from Comment where commentId=" + commentId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				comment = new Comment();
				comment.setCommentId(rs.getInt("commentId"));
				comment.setNovelObj(rs.getInt("novelObj"));
				comment.setContent(rs.getString("content"));
				comment.setUserObj(rs.getString("userObj"));
				comment.setCommentTime(rs.getString("commentTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return comment;
	}
	/* �������� */
	public String UpdateComment(Comment comment) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Comment set ";
			sql += "novelObj=" + comment.getNovelObj() + ",";
			sql += "content='" + comment.getContent() + "',";
			sql += "userObj='" + comment.getUserObj() + "',";
			sql += "commentTime='" + comment.getCommentTime() + "'";
			sql += " where commentId=" + comment.getCommentId();
			db.executeUpdate(sql);
			result = "���۸��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���۸���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
