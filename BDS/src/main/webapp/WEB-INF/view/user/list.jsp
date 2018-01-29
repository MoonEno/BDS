<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  - PROGRAM ID  : list.jsp
  - AUTHOR      : openit
  - CREATED     : 2017. 11. 1.
  - DESCRIPTION : 회원정보관리 > 회원 목록 화면
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
                    <h1 class="title"><span>회원목록조회</span></h1>
                    <div class="area">회원정보관리 &gt; <span>회원목록조회</span></div>
                </div>
                <!-- 본문 페이지 타이틀 & 로케이션 맵 -->

                <!-- 검색영역 -->
                <div class="search_wrap mt_20">
                    <h2 class="search_tit"><i class="fa  fa-search"></i> Search</h2>

                    <form method="post">
	                    <input type="hidden" name="pageNo" value="${param.pageNo != null ? param.pageNo : 1}" />
	                    <input type="hidden" name="basCd" value="${param.basCd}" />
	                    <input type="hidden" name="state" value="${param.state}" />
	                    <input type="hidden" name="keyword" value="${param.keyword}" />
	                    <input type="hidden" name="userId" value="${param.userId}" />
                    </form>

                    <table class="list_search">
                        <colgroup>
                            <col width="20%">
                            <col width="20%">
                            <col width="*">
                            <col width="10%">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>종목</th>
                                <th>회원상태</th>
                                <th>검색어</th>
                                <th></th>
                            </tr>
                            <tr>
                                <td>
                                    <select id="basCd" class="w50">
                                        <option value="">ALL</option>
                                        <c:forEach var="bas" items="${basList}">
                                        <option value="${bas.BAS_CD}">${bas.BAS_NM}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <select id="state" class="w50">
                                        <option value="">ALL</option>
                                        <option value="using">이용중</option>
                                        <option value="wating">미이용</option>
                                    </select>
                                </td>
                                <td>
                                    <input id="keyword" type="text" class="w100" placeholder="회원아이디, 회원명, 사번" />
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
                    <div class="btn_wrap">
                        <button id="modifyUserList" type="button" class="no_ico">회원정보 갱신하기</button>
                        <button id="createUser" type="button" class="no_ico">등록하기</button>
                    </div>
                </div>
                <!--// 검색결과 헤더 -->

                <!-- 검색결과 리스트 -->
                <table id="userList" class="list01">
                    <colgroup>
                        <col width="5%">
                        <col width="15%">
                        <col width="10%">
                        <col width="10%">
                        <col width="10%">
                        <col width="10%">
                        <col width="*">
                        <col width="15%">
                        <col width="10%">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>회원아이디</th>
                            <th>회원명</th>
                            <th>사번</th>
                            <th>종목명</th>
                            <th>출입횟수</th>
                            <th>회원상태</th>
                            <th>최근입장일시<br>최근퇴장일시</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody></tbody>
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
    <script src='../js/user/list.js'></script>
</body>
</html>
