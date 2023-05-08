package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.NovelClass;
import com.mobileserver.util.DB;

public class NovelClassDAO {

	public List<NovelClass> QueryNovelClass() {
		List<NovelClass> novelClassList = new ArrayList<NovelClass>();
		DB db = new DB();
		String sql = "select * from NovelClass where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				NovelClass novelClass = new NovelClass();
				novelClass.setClassId(rs.getInt("classId"));
				novelClass.setClassName(rs.getString("className"));
				novelClassList.add(novelClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return novelClassList;
	}
	/* ����С˵�����󣬽���С˵�������ҵ�� */
	public String AddNovelClass(NovelClass novelClass) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����С˵��� */
			String sqlString = "insert into NovelClass(className) values (";
			sqlString += "'" + novelClass.getClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "С˵�����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "С˵������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��С˵��� */
	public String DeleteNovelClass(int classId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from NovelClass where classId=" + classId;
			db.executeUpdate(sqlString);
			result = "С˵���ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "С˵���ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݷ���id��ȡ��С˵��� */
	public NovelClass GetNovelClass(int classId) {
		NovelClass novelClass = null;
		DB db = new DB();
		String sql = "select * from NovelClass where classId=" + classId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				novelClass = new NovelClass();
				novelClass.setClassId(rs.getInt("classId"));
				novelClass.setClassName(rs.getString("className"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return novelClass;
	}
	/* ����С˵��� */
	public String UpdateNovelClass(NovelClass novelClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update NovelClass set ";
			sql += "className='" + novelClass.getClassName() + "'";
			sql += " where classId=" + novelClass.getClassId();
			db.executeUpdate(sql);
			result = "С˵�����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "С˵������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
