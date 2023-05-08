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
import com.chengxusheji.dao.CommentDAO;
import com.chengxusheji.domain.Comment;
import com.chengxusheji.dao.NovelDAO;
import com.chengxusheji.domain.Novel;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CommentAction extends BaseAction {

    /*�������Ҫ��ѯ������: ����С˵*/
    private Novel novelObj;
    public void setNovelObj(Novel novelObj) {
        this.novelObj = novelObj;
    }
    public Novel getNovelObj() {
        return this.novelObj;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String content;
    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return this.content;
    }

    /*�������Ҫ��ѯ������: ������*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String commentTime;
    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
    public String getCommentTime() {
        return this.commentTime;
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

    private int commentId;
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
    public int getCommentId() {
        return commentId;
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
    @Resource NovelDAO novelDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource CommentDAO commentDAO;

    /*��������Comment����*/
    private Comment comment;
    public void setComment(Comment comment) {
        this.comment = comment;
    }
    public Comment getComment() {
        return this.comment;
    }

    /*��ת�����Comment��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Novel��Ϣ*/
        List<Novel> novelList = novelDAO.QueryAllNovelInfo();
        ctx.put("novelList", novelList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���Comment��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddComment() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Novel novelObj = novelDAO.GetNovelByNovelId(comment.getNovelObj().getNovelId());
            comment.setNovelObj(novelObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(comment.getUserObj().getUser_name());
            comment.setUserObj(userObj);
            commentDAO.AddComment(comment);
            ctx.put("message",  java.net.URLEncoder.encode("Comment��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Comment���ʧ��!"));
            return "error";
        }
    }

    /*��ѯComment��Ϣ*/
    public String QueryComment() {
        if(currentPage == 0) currentPage = 1;
        if(content == null) content = "";
        if(commentTime == null) commentTime = "";
        List<Comment> commentList = commentDAO.QueryCommentInfo(novelObj, content, userObj, commentTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        commentDAO.CalculateTotalPageAndRecordNumber(novelObj, content, userObj, commentTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = commentDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = commentDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("commentList",  commentList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("novelObj", novelObj);
        List<Novel> novelList = novelDAO.QueryAllNovelInfo();
        ctx.put("novelList", novelList);
        ctx.put("content", content);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("commentTime", commentTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryCommentOutputToExcel() { 
        if(content == null) content = "";
        if(commentTime == null) commentTime = "";
        List<Comment> commentList = commentDAO.QueryCommentInfo(novelObj,content,userObj,commentTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Comment��Ϣ��¼"; 
        String[] headers = { "����id","����С˵","��������","������","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<commentList.size();i++) {
        	Comment comment = commentList.get(i); 
        	dataset.add(new String[]{comment.getCommentId() + "",comment.getNovelObj().getNovelName(),
comment.getContent(),comment.getUserObj().getName(),
comment.getCommentTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Comment.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯComment��Ϣ*/
    public String FrontQueryComment() {
        if(currentPage == 0) currentPage = 1;
        if(content == null) content = "";
        if(commentTime == null) commentTime = "";
        List<Comment> commentList = commentDAO.QueryCommentInfo(novelObj, content, userObj, commentTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        commentDAO.CalculateTotalPageAndRecordNumber(novelObj, content, userObj, commentTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = commentDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = commentDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("commentList",  commentList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("novelObj", novelObj);
        List<Novel> novelList = novelDAO.QueryAllNovelInfo();
        ctx.put("novelList", novelList);
        ctx.put("content", content);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("commentTime", commentTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Comment��Ϣ*/
    public String ModifyCommentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������commentId��ȡComment����*/
        Comment comment = commentDAO.GetCommentByCommentId(commentId);

        List<Novel> novelList = novelDAO.QueryAllNovelInfo();
        ctx.put("novelList", novelList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("comment",  comment);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Comment��Ϣ*/
    public String FrontShowCommentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������commentId��ȡComment����*/
        Comment comment = commentDAO.GetCommentByCommentId(commentId);

        List<Novel> novelList = novelDAO.QueryAllNovelInfo();
        ctx.put("novelList", novelList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("comment",  comment);
        return "front_show_view";
    }

    /*�����޸�Comment��Ϣ*/
    public String ModifyComment() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Novel novelObj = novelDAO.GetNovelByNovelId(comment.getNovelObj().getNovelId());
            comment.setNovelObj(novelObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(comment.getUserObj().getUser_name());
            comment.setUserObj(userObj);
            commentDAO.UpdateComment(comment);
            ctx.put("message",  java.net.URLEncoder.encode("Comment��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Comment��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Comment��Ϣ*/
    public String DeleteComment() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            commentDAO.DeleteComment(commentId);
            ctx.put("message",  java.net.URLEncoder.encode("Commentɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Commentɾ��ʧ��!"));
            return "error";
        }
    }

}
