<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.NovelClass" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�NovelClass��Ϣ
    List<NovelClass> novelClassList = (List<NovelClass>)request.getAttribute("novelClassList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>���С˵</TITLE> 
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
/*��֤��*/
function checkForm() {
    var novelName = document.getElementById("novel.novelName").value;
    if(novelName=="") {
        alert('������С˵����!');
        return false;
    }
    var author = document.getElementById("novel.author").value;
    if(author=="") {
        alert('����������!');
        return false;
    }
    var publish = document.getElementById("novel.publish").value;
    if(publish=="") {
        alert('�����������!');
        return false;
    }
    var tjFlag = document.getElementById("novel.tjFlag").value;
    if(tjFlag=="") {
        alert('�������Ƿ��Ƽ�!');
        return false;
    }
    var novelDesc = document.getElementById("novel.novelDesc").value;
    if(novelDesc=="") {
        alert('������С˵���!');
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
    <TD align="left" vAlign=top >
    <s:form action="Novel/Novel_AddNovel.action" method="post" id="novelAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>С˵���:</td>
    <td width=70%>
      <select name="novel.novelClassObj.classId">
      <%
        for(NovelClass novelClass:novelClassList) {
      %>
          <option value='<%=novelClass.getClassId() %>'><%=novelClass.getClassName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>С˵����:</td>
    <td width=70%><input id="novel.novelName" name="novel.novelName" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>С˵ͼƬ:</td>
    <td width=70%><input id="novelPhotoFile" name="novelPhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="novel.author" name="novel.author" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><input id="novel.publish" name="novel.publish" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input type="text" readonly id="novel.publishDate"  name="novel.publishDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>ҳ��:</td>
    <td width=70%><input id="novel.novelPageNum" name="novel.novelPageNum" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="novel.wordsNum" name="novel.wordsNum" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>С˵�ļ�:</td>
    <td width=70%><input id="novelFileFile" name="novelFileFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>�Ƿ��Ƽ�:</td>
    <td width=70%><input id="novel.tjFlag" name="novel.tjFlag" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�Ķ���:</td>
    <td width=70%><input id="novel.readCount" name="novel.readCount" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>С˵���:</td>
    <td width=70%><textarea id="novel.novelDesc" name="novel.novelDesc" rows="5" cols="50"></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
