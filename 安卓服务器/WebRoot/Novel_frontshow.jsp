<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Novel" %>
<%@ page import="com.chengxusheji.domain.NovelClass" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的NovelClass信息
    List<NovelClass> novelClassList = (List<NovelClass>)request.getAttribute("novelClassList");
    Novel novel = (Novel)request.getAttribute("novel");

%>
<HTML><HEAD><TITLE>查看小说</TITLE>
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
    <td width=30%>小说id:</td>
    <td width=70%><%=novel.getNovelId() %></td>
  </tr>

  <tr>
    <td width=30%>小说类别:</td>
    <td width=70%>
      <%=novel.getNovelClassObj().getClassName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>小说名称:</td>
    <td width=70%><%=novel.getNovelName() %></td>
  </tr>

  <tr>
    <td width=30%>小说图片:</td>
    <td width=70%><img src="<%=basePath %><%=novel.getNovelPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>作者:</td>
    <td width=70%><%=novel.getAuthor() %></td>
  </tr>

  <tr>
    <td width=30%>出版社:</td>
    <td width=70%><%=novel.getPublish() %></td>
  </tr>

  <tr>
    <td width=30%>出版日期:</td>
        <% java.text.DateFormat publishDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=publishDateSDF.format(novel.getPublishDate()) %></td>
  </tr>

  <tr>
    <td width=30%>页数:</td>
    <td width=70%><%=novel.getNovelPageNum() %></td>
  </tr>

  <tr>
    <td width=30%>字数:</td>
    <td width=70%><%=novel.getWordsNum() %></td>
  </tr>

  <tr>
    <td width=30%>小说文件:</td>
    <td width=70%><img src="<%=basePath %><%=novel.getNovelFile() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>是否推荐:</td>
    <td width=70%><%=novel.getTjFlag() %></td>
  </tr>

  <tr>
    <td width=30%>阅读量:</td>
    <td width=70%><%=novel.getReadCount() %></td>
  </tr>

  <tr>
    <td width=30%>小说简介:</td>
    <td width=70%><%=novel.getNovelDesc() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
