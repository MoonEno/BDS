<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  - PROGRAM ID  : hist.jsp
  - AUTHOR      : openit
  - CREATED     : 2017. 11. 1.
  - DESCRIPTION : 회원정보관리 > 회원 이용 이력 목록 화면
--%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/view/com/header.jsp" %>
<body class="user">
    <div class="wrap">
        <div class="container">
            <!-- LNB -->
            <%@ include file="/WEB-INF/view/com/menu.jsp" %>
            <!--// LNB -->

            <!-- body centents -->
            <div class="right_body">
                <!-- 본문 페이지 타이틀 & 로케이션 맵 -->
                <div class="con_tit">
                    <h1 class="title"><span>회원이용이력목록조회</span></h1>
                    <div class="area">회원정보관리 &gt; <span>회원이용이력목록조회</span></div>
                </div>
                <!-- 본문 페이지 타이틀 & 로케이션 맵 -->

                <!-- 검색영역 -->
                <div class="search_wrap mt_20">
                    <h2 class="search_tit"><i class="fa  fa-search"></i> Search</h2>

                    <form method="post" action="/user">
                        <input type="hidden" class="list" name="pageNo" value="${param.pageNo != null ? param.pageNo : 1}" />
                        <input type="hidden" class="list" name="basCd" value="${param.basCd}" />
                        <input type="hidden" class="list" name="state" value="${param.state}" />
                        <input type="hidden" class="list" name="keyword" value="${param.keyword}" />
                    </form>
                    <input type="hidden" class="hist" name="userId" value="${param.userId}" />
                    <input type="hidden" class="hist" name="pageNo" value="1" />
                    <input type="hidden" class="hist" name="startDt" />
                    <input type="hidden" class="hist" name="endDt" />
                    <input type="hidden" class="hist" name="actCd" />
                    <input type="hidden" class="hist" name="keyword" />

                    <table class="list_search">
                        <colgroup>
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                            <col width="*">
                            <col width="10%">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>검색시작일</th>
                                <th>검색종료일</th>
                                <th>행동</th>
                                <th>검색어</th>
                                <th></th>
                            </tr>
                            <tr>
                                <td>
                                    <input id="startDt" type="text" class="datepicker_input" readonly="readonly">
                                </td>
                                <td>
                                    <input id="endDt" type="text" class="datepicker_input" readonly="readonly">
                                </td>
                                <td>
                                    <select id="actCd" class="w60">
                                        <option value="">ALL</option>
                                        <c:forEach var="act" items="${actList}">
                                        <option value="${act.CODE}">${act.CODE_NM}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input id="keyword" type="text" class="w100" placeholder="디바이스아이디" />
                                </td>
                                <td>
                                    <a id="searchBtn" class="td_btn w80 h15">검색</a>
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
                <table id="userHistList" class="list01">
                    <colgroup>
                        <col width="5%">
                        <col width="10%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <col width="*">
                        <col width="*">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>행동</th>
                            <th>행동일시</th>
                            <th>회원명</th>
                            <th>사번</th>
                            <th>디바이스정보</th>
                            <th>락커정보</th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
                <!--// 검색결과 리스트 -->

                <!-- 페이징 -->
                <ul class="shp2_paging"></ul>
                <!--// 페이징 -->
                
                <div class="result_header">
                    <div class="btn_wrap">
                        <button id="searchUserList" type="button" class="no_ico">목록</button>
                    </div>
                </div>
            </div>
            <!--// body centents -->
        </div>

        <!-- footer -->
        <%@ include file="/WEB-INF/view/com/footer.jsp" %>
        <!--// footer -->
    </div>
    <script src='../js/user/hist.js'></script>
</body>
</html>