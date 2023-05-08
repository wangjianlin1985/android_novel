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

	/*ͼƬ���ļ��ֶ�novelPhoto��������*/
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
	/*ͼƬ���ļ��ֶ�novelFile��������*/
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
    /*�������Ҫ��ѯ������: С˵���*/
    private NovelClass novelClassObj;
    public void setNovelClassObj(NovelClass novelClassObj) {
        this.novelClassObj = novelClassObj;
    }
    public NovelClass getNovelClassObj() {
        return this.novelClassObj;
    }

    /*�������Ҫ��ѯ������: С˵����*/
    private String novelName;
    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }
    public String getNovelName() {
        return this.novelName;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String author;
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return this.author;
    }

    /*�������Ҫ��ѯ������: ������*/
    private String publish;
    public void setPublish(String publish) {
        this.publish = publish;
    }
    public String getPublish() {
        return this.publish;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String publishDate;
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getPublishDate() {
        return this.publishDate;
    }

    /*�������Ҫ��ѯ������: �Ƿ��Ƽ�*/
    private String tjFlag;
    public void setTjFlag(String tjFlag) {
        this.tjFlag = tjFlag;
    }
    public String getTjFlag() {
        return this.tjFlag;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource NovelClassDAO novelClassDAO;
    @Resource NovelDAO novelDAO;

    /*��������Novel����*/
    private Novel novel;
    public void setNovel(Novel novel) {
        this.novel = novel;
    }
    public Novel getNovel() {
        return this.novel;
    }

    /*��ת�����Novel��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�NovelClass��Ϣ*/
        List<NovelClass> novelClassList = novelClassDAO.QueryAllNovelClassInfo();
        ctx.put("novelClassList", novelClassList);
        return "add_view";
    }

    /*���Novel��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddNovel() {
        ActionContext ctx = ActionContext.getContext();
        try {
            NovelClass novelClassObj = novelClassDAO.GetNovelClassByClassId(novel.getNovelClassObj().getClassId());
            novel.setNovelClassObj(novelClassObj);
            /*����С˵ͼƬ�ϴ�*/
            String novelPhotoPath = "upload/noimage.jpg"; 
       	 	if(novelPhotoFile != null)
       	 		novelPhotoPath = photoUpload(novelPhotoFile,novelPhotoFileContentType);
       	 	novel.setNovelPhoto(novelPhotoPath);
            /*����С˵�ļ��ϴ�*/
            String novelFilePath = "upload/noimage.jpg"; 
       	 	if(novelFileFile != null)
       	 		novelFilePath = photoUpload(novelFileFile,novelFileFileContentType);
       	 	novel.setNovelFile(novelFilePath);
            novelDAO.AddNovel(novel);
            ctx.put("message",  java.net.URLEncoder.encode("Novel��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Novel���ʧ��!"));
            return "error";
        }
    }

    /*��ѯNovel��Ϣ*/
    public String QueryNovel() {
        if(currentPage == 0) currentPage = 1;
        if(novelName == null) novelName = "";
        if(author == null) author = "";
        if(publish == null) publish = "";
        if(publishDate == null) publishDate = "";
        if(tjFlag == null) tjFlag = "";
        List<Novel> novelList = novelDAO.QueryNovelInfo(novelClassObj, novelName, author, publish, publishDate, tjFlag, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        novelDAO.CalculateTotalPageAndRecordNumber(novelClassObj, novelName, author, publish, publishDate, tjFlag);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = novelDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryNovelOutputToExcel() { 
        if(novelName == null) novelName = "";
        if(author == null) author = "";
        if(publish == null) publish = "";
        if(publishDate == null) publishDate = "";
        if(tjFlag == null) tjFlag = "";
        List<Novel> novelList = novelDAO.QueryNovelInfo(novelClassObj,novelName,author,publish,publishDate,tjFlag);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Novel��Ϣ��¼"; 
        String[] headers = { "С˵id","С˵���","С˵����","С˵ͼƬ","����","������","��������","ҳ��","����","�Ƿ��Ƽ�","�Ķ���"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Novel.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯNovel��Ϣ*/
    public String FrontQueryNovel() {
        if(currentPage == 0) currentPage = 1;
        if(novelName == null) novelName = "";
        if(author == null) author = "";
        if(publish == null) publish = "";
        if(publishDate == null) publishDate = "";
        if(tjFlag == null) tjFlag = "";
        List<Novel> novelList = novelDAO.QueryNovelInfo(novelClassObj, novelName, author, publish, publishDate, tjFlag, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        novelDAO.CalculateTotalPageAndRecordNumber(novelClassObj, novelName, author, publish, publishDate, tjFlag);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = novelDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Novel��Ϣ*/
    public String ModifyNovelQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������novelId��ȡNovel����*/
        Novel novel = novelDAO.GetNovelByNovelId(novelId);

        List<NovelClass> novelClassList = novelClassDAO.QueryAllNovelClassInfo();
        ctx.put("novelClassList", novelClassList);
        ctx.put("novel",  novel);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Novel��Ϣ*/
    public String FrontShowNovelQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������novelId��ȡNovel����*/
        Novel novel = novelDAO.GetNovelByNovelId(novelId);

        List<NovelClass> novelClassList = novelClassDAO.QueryAllNovelClassInfo();
        ctx.put("novelClassList", novelClassList);
        ctx.put("novel",  novel);
        return "front_show_view";
    }

    /*�����޸�Novel��Ϣ*/
    public String ModifyNovel() {
        ActionContext ctx = ActionContext.getContext();
        try {
            NovelClass novelClassObj = novelClassDAO.GetNovelClassByClassId(novel.getNovelClassObj().getClassId());
            novel.setNovelClassObj(novelClassObj);
            /*����С˵ͼƬ�ϴ�*/
            if(novelPhotoFile != null) {
            	String novelPhotoPath = photoUpload(novelPhotoFile,novelPhotoFileContentType);
            	novel.setNovelPhoto(novelPhotoPath);
            }
            /*����С˵�ļ��ϴ�*/
            if(novelFileFile != null) {
            	String novelFilePath = photoUpload(novelFileFile,novelFileFileContentType);
            	novel.setNovelFile(novelFilePath);
            }
            novelDAO.UpdateNovel(novel);
            ctx.put("message",  java.net.URLEncoder.encode("Novel��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Novel��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Novel��Ϣ*/
    public String DeleteNovel() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            novelDAO.DeleteNovel(novelId);
            ctx.put("message",  java.net.URLEncoder.encode("Novelɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Novelɾ��ʧ��!"));
            return "error";
        }
    }

}
