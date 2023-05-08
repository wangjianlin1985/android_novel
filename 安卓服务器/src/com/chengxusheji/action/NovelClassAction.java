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
import com.chengxusheji.dao.NovelClassDAO;
import com.chengxusheji.domain.NovelClass;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class NovelClassAction extends BaseAction {

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

    private int classId;
    public void setClassId(int classId) {
        this.classId = classId;
    }
    public int getClassId() {
        return classId;
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

    /*��������NovelClass����*/
    private NovelClass novelClass;
    public void setNovelClass(NovelClass novelClass) {
        this.novelClass = novelClass;
    }
    public NovelClass getNovelClass() {
        return this.novelClass;
    }

    /*��ת�����NovelClass��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���NovelClass��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddNovelClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            novelClassDAO.AddNovelClass(novelClass);
            ctx.put("message",  java.net.URLEncoder.encode("NovelClass��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NovelClass���ʧ��!"));
            return "error";
        }
    }

    /*��ѯNovelClass��Ϣ*/
    public String QueryNovelClass() {
        if(currentPage == 0) currentPage = 1;
        List<NovelClass> novelClassList = novelClassDAO.QueryNovelClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        novelClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = novelClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = novelClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("novelClassList",  novelClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryNovelClassOutputToExcel() { 
        List<NovelClass> novelClassList = novelClassDAO.QueryNovelClassInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "NovelClass��Ϣ��¼"; 
        String[] headers = { "����id","��������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<novelClassList.size();i++) {
        	NovelClass novelClass = novelClassList.get(i); 
        	dataset.add(new String[]{novelClass.getClassId() + "",novelClass.getClassName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"NovelClass.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯNovelClass��Ϣ*/
    public String FrontQueryNovelClass() {
        if(currentPage == 0) currentPage = 1;
        List<NovelClass> novelClassList = novelClassDAO.QueryNovelClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        novelClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = novelClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = novelClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("novelClassList",  novelClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�NovelClass��Ϣ*/
    public String ModifyNovelClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������classId��ȡNovelClass����*/
        NovelClass novelClass = novelClassDAO.GetNovelClassByClassId(classId);

        ctx.put("novelClass",  novelClass);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�NovelClass��Ϣ*/
    public String FrontShowNovelClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������classId��ȡNovelClass����*/
        NovelClass novelClass = novelClassDAO.GetNovelClassByClassId(classId);

        ctx.put("novelClass",  novelClass);
        return "front_show_view";
    }

    /*�����޸�NovelClass��Ϣ*/
    public String ModifyNovelClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            novelClassDAO.UpdateNovelClass(novelClass);
            ctx.put("message",  java.net.URLEncoder.encode("NovelClass��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NovelClass��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��NovelClass��Ϣ*/
    public String DeleteNovelClass() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            novelClassDAO.DeleteNovelClass(classId);
            ctx.put("message",  java.net.URLEncoder.encode("NovelClassɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NovelClassɾ��ʧ��!"));
            return "error";
        }
    }

}
