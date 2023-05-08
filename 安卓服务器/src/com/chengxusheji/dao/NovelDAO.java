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
import com.chengxusheji.domain.Novel;

@Service @Transactional
public class NovelDAO {

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
    public void AddNovel(Novel novel) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(novel);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Novel> QueryNovelInfo(NovelClass novelClassObj,String novelName,String author,String publish,String publishDate,String tjFlag,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Novel novel where 1=1";
    	if(null != novelClassObj && novelClassObj.getClassId()!=0) hql += " and novel.novelClassObj.classId=" + novelClassObj.getClassId();
    	if(!novelName.equals("")) hql = hql + " and novel.novelName like '%" + novelName + "%'";
    	if(!author.equals("")) hql = hql + " and novel.author like '%" + author + "%'";
    	if(!publish.equals("")) hql = hql + " and novel.publish like '%" + publish + "%'";
    	if(!publishDate.equals("")) hql = hql + " and novel.publishDate like '%" + publishDate + "%'";
    	if(!tjFlag.equals("")) hql = hql + " and novel.tjFlag like '%" + tjFlag + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List novelList = q.list();
    	return (ArrayList<Novel>) novelList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Novel> QueryNovelInfo(NovelClass novelClassObj,String novelName,String author,String publish,String publishDate,String tjFlag) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Novel novel where 1=1";
    	if(null != novelClassObj && novelClassObj.getClassId()!=0) hql += " and novel.novelClassObj.classId=" + novelClassObj.getClassId();
    	if(!novelName.equals("")) hql = hql + " and novel.novelName like '%" + novelName + "%'";
    	if(!author.equals("")) hql = hql + " and novel.author like '%" + author + "%'";
    	if(!publish.equals("")) hql = hql + " and novel.publish like '%" + publish + "%'";
    	if(!publishDate.equals("")) hql = hql + " and novel.publishDate like '%" + publishDate + "%'";
    	if(!tjFlag.equals("")) hql = hql + " and novel.tjFlag like '%" + tjFlag + "%'";
    	Query q = s.createQuery(hql);
    	List novelList = q.list();
    	return (ArrayList<Novel>) novelList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Novel> QueryAllNovelInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Novel";
        Query q = s.createQuery(hql);
        List novelList = q.list();
        return (ArrayList<Novel>) novelList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(NovelClass novelClassObj,String novelName,String author,String publish,String publishDate,String tjFlag) {
        Session s = factory.getCurrentSession();
        String hql = "From Novel novel where 1=1";
        if(null != novelClassObj && novelClassObj.getClassId()!=0) hql += " and novel.novelClassObj.classId=" + novelClassObj.getClassId();
        if(!novelName.equals("")) hql = hql + " and novel.novelName like '%" + novelName + "%'";
        if(!author.equals("")) hql = hql + " and novel.author like '%" + author + "%'";
        if(!publish.equals("")) hql = hql + " and novel.publish like '%" + publish + "%'";
        if(!publishDate.equals("")) hql = hql + " and novel.publishDate like '%" + publishDate + "%'";
        if(!tjFlag.equals("")) hql = hql + " and novel.tjFlag like '%" + tjFlag + "%'";
        Query q = s.createQuery(hql);
        List novelList = q.list();
        recordNumber = novelList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Novel GetNovelByNovelId(int novelId) {
        Session s = factory.getCurrentSession();
        Novel novel = (Novel)s.get(Novel.class, novelId);
        return novel;
    }

    /*更新Novel信息*/
    public void UpdateNovel(Novel novel) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(novel);
    }

    /*删除Novel信息*/
    public void DeleteNovel (int novelId) throws Exception {
        Session s = factory.getCurrentSession();
        Object novel = s.load(Novel.class, novelId);
        s.delete(novel);
    }

}
