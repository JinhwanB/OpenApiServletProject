<%@ page import="com.example.mission1.service.WifiService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.mission1.dto.WifiInfoDetailDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");
    // request.getParameter를 통해 url의 parameter값을 가져올 수 있다. ex) ?lat=3.0245 -> request.getParameter("lat")를 통해 3.0245를 가져옴
    double lat = request.getParameter("lat") == null ? 0.0 : Double.parseDouble(request.getParameter("lat"));
    double lnt = request.getParameter("lnt") == null ? 0.0 : Double.parseDouble(request.getParameter("lnt"));

    WifiService wifiService = new WifiService();
    long count = wifiService.getWifiTableCnt(); // 현재 와이파이가 저장된 갯수
    List<WifiInfoDetailDto> list = new ArrayList<>();
    if (request.getParameter("lat") != null && request.getParameter("lnt") != null) { // 근처 와이파이 정보 보기 버튼을 눌렀을 때
        if (count == 0) { // openApi를 통해 와이파이를 가져오지 않았을 때
%>
<script type="text/javascript">
    alert("openApi를 통해 와이파이 정보를 먼저 가져와야합니다.");
    location.href = '/';
</script>
<%
        } else { // 와이파이 정보가 있다면
            List<WifiInfoDetailDto> nearWifi = wifiService.getNearWifi(lat, lnt); // 모두 가져와서
            list = nearWifi; // list에 저장
        }
    }
%>
<h1>와이파이 정보 구하기</h1>
<jsp:include page="menu.jsp"></jsp:include>
LAT: <input name="lat" class="lat-input" type="number" value=<%=lat%> step="any">
, LNT: <input name="lnt" class="lnt-input" type="number" value=<%=lnt%> step="any">
<button class="get-location" type="button">내 위치 가져오기</button>
<button class="get-wifi" type="button">근처 wifi 정보 보기</button>
<table>
    <thead>
    <tr>
        <th scope="col">거리(Km)</th>
        <th scope="col">관리번호</th>
        <th scope="col">자치구</th>
        <th scope="col">와이파이명</th>
        <th scope="col">도로명주소</th>
        <th scope="col">상세주소</th>
        <th scope="col">설치위치(층)</th>
        <th scope="col">설치유형</th>
        <th scope="col">설치기관</th>
        <th scope="col">서비스구분</th>
        <th scope="col">망종류</th>
        <th scope="col">설치년도</th>
        <th scope="col">실내외구분</th>
        <th scope="col">WIFI접속환경</th>
        <th scope="col">X좌표</th>
        <th scope="col">Y좌표</th>
        <th scope="col">작업일자</th>
    </tr>
    </thead>
    <tbody>
    <%
        if (!list.isEmpty()) { // list가 비어있지 않다면
            for (int i = 0; i < 20; i++) { // 근처 와이파이 정보 20개 가져오기
    %>
    <tr>
        <td><%=String.format("%.4f", list.get(i).getDistance())%>
        </td>
        <td><%=list.get(i).getX_SWIFI_MGR_NO()%>
        </td>
        <td><%=list.get(i).getX_SWIFI_WRDOFC()%>
        </td>
        <td><%=list.get(i).getX_SWIFI_MAIN_NM()%>
        </td>
        <td><%=list.get(i).getX_SWIFI_ADRES1()%>
        </td>
        <td><%=list.get(i).getX_SWIFI_ADRES2()%>
        </td>
        <td><%=list.get(i).getX_SWIFI_INSTL_FLOOR()%>
        </td>
        <td><%=list.get(i).getX_SWIFI_INSTL_TY()%>
        </td>
        <td><%=list.get(i).getX_SWIFI_INSTL_MBY()%>
        </td>
        <td><%=list.get(i).getX_SWIFI_SVC_SE()%>
        </td>
        <td><%=list.get(i).getX_SWIFI_CMCWR()%>
        </td>
        <td><%=list.get(i).getX_SWIFI_CNSTC_YEAR()%>
        </td>
        <td><%=list.get(i).getX_SWIFI_INOUT_DOOR()%>
        </td>
        <td><%=list.get(i).getX_SWIFI_REMARS3()%>
        </td>
        <td><%=list.get(i).getLAT()%>
        </td>
        <td><%=list.get(i).getLNT()%>
        </td>
        <td><%=list.get(i).getWORK_DTTM()%>
        </td>
    </tr>
    <%
        }
    } else { // 비어있을 때
    %>
    <tr>
        <td colspan="17" style="text-align: center;">위치 정보를 입력한 후에 조회해 주세요.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<script type="text/javascript" src="js/index.js"></script>
</body>
</html>