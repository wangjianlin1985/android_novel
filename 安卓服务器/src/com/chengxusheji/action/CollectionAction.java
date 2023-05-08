package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.CollectionDAO;
import com.chengxusheji.domain.Collection;
import com.chengxusheji.dao.NovelDAO;
import com.chengxusheji.domain.Novel;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CollectionAction extends BaseAction {

    /*界面层需要查询的属性: 被收藏小说*/
    private Novel novelObj;
    public void setNovelObj(Novel novelObj) {
        this.novelObj = novelObj;
    }
    public Novel getNovelObj() {
        return this.novelObj;
    }

    /*界面层需要查询的属性: 收藏用户*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 收藏时间*/
    private String collectTime;
    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }
    public String getCollectTime() {
        return this.collectTime;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int collectId;
    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }
    public int getCollectId() {
        return collectId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource NovelDAO novelDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource CollectionDAO collectionDAO;

    /*待操作的Collection对象*/
    private Collection collection;
    public void setCollection(Collection collection) {
        this.collection = collection;
    }
    public Collection getCollection() {
        return this.collection;
    }

    /*跳转到添加Collection视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Novel信息*/
        List<Novel> novelList = novelDAO.QueryAllNovelInfo();
        ctx.put("novelList", novelList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加Collection信息*/
    @SuppressWarnings("deprecation")
    public String AddCollection() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Novel novelObj = novelDAO.GetNovelByNovelId(collection.getNovelObj().getNovelId());
            collection.setNovelObj(novelObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(collection.getUserObj().getUser_name());
            collection.setUserObj(userObj);
            collectionDAO.AddCollection(collection);
            ctx.put("message",  java.net.URLEncoder.encode("Collection添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Collection添加失败!"));
            return "error";
        }
    }

    /*查询Collection信息*/
    public String QueryCollection() {
        if(currentPage == 0) currentPage = 1;
        if(collectTime == null) collectTime = "";
        List<Collection> collectionList = collectionDAO.QueryCollectionInfo(novelObj, userObj, collectTime, currentPage);
        /*计算总的页数和总的记录数*/
        collectionDAO.CalculateTotalPageAndRecordNumber(novelObj, userObj, collectTime);
        /*获取到总的页码数目*/
        totalPage = collectionDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = collectionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("collectionList",  collectionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("novelObj", novelObj);
        List<Novel> novelList = novelDAO.QueryAllNovelInfo();
        ctx.put("novelList", novelList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("collectTime", collectTime);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryCollectionOutputToExcel() { 
        if(collectTime == null) collectTime = "";
        List<Collection> collectionList = collectionDAO.QueryCollectionInfo(novelObj,userObj,collectTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Collection信息记录"; 
        String[] headers = { "收藏id","被收藏小说","收藏用户","收藏时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<collectionList.size();i++) {
        	Collection collection = collectionList.get(i); 
        	dataset.add(new String[]{collection.getCollectId() + "",collection.getNovelObj().getNovelName(),
collection.getUserObj().getName(),
collection.getCollectTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Collection.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询Collection信息*/
    public String FrontQueryCollection() {
        if(currentPage == 0) currentPage = 1;
        if(collectTime == null) collectTime = "";
        List<Collection> collectionList = collectionDAO.QueryCollectionInfo(novelObj, userObj, collectTime, currentPage);
        /*计算总的页数和总的记录数*/
        collectionDAO.CalculateTotalPageAndRecordNumber(novelObj, userObj, collectTime);
        /*获取到总的页码数目*/
        totalPage = collectionDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = collectionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("collectionList",  collectionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("novelObj", novelObj);
        List<Novel> novelList = novelDAO.QueryAllNovelInfo();
        ctx.put("novelList", novelList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("collectTime", collectTime);
        return "front_query_view";
    }

    /*查询要修改的Collection信息*/
    public String ModifyCollectionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键collectId获取Collection对象*/
        Collection collection = collectionDAO.GetCollectionByCollectId(collectId);

        List<Novel> novelList = novelDAO.QueryAllNovelInfo();
        ctx.put("novelList", novelList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("collection",  collection);
        return "modify_view";
    }

    /*查询要修改的Collection信息*/
    public String FrontShowCollectionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键collectId获取Collection对象*/
        Collection collection = collectionDAO.GetCollectionByCollectId(collectId);

        List<Novel> novelList = novelDAO.QueryAllNovelInfo();
        ctx.put("novelList", novelList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("collection",  collection);
        return "front_show_view";
    }

    /*更新修改Collection信息*/
    public String ModifyCollection() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Novel novelObj = novelDAO.GetNovelByNovelId(collection.getNovelObj().getNovelId());
            collection.setNovelObj(novelObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(collection.getUserObj().getUser_name());
            collection.setUserObj(userObj);
            collectionDAO.UpdateCollection(collection);
            ctx.put("message",  java.net.URLEncoder.encode("Collection信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Collection信息更新失败!"));
            return "error";
       }
   }

    /*删除Collection信息*/
    public String DeleteCollection() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            collectionDAO.DeleteCollection(collectId);
            ctx.put("message",  java.net.URLEncoder.encode("Collection删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Collection删除失败!"));
            return "error";
        }
    }

}
