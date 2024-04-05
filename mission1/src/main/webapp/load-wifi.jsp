<%@ page import="com.example.mission1.service.WifiService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    WifiService wifiService = new WifiService();
    int wifiInfoList = wifiService.getWifiInfoList();
%>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" href="css/loadWifi.css">
</head>
<body>
<%
    if (wifiInfoList != -1) { // table이 비어있음
%>
<h1><%=wifiInfoList%>개의 와이파이 정보가 저장되었습니다.</h1>
<%
} else { // 이미 저장되어있음
%>
<h1>이미 와이파이 정보를 불러왔습니다.</h1>
<%
    }
%>
<div class="aTag">
    <a href="/">홈으로 가기</a>
</div>
</body>
</html>
