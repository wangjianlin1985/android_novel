package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Collection;
import com.mobileserver.util.DB;

public class CollectionDAO {

	public List<Collection> QueryCollection(int novelObj,String userObj,String collectTime) {
		List<Collection> collectionList = new ArrayList<Collection>();
		DB db = new DB();
		String sql = "select * from Collection where 1=1";
		if (novelObj != 0)
			sql += " and novelObj=" + novelObj;
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!collectTime.equals(""))
			sql += " and collectTime like '%" + collectTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Collection collection = new Collection();
				collection.setCollectId(rs.getInt("collectId"));
				collection.setNovelObj(rs.getInt("novelObj"));
				collection.setUserObj(rs.getString("userObj"));
				collection.setCollectTime(rs.getString("collectTime"));
				collectionList.add(collection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return collectionList;
	}
	/* 传入收藏对象，进行收藏的添加业务 */
	public String AddCollection(Collection collection) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新收藏 */
			String sqlString = "insert into Collection(novelObj,userObj,collectTime) values (";
			sqlString += collection.getNovelObj() + ",";
			sqlString += "'" + collection.getUserObj() + "',";
			sqlString += "'" + collection.getCollectTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "收藏添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "收藏添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除收藏 */
	public String DeleteCollection(int collectId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Collection where collectId=" + collectId;
			db.executeUpdate(sqlString);
			result = "收藏删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "收藏删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据收藏id获取到收藏 */
	public Collection GetCollection(int collectId) {
		Collection collection = null;
		DB db = new DB();
		String sql = "select * from Collection where collectId=" + collectId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				collection = new Collection();
				collection.setCollectId(rs.getInt("collectId"));
				collection.setNovelObj(rs.getInt("novelObj"));
				collection.setUserObj(rs.getString("userObj"));
				collection.setCollectTime(rs.getString("collectTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return collection;
	}
	/* 更新收藏 */
	public String UpdateCollection(Collection collection) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Collection set ";
			sql += "novelObj=" + collection.getNovelObj() + ",";
			sql += "userObj='" + collection.getUserObj() + "',";
			sql += "collectTime='" + collection.getCollectTime() + "'";
			sql += " where collectId=" + collection.getCollectId();
			db.executeUpdate(sql);
			result = "收藏更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "收藏更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
