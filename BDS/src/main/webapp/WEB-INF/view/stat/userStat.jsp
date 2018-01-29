<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  - PROGRAM ID  : userStat.jsp
  - AUTHOR      : openit
  - CREATED     : 2017. 11. 15.
  - DESCRIPTION : 통계 > 사용자 통계
--%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/view/com/header.jsp" %>
<body class="userStat">

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
                    <h1 class="title"><span>사용자 통계</span></h1>
                    <div class="area">통계 &gt; <span>사용자 통계</span></div>
                </div>
                <!-- 본문 페이지 타이틀 & 로케이션 맵 -->

                <!-- 검색영역 -->
                <div class="search_wrap mt_20">
                    <h2 class="search_tit"><i class="fa  fa-search"></i> Search</h2>

                    <input type="hidden" name="pageNo" value="${param.pageNo != null ? param.pageNo : 1}" />
                    <input type="hidden" name="dateTypeChoice" value="${param.dateTypeChoice}" />
                    <input type="hidden" name="sex" value="${param.sex}" />
                    <input type="hidden" name="age" value="${param.age}" />

                    <table class="list_search">
                        <colgroup>
                            <col width="30%">
                            <col width="20%">
                            <col width="12%">
                            <col width="12%">
                            <col width="20%">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th></th>
                                <th></th>
                                <th>성별</th>
                                <th>연령</th>
                                <th></th>
                            </tr>
                            <tr>
                                <td>
	                                <input type="radio" id="statChoice1" name="dateTypeChoice" value="time">
	                                <label for="statChoice1">시간별 통계</label>
	                                <input type="radio" id="statChoice2" name="dateTypeChoice" value="day">
	                                <label for="statChoice2">일별 통계</label>
	                                <input type="radio" id="statChoice3" name="dateTypeChoice" value="month">
	                                <label for="statChoice3">월별 통계</label>
	                            </td>
                                
                                <td>
                                    <input id="startDt" type="text" class="datepicker_input" readonly="readonly">
                                </td>
                                <td>
                                    <select id="sex" class="w60">
                                        <option value="">ALL</option>
                                        <option value="M">남자</option>
                                        <option value="F">여자</option>
                                    </select>
                                </td>
                                <td>
                                    <select id="age" class="w80">
                                        <option value="">ALL</option>
                                        <c:forEach var="i" begin="10" end="50" step="10">
                                           <option value="${i}">${i}대</option>
                                        </c:forEach>
                                        <option value="60">60대 이상</option>
                                    </select>
                                </td>

                                <td>
                                    <a id="searchBtn" class="td_btn w40 h15">검색</a>
                                    <a id="clearBtn" class="td_btn w40 h15">초기화</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <!--// 검색영역 -->

                <!-- 검색결과 헤더 -->
                <div class="result_header">
                    <div class="result_count">
                        <img src="../images/ico_result_num.png" alt="검색결과"> 검색결과 : <span class="txt_b totalCount">0</span>
                    </div>
                </div>
                <!--// 검색결과 헤더 -->

                <!-- 검색결과 리스트 -->
                <table id="userStatList" class="list01">
                    <colgroup>
                        <col width="10%">
                        <col width="30%">
                        <col width="20%">
                        <col width="20%">
                        <col width="30%">
                        <col width="*%">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>통계기준 일시</th>
                            <th>성별</th>
                            <th>연령</th>
                            <th>이용횟수 (총:<font class="totalCnt"></font>회)</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <!--// 검색결과 리스트 -->

                <!-- 페이징 -->
                <ul class="shp2_paging"></ul>
                <!--// 페이징 -->
            </div>
            <!--// body centents -->
        </div>

        <!-- footer -->
        <%@ include file="/WEB-INF/view/com/footer.jsp" %>
        <!--// footer -->
    </div>
    <script src='../js/stat/userStat.js'></script>
</body>
</html>