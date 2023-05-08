<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Novel" %>
<%@ page import="com.chengxusheji.domain.NovelClass" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的NovelClass信息
    List<NovelClass> novelClassList = (List<NovelClass>)request.getAttribute("novelClassList");
    Novel novel = (Novel)request.getAttribute("novel");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改小说</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var novelName = document.getElementById("novel.novelName").value;
    if(novelName=="") {
        alert('请输入小说名称!');
        return false;
    }
    var author = document.getElementById("novel.author").value;
    if(author=="") {
        alert('请输入作者!');
        return false;
    }
    var publish = document.getElementById("novel.publish").value;
    if(publish=="") {
        alert('请输入出版社!');
        return false;
    }
    var tjFlag = document.getElementById("novel.tjFlag").value;
    if(tjFlag=="") {
        alert('请输入是否推荐!');
        return false;
    }
    var novelDesc = document.getElementById("novel.novelDesc").value;
    if(novelDesc=="") {
        alert('请输入小说简介!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="Novel/Novel_ModifyNovel.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>小说id:</td>
    <td width=70%><input id="novel.novelId" name="novel.novelId" type="text" value="<%=novel.getNovelId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>小说类别:</td>
    <td width=70%>
      <select name="novel.novelClassObj.classId">
      <%
        for(NovelClass novelClass:novelClassList) {
          String selected = "";
          if(novelClass.getClassId() == novel.getNovelClassObj().getClassId())
            selected = "selected";
      %>
          <option value='<%=novelClass.getClassId() %>' <%=selected %>><%=novelClass.getClassName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>小说名称:</td>
    <td width=70%><input id="novel.novelName" name="novel.novelName" type="text" size="30" value='<%=novel.getNovelName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>小说图片:</td>
    <td width=70%><img src="<%=basePath %><%=novel.getNovelPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="novel.novelPhoto" value="<%=novel.getNovelPhoto() %>" />
    <input id="novelPhotoFile" name="novelPhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>作者:</td>
    <td width=70%><input id="novel.author" name="novel.author" type="text" size="20" value='<%=novel.getAuthor() %>'/></td>
  </tr>

  <tr>
    <td width=30%>出版社:</td>
    <td width=70%><input id="novel.publish" name="novel.publish" type="text" size="20" value='<%=novel.getPublish() %>'/></td>
  </tr>

  <tr>
    <td width=30%>出版日期:</td>
    <% DateFormat publishDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="novel.publishDate"  name="novel.publishDate" onclick="setDay(this);" value='<%=publishDateSDF.format(novel.getPublishDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>页数:</td>
    <td width=70%><input id="novel.novelPageNum" name="novel.novelPageNum" type="text" size="8" value='<%=novel.getNovelPageNum() %>'/></td>
  </tr>

  <tr>
    <td width=30%>字数:</td>
    <td width=70%><input id="novel.wordsNum" name="novel.wordsNum" type="text" size="8" value='<%=novel.getWordsNum() %>'/></td>
  </tr>

  <tr>
    <td width=30%>小说文件:</td>
    <td width=70%><img src="<%=basePath %><%=novel.getNovelFile() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="novel.novelFile" value="<%=novel.getNovelFile() %>" />
    <input id="novelFileFile" name="novelFileFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>是否推荐:</td>
    <td width=70%><input id="novel.tjFlag" name="novel.tjFlag" type="text" size="20" value='<%=novel.getTjFlag() %>'/></td>
  </tr>

  <tr>
    <td width=30%>阅读量:</td>
    <td width=70%><input id="novel.readCount" name="novel.readCount" type="text" size="8" value='<%=novel.getReadCount() %>'/></td>
  </tr>

  <tr>
    <td width=30%>小说简介:</td>
    <td width=70%><textarea id="novel.novelDesc" name="novel.novelDesc" rows=5 cols=50><%=novel.getNovelDesc() %></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
