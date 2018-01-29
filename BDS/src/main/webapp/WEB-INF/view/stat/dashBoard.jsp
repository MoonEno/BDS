<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  - PROGRAM ID  : dashBoard.jsp
  - AUTHOR      : openit
  - CREATED     : 2017. 11. 15.
  - DESCRIPTION : 통계 > 대시보드
--%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/view/com/header.jsp" %>
<head>
    <!-- chart plugin -->
    <script src='../js/stat/morris/morris-chart-settings.min.js'></script>
    <script src='../js/stat/morris/morris.min.js'></script>
    <script src='../js/stat/morris/raphael.min.js'></script>

    <link rel="stylesheet" type="text/css" href="../css/dashboard.css" >
    <link rel="stylesheet" type="text/css" href="../css/morris.css" >
</head>

<body class="dashBoard">

    <!-- S: 사용자 통계 조회 -->
    <div class="wrap">
        <div class="container">
            <!-- LNB -->
            <%@ include file="/WEB-INF/view/com/menu.jsp" %>
            <!--// LNB -->
            <!-- body centents -->
            <div class="right_body">
                <!-- 본문 페이지 타이틀 & 로케이션 맵 -->
                <div class="con_tit">
                    <h1 class="title"><span>대시보드</span></h1>
                    <div class="area">통계 &gt; <span>대시보드</span></div>
                </div>
                <!-- 본문 페이지 타이틀 & 로케이션 맵 -->
                <!-- S: 검색영역 -->
                <div class="mt_20 txt_r">
                    <input id="startDt" type="text" class="datepicker_input" readonly="readonly">
                    <a id="searchBtn" class="datePicker_search_btn w5">검색</a>
                </div>
                <!-- E: 검색영역 -->
                
                <!-- 이용현황 CONTENTS-->
                <div class="chartContents">
                     <div class="w45 chart">
                     <header role="heading">
                         <h2>시간별 현황</h2>
                     </header>
                     <div id ="timeChart"></div>
                     </div>
                     
                     <div class="w45 chart">
                     <header role="heading">
                         <h2>일별 현황</h2>
                     </header>
                     <div id ="dayChart"></div>
                     </div>
                </div>
                    <!-- E: 이용현황 차트 -->
                    <!-- S: 이용현황 차트 설명 -->
                <div class="chartDesc mt_30">
                    <table class="userCount txt_c">
                         <thead>
                             <tr>
                              <th colspan="2">구분</th>
                              <th>회원수</th>
                             <tr>
                         </thead>
                         <tbody>
                          <tr class="totalUserCnt">
                            <td colspan="2">현재 이용 중 회원수</td>
                            <td class="useUserCnt"></td>
                          </tr>
                          <tr class="totalUserCnt">
                            <td colspan="2" >체크아웃 총 회원수</td>
                            <td class="chkOutCnt"></td>
                          </tr>
                         </tbody>
                    </table>
                </div>
                    <!-- E: 이용현황 차트 설명 -->
                
            </div>
            <!--// body centents -->
        </div>

        <!-- footer -->
        <%@ include file="/WEB-INF/view/com/footer.jsp" %>
        <!--// footer -->
    </div>
    <script src='../js/stat/dashBoard.js'></script>
</body>
</html>