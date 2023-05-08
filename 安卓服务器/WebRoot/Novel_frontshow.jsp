<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Novel" %>
<%@ page import="com.chengxusheji.domain.NovelClass" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�NovelClass��Ϣ
    List<NovelClass> novelClassList = (List<NovelClass>)request.getAttribute("novelClassList");
    Novel novel = (Novel)request.getAttribute("novel");

%>
<HTML><HEAD><TITLE>�鿴С˵</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>С˵id:</td>
    <td width=70%><%=novel.getNovelId() %></td>
  </tr>

  <tr>
    <td width=30%>С˵���:</td>
    <td width=70%>
      <%=novel.getNovelClassObj().getClassName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>С˵����:</td>
    <td width=70%><%=novel.getNovelName() %></td>
  </tr>

  <tr>
    <td width=30%>С˵ͼƬ:</td>
    <td width=70%><img src="<%=basePath %><%=novel.getNovelPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>����:</td>
    <td width=70%><%=novel.getAuthor() %></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><%=novel.getPublish() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
        <% java.text.DateFormat publishDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=publishDateSDF.format(novel.getPublishDate()) %></td>
  </tr>

  <tr>
    <td width=30%>ҳ��:</td>
    <td width=70%><%=novel.getNovelPageNum() %></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><%=novel.getWordsNum() %></td>
  </tr>

  <tr>
    <td width=30%>С˵�ļ�:</td>
    <td width=70%><img src="<%=basePath %><%=novel.getNovelFile() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>�Ƿ��Ƽ�:</td>
    <td width=70%><%=novel.getTjFlag() %></td>
  </tr>

  <tr>
    <td width=30%>�Ķ���:</td>
    <td width=70%><%=novel.getReadCount() %></td>
  </tr>

  <tr>
    <td width=30%>С˵���:</td>
    <td width=70%><%=novel.getNovelDesc() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
