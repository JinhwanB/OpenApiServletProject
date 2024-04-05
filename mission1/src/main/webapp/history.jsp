<%@ page import="com.example.mission1.service.HistoryService" %>
<%@ page import="com.example.mission1.dto.HistoryDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HistoryService historyService = new HistoryService();
    List<HistoryDto> historyList = historyService.getHistoryList(); // 저장된 히스토리 가져오기

    String id = request.getParameter("id");
    if (id != null) { // 삭제 버튼을 눌렀을 때
        historyService.deleteHistory(Long.parseLong(id)); // 삭제 진행
    }
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
<h1>위치 히스토리 목록</h1>
<jsp:include page="menu.jsp"></jsp:include>
<table>
    <thead>
    <tr>
        <th scope="col">ID</th>
        <th scope="col">X좌표</th>
        <th scope="col">Y좌표</th>
        <th scope="col">조회일자</th>
        <th scope="col">비고</th>
    </tr>
    </thead>
    <tbody>
    <%
        if (!historyList.isEmpty()) {
            for (int i = historyList.size() - 1; i >= 0; i--) {
    %>
    <tr>
        <td><%=i + 1%>
        </td>
        <td><%=historyList.get(i).getX()%>
        </td>
        <td><%=historyList.get(i).getY()%>
        </td>
        <td><%=historyList.get(i).getDate()%>
        </td>
        <td>
            <div class="delete-button-div">
                <button type="button" onclick="deleteHistory(<%= historyList.get(i).getId() %>)">삭제
                </button>
            </div>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5" style="text-align: center;">근처 wifi 조회 후에 조회해 주세요.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<script type="text/javascript" src="js/history.js"></script>
</body>
</html>
