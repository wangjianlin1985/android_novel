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
	/* �����ղض��󣬽����ղص����ҵ�� */
	public String AddCollection(Collection collection) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в������ղ� */
			String sqlString = "insert into Collection(novelObj,userObj,collectTime) values (";
			sqlString += collection.getNovelObj() + ",";
			sqlString += "'" + collection.getUserObj() + "',";
			sqlString += "'" + collection.getCollectTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�ղ���ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ղ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���ղ� */
	public String DeleteCollection(int collectId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Collection where collectId=" + collectId;
			db.executeUpdate(sqlString);
			result = "�ղ�ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ղ�ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* �����ղ�id��ȡ���ղ� */
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
	/* �����ղ� */
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
			result = "�ղظ��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ղظ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
