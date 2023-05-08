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
import com.chengxusheji.dao.NovelDAO;
import com.chengxusheji.domain.Novel;
import com.chengxusheji.dao.NovelClassDAO;
import com.chengxusheji.domain.NovelClass;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class NovelAction extends BaseAction {

	/*图片或文件字段novelPhoto参数接收*/
	private File novelPhotoFile;
	private String novelPhotoFileFileName;
	private String novelPhotoFileContentType;
	public File getNovelPhotoFile() {
		return novelPhotoFile;
	}
	public void setNovelPhotoFile(File novelPhotoFile) {
		this.novelPhotoFile = novelPhotoFile;
	}
	public String getNovelPhotoFileFileName() {
		return novelPhotoFileFileName;
	}
	public void setNovelPhotoFileFileName(String novelPhotoFileFileName) {
		this.novelPhotoFileFileName = novelPhotoFileFileName;
	}
	public String getNovelPhotoFileContentType() {
		return novelPhotoFileContentType;
	}
	public void setNovelPhotoFileContentType(String novelPhotoFileContentType) {
		this.novelPhotoFileContentType = novelPhotoFileContentType;
	}
	/*图片或文件字段novelFile参数接收*/
	private File novelFileFile;
	private String novelFileFileFileName;
	private String novelFileFileContentType;
	public File getNovelFileFile() {
		return novelFileFile;
	}
	public void setNovelFileFile(File novelFileFile) {
		this.novelFileFile = novelFileFile;
	}
	public String getNovelFileFileFileName() {
		return novelFileFileFileName;
	}
	public void setNovelFileFileFileName(String novelFileFileFileName) {
		this.novelFileFileFileName = novelFileFileFileName;
	}
	public String getNovelFileFileContentType() {
		return novelFileFileContentType;
	}
	public void setNovelFileFileContentType(String novelFileFileContentType) {
		this.novelFileFileContentType = novelFileFileContentType;
	}
    /*界面层需要查询的属性: 小说类别*/
    private NovelClass novelClassObj;
    public void setNovelClassObj(NovelClass novelClassObj) {
        this.novelClassObj = novelClassObj;
    }
    public NovelClass getNovelClassObj() {
        return this.novelClassObj;
    }

    /*界面层需要查询的属性: 小说名称*/
    private String novelName;
    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }
    public String getNovelName() {
        return this.novelName;
    }

    /*界面层需要查询的属性: 作者*/
    private String author;
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return this.author;
    }

    /*界面层需要查询的属性: 出版社*/
    private String publish;
    public void setPublish(String publish) {
        this.publish = publish;
    }
    public String getPublish() {
        return this.publish;
    }

    /*界面层需要查询的属性: 出版日期*/
    private String publishDate;
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getPublishDate() {
        return this.publishDate;
    }

    /*界面层需要查询的属性: 是否推荐*/
    private String tjFlag;
    public void setTjFlag(String tjFlag) {
        this.tjFlag = tjFlag;
    }
    public String getTjFlag() {
        return this.tjFlag;
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

    private int novelId;
    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }
    public int getNovelId() {
        return novelId;
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
    @Resource NovelClassDAO novelClassDAO;
    @Resource NovelDAO novelDAO;

    /*待操作的Novel对象*/
    private Novel novel;
    public void setNovel(Novel novel) {
        this.novel = novel;
    }
    public Novel getNovel() {
        return this.novel;
    }

    /*跳转到添加Novel视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的NovelClass信息*/
        List<NovelClass> novelClassList = novelClassDAO.QueryAllNovelClassInfo();
        ctx.put("novelClassList", novelClassList);
        return "add_view";
    }

    /*添加Novel信息*/
    @SuppressWarnings("deprecation")
    public String AddNovel() {
        ActionContext ctx = ActionContext.getContext();
        try {
            NovelClass novelClassObj = novelClassDAO.GetNovelClassByClassId(novel.getNovelClassObj().getClassId());
            novel.setNovelClassObj(novelClassObj);
            /*处理小说图片上传*/
            String novelPhotoPath = "upload/noimage.jpg"; 
       	 	if(novelPhotoFile != null)
       	 		novelPhotoPath = photoUpload(novelPhotoFile,novelPhotoFileContentType);
       	 	novel.setNovelPhoto(novelPhotoPath);
            /*处理小说文件上传*/
            String novelFilePath = "upload/noimage.jpg"; 
       	 	if(novelFileFile != null)
       	 		novelFilePath = photoUpload(novelFileFile,novelFileFileContentType);
       	 	novel.setNovelFile(novelFilePath);
            novelDAO.AddNovel(novel);
            ctx.put("message",  java.net.URLEncoder.encode("Novel添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Novel添加失败!"));
            return "error";
        }
    }

    /*查询Novel信息*/
    public String QueryNovel() {
        if(currentPage == 0) currentPage = 1;
        if(novelName == null) novelName = "";
        if(author == null) author = "";
        if(publish == null) publish = "";
        if(publishDate == null) publishDate = "";
        if(tjFlag == null) tjFlag = "";
        List<Novel> novelList = novelDAO.QueryNovelInfo(novelClassObj, novelName, author, publish, publishDate, tjFlag, currentPage);
        /*计算总的页数和总的记录数*/
        novelDAO.CalculateTotalPageAndRecordNumber(novelClassObj, novelName, author, publish, publishDate, tjFlag);
        /*获取到总的页码数目*/
        totalPage = novelDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = novelDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("novelList",  novelList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("novelClassObj", novelClassObj);
        List<NovelClass> novelClassList = novelClassDAO.QueryAllNovelClassInfo();
        ctx.put("novelClassList", novelClassList);
        ctx.put("novelName", novelName);
        ctx.put("author", author);
        ctx.put("publish", publish);
        ctx.put("publishDate", publishDate);
        ctx.put("tjFlag", tjFlag);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryNovelOutputToExcel() { 
        if(novelName == null) novelName = "";
        if(author == null) author = "";
        if(publish == null) publish = "";
        if(publishDate == null) publishDate = "";
        if(tjFlag == null) tjFlag = "";
        List<Novel> novelList = novelDAO.QueryNovelInfo(novelClassObj,novelName,author,publish,publishDate,tjFlag);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Novel信息记录"; 
        String[] headers = { "小说id","小说类别","小说名称","小说图片","作者","出版社","出版日期","页数","字数","是否推荐","阅读量"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<novelList.size();i++) {
        	Novel novel = novelList.get(i); 
        	dataset.add(new String[]{novel.getNovelId() + "",novel.getNovelClassObj().getClassName(),
novel.getNovelName(),novel.getNovelPhoto(),novel.getAuthor(),novel.getPublish(),new SimpleDateFormat("yyyy-MM-dd").format(novel.getPublishDate()),novel.getNovelPageNum() + "",novel.getWordsNum() + "",novel.getTjFlag(),novel.getReadCount() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"Novel.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Novel信息*/
    public String FrontQueryNovel() {
        if(currentPage == 0) currentPage = 1;
        if(novelName == null) novelName = "";
        if(author == null) author = "";
        if(publish == null) publish = "";
        if(publishDate == null) publishDate = "";
        if(tjFlag == null) tjFlag = "";
        List<Novel> novelList = novelDAO.QueryNovelInfo(novelClassObj, novelName, author, publish, publishDate, tjFlag, currentPage);
        /*计算总的页数和总的记录数*/
        novelDAO.CalculateTotalPageAndRecordNumber(novelClassObj, novelName, author, publish, publishDate, tjFlag);
        /*获取到总的页码数目*/
        totalPage = novelDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = novelDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("novelList",  novelList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("novelClassObj", novelClassObj);
        List<NovelClass> novelClassList = novelClassDAO.QueryAllNovelClassInfo();
        ctx.put("novelClassList", novelClassList);
        ctx.put("novelName", novelName);
        ctx.put("author", author);
        ctx.put("publish", publish);
        ctx.put("publishDate", publishDate);
        ctx.put("tjFlag", tjFlag);
        return "front_query_view";
    }

    /*查询要修改的Novel信息*/
    public String ModifyNovelQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键novelId获取Novel对象*/
        Novel novel = novelDAO.GetNovelByNovelId(novelId);

        List<NovelClass> novelClassList = novelClassDAO.QueryAllNovelClassInfo();
        ctx.put("novelClassList", novelClassList);
        ctx.put("novel",  novel);
        return "modify_view";
    }

    /*查询要修改的Novel信息*/
    public String FrontShowNovelQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键novelId获取Novel对象*/
        Novel novel = novelDAO.GetNovelByNovelId(novelId);

        List<NovelClass> novelClassList = novelClassDAO.QueryAllNovelClassInfo();
        ctx.put("novelClassList", novelClassList);
        ctx.put("novel",  novel);
        return "front_show_view";
    }

    /*更新修改Novel信息*/
    public String ModifyNovel() {
        ActionContext ctx = ActionContext.getContext();
        try {
            NovelClass novelClassObj = novelClassDAO.GetNovelClassByClassId(novel.getNovelClassObj().getClassId());
            novel.setNovelClassObj(novelClassObj);
            /*处理小说图片上传*/
            if(novelPhotoFile != null) {
            	String novelPhotoPath = photoUpload(novelPhotoFile,novelPhotoFileContentType);
            	novel.setNovelPhoto(novelPhotoPath);
            }
            /*处理小说文件上传*/
            if(novelFileFile != null) {
            	String novelFilePath = photoUpload(novelFileFile,novelFileFileContentType);
            	novel.setNovelFile(novelFilePath);
            }
            novelDAO.UpdateNovel(novel);
            ctx.put("message",  java.net.URLEncoder.encode("Novel信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Novel信息更新失败!"));
            return "error";
       }
   }

    /*删除Novel信息*/
    public String DeleteNovel() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            novelDAO.DeleteNovel(novelId);
            ctx.put("message",  java.net.URLEncoder.encode("Novel删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Novel删除失败!"));
            return "error";
        }
    }

}
