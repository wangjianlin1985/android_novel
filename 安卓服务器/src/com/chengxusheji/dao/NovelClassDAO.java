package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.NovelClass;

@Service @Transactional
public class NovelClassDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddNovelClass(NovelClass novelClass) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(novelClass);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<NovelClass> QueryNovelClassInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From NovelClass novelClass where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List novelClassList = q.list();
    	return (ArrayList<NovelClass>) novelClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<NovelClass> QueryNovelClassInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From NovelClass novelClass where 1=1";
    	Query q = s.createQuery(hql);
    	List novelClassList = q.list();
    	return (ArrayList<NovelClass>) novelClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<NovelClass> QueryAllNovelClassInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From NovelClass";
        Query q = s.createQuery(hql);
        List novelClassList = q.list();
        return (ArrayList<NovelClass>) novelClassList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From NovelClass novelClass where 1=1";
        Query q = s.createQuery(hql);
        List novelClassList = q.list();
        recordNumber = novelClassList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public NovelClass GetNovelClassByClassId(int classId) {
        Session s = factory.getCurrentSession();
        NovelClass novelClass = (NovelClass)s.get(NovelClass.class, classId);
        return novelClass;
    }

    /*更新NovelClass信息*/
    public void UpdateNovelClass(NovelClass novelClass) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(novelClass);
    }

    /*删除NovelClass信息*/
    public void DeleteNovelClass (int classId) throws Exception {
        Session s = factory.getCurrentSession();
        Object novelClass = s.load(NovelClass.class, classId);
        s.delete(novelClass);
    }

}
