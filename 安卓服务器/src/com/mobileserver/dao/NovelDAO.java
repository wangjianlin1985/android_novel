package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Novel;
import com.mobileserver.util.DB;

public class NovelDAO {

	public List<Novel> QueryNovel(int novelClassObj,String novelName,String author,String publish,Timestamp publishDate,String tjFlag) {
		List<Novel> novelList = new ArrayList<Novel>();
		DB db = new DB();
		String sql = "select * from Novel where 1=1";
		if (novelClassObj != 0)
			sql += " and novelClassObj=" + novelClassObj;
		if (!novelName.equals(""))
			sql += " and novelName like '%" + novelName + "%'";
		if (!author.equals(""))
			sql += " and author like '%" + author + "%'";
		if (!publish.equals(""))
			sql += " and publish like '%" + publish + "%'";
		if(publishDate!=null)
			sql += " and publishDate='" + publishDate + "'";
		if (!tjFlag.equals(""))
			sql += " and tjFlag like '%" + tjFlag + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Novel novel = new Novel();
				novel.setNovelId(rs.getInt("novelId"));
				novel.setNovelClassObj(rs.getInt("novelClassObj"));
				novel.setNovelName(rs.getString("novelName"));
				novel.setNovelPhoto(rs.getString("novelPhoto"));
				novel.setAuthor(rs.getString("author"));
				novel.setPublish(rs.getString("publish"));
				novel.setPublishDate(rs.getTimestamp("publishDate"));
				novel.setNovelPageNum(rs.getInt("novelPageNum"));
				novel.setWordsNum(rs.getInt("wordsNum"));
				novel.setNovelFile(rs.getString("novelFile"));
				novel.setTjFlag(rs.getString("tjFlag"));
				novel.setReadCount(rs.getInt("readCount"));
				novel.setNovelDesc(rs.getString("novelDesc"));
				novelList.add(novel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return novelList;
	}
	/* 传入小说对象，进行小说的添加业务 */
	public String AddNovel(Novel novel) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新小说 */
			String sqlString = "insert into Novel(novelClassObj,novelName,novelPhoto,author,publish,publishDate,novelPageNum,wordsNum,novelFile,tjFlag,readCount,novelDesc) values (";
			sqlString += novel.getNovelClassObj() + ",";
			sqlString += "'" + novel.getNovelName() + "',";
			sqlString += "'" + novel.getNovelPhoto() + "',";
			sqlString += "'" + novel.getAuthor() + "',";
			sqlString += "'" + novel.getPublish() + "',";
			sqlString += "'" + novel.getPublishDate() + "',";
			sqlString += novel.getNovelPageNum() + ",";
			sqlString += novel.getWordsNum() + ",";
			sqlString += "'" + novel.getNovelFile() + "',";
			sqlString += "'" + novel.getTjFlag() + "',";
			sqlString += novel.getReadCount() + ",";
			sqlString += "'" + novel.getNovelDesc() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "小说添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "小说添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除小说 */
	public String DeleteNovel(int novelId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Novel where novelId=" + novelId;
			db.executeUpdate(sqlString);
			result = "小说删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "小说删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据小说id获取到小说 */
	public Novel GetNovel(int novelId) {
		Novel novel = null;
		DB db = new DB();
		String sql = "select * from Novel where novelId=" + novelId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				novel = new Novel();
				novel.setNovelId(rs.getInt("novelId"));
				novel.setNovelClassObj(rs.getInt("novelClassObj"));
				novel.setNovelName(rs.getString("novelName"));
				novel.setNovelPhoto(rs.getString("novelPhoto"));
				novel.setAuthor(rs.getString("author"));
				novel.setPublish(rs.getString("publish"));
				novel.setPublishDate(rs.getTimestamp("publishDate"));
				novel.setNovelPageNum(rs.getInt("novelPageNum"));
				novel.setWordsNum(rs.getInt("wordsNum"));
				novel.setNovelFile(rs.getString("novelFile"));
				novel.setTjFlag(rs.getString("tjFlag"));
				novel.setReadCount(rs.getInt("readCount"));
				novel.setNovelDesc(rs.getString("novelDesc"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return novel;
	}
	/* 更新小说 */
	public String UpdateNovel(Novel novel) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Novel set ";
			sql += "novelClassObj=" + novel.getNovelClassObj() + ",";
			sql += "novelName='" + novel.getNovelName() + "',";
			sql += "novelPhoto='" + novel.getNovelPhoto() + "',";
			sql += "author='" + novel.getAuthor() + "',";
			sql += "publish='" + novel.getPublish() + "',";
			sql += "publishDate='" + novel.getPublishDate() + "',";
			sql += "novelPageNum=" + novel.getNovelPageNum() + ",";
			sql += "wordsNum=" + novel.getWordsNum() + ",";
			sql += "novelFile='" + novel.getNovelFile() + "',";
			sql += "tjFlag='" + novel.getTjFlag() + "',";
			sql += "readCount=" + novel.getReadCount() + ",";
			sql += "novelDesc='" + novel.getNovelDesc() + "'";
			sql += " where novelId=" + novel.getNovelId();
			db.executeUpdate(sql);
			result = "小说更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "小说更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
