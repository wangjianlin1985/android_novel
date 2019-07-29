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
	/* 传入小说类别对象，进行小说类别的添加业务 */
	public String AddNovelClass(NovelClass novelClass) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新小说类别 */
			String sqlString = "insert into NovelClass(className) values (";
			sqlString += "'" + novelClass.getClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "小说类别添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "小说类别添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除小说类别 */
	public String DeleteNovelClass(int classId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from NovelClass where classId=" + classId;
			db.executeUpdate(sqlString);
			result = "小说类别删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "小说类别删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据分类id获取到小说类别 */
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
	/* 更新小说类别 */
	public String UpdateNovelClass(NovelClass novelClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update NovelClass set ";
			sql += "className='" + novelClass.getClassName() + "'";
			sql += " where classId=" + novelClass.getClassId();
			db.executeUpdate(sql);
			result = "小说类别更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "小说类别更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
