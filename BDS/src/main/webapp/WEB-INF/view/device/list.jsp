<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  - PROGRAM ID  : list.jsp
  - AUTHOR      : openit
  - CREATED     : 2017. 11. 1.
  - DESCRIPTION : 디바이스정보관리 > 디바이스 목록 화면
--%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/view/com/header.jsp" %>
<body class="device">

    <!-- 디바이스 등록하기 -->
    <!-- 딤처리 -->
    <div class="overlay" id="add"></div>
    <!--// 딤처리 -->

    <div class="popup add" style="width:400px;">
        <!-- 팝업 타이틀 -->
        <h1 class="pop_tit">디바이스 등록하기</h1>
        <span class="close popup_close" onclick="location.href='#list'"><img src="../images/btn_close.png" alt="닫기"/></span>
        <!--// 팝업 타이틀 -->

        <!-- 팝업 콘텐츠 -->
        <div class="pop_container">
 
            <!-- 점수내역 -->
            <table class="list_search">
                <colgroup>
                    <col width="40%">
                    <col width="*">
                </colgroup>
                <tbody>
                    <tr>
                        <th>UID</th>
                        <td><input type="text" class="w80 uid" /></td>
                    </tr>
                    <tr>
                        <th>디바이스종류</th>
                        <td>
                            <select class="w80 deviceTypeCd">
                                <c:forEach var="deviceType" items="${deviceTypeList}">
                                <option value="${deviceType.CODE}">${deviceType.CODE_NM}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>디바이스아이디</th>
                        <td><input type="text" class="w80 deviceId" /></td>
                    </tr>
                </tbody>
            </table>
            <!--// 점수내역 -->

            <!-- 버튼영역 -->
            <div class="content_btn_wrap btn_center">
                <button type="button" class="popup_close reset">초기화</button>
                <button type="button" class="popup_close ok">확인</button>
            </div>
            <!--// 버튼영역 -->

        </div>
        <!--// 팝업 콘텐츠 -->

    </div>
    <!--// 디바이스 등록하기 -->

    <!-- 디바이스 수정하기 -->
    <!-- 딤처리 -->
    <div class="overlay" id="upd"></div>
    <!--// 딤처리 -->

    <div class="popup upd" style="width:400px;">
        <!-- 팝업 타이틀 -->
        <h1 class="pop_tit">디바이스 수정하기</h1>
        <span class="close popup_close" onclick="location.href='#list'"><img src="../images/btn_close.png" alt="닫기"/></span>
        <!--// 팝업 타이틀 -->

        <!-- 팝업 콘텐츠 -->
        <div class="pop_container">

            <!-- 점수내역 -->
            <table class="list_search">
                <colgroup>
                    <col width="40%">
                    <col width="*">
                </colgroup>
                <tbody>
                    <tr>
                        <th>UID</th>
                        <td class="uid"></td>
                    </tr>
                    <tr>
                        <th>디바이스종류</th>
                        <td>
                            <select class="w80 deviceTypeCd">
                                <c:forEach var="deviceType" items="${deviceTypeList}">
                                <option value="${deviceType.CODE}">${deviceType.CODE_NM}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>디바이스아이디</th>
                        <td><input type="text" class="w80 deviceId" /></td>
                    </tr>
                </tbody>
            </table>
            <!--// 점수내역 -->

            <!-- 버튼영역 -->
            <div class="content_btn_wrap btn_center">
                <button type="button" class="popup_close del">삭제</button>
                <button type="button" class="popup_close reset">초기화</button>
                <button type="button" class="popup_close ok">확인</button>
            </div>
            <!--// 버튼영역 -->

        </div>
        <!--// 팝업 콘텐츠 -->

    </div>
    <!--// 디바이스 수정하기 -->

    <div class="wrap">
        <div class="container">
            <!-- LNB -->
            <%@ include file="/WEB-INF/view/com/menu.jsp" %>
            <!--// LNB -->

            <!-- body centents -->
            <div class="right_body">
                <!-- 본문 페이지 타이틀 & 로케이션 맵 -->
                <div class="con_tit">
                    <h1 class="title"><span>디바이스목록조회</span></h1>
                    <div class="area">디바이스정보관리 &gt; <span>디바이스목록조회</span></div>
                </div>
                <!-- 본문 페이지 타이틀 & 로케이션 맵 -->

                <!-- 검색영역 -->
                <div class="search_wrap mt_20">
                    <h2 class="search_tit"><i class="fa  fa-search"></i> Search</h2>

                    <input type="hidden" name="pageNo" value="${param.pageNo != null ? param.pageNo : 1}" />
                    <input type="hidden" name="deviceTypeCd" value="${param.deviceTypeCd}" />
                    <input type="hidden" name="state" value="${param.state}" />
                    <input type="hidden" name="keyword" value="${param.keyword}" />

                    <table class="list_search">
                        <colgroup>
                            <col width="20%">
                            <col width="20%">
                            <col width="*">
                            <col width="10%">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>디바이스종류</th>
                                <th>사용구분</th>
                                <th>검색어</th>
                                <th></th>
                            </tr>
                            <tr>
                                <td>
                                    <select id="deviceTypeCd" class="w50">
                                        <option value="">ALL</option>
                                        <c:forEach var="deviceType" items="${deviceTypeList}">
                                        <option value="${deviceType.CODE}">${deviceType.CODE_NM}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <select id="state" class="w50">
                                        <option value="">ALL</option>
                                        <option value="using">할당</option>
                                        <option value="wating">미할당</option>
                                    </select>
                                </td>
                                <td>
                                    <input id="keyword" type="text" class="w100" placeholder="디바이스아이디, 이용회원명" />
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
                        <button id="createBtn" type="button" class="no_ico" onclick="location.href='#add'">디바이스 등록하기</button>
                    </div>
                </div>
                <!--// 검색결과 헤더 -->

                <!-- 검색결과 리스트 -->
                <table id="deviceList" class="list01">
                    <colgroup>
                        <col width="5%">
                        <col width="20%">
                        <col width="10%">
                        <col width="10%">
                        <col width="10%">
                        <col width="*">
                        <col width="5%">
                        <col width="5%">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>디바이스아이디</th>
                            <th>디바이스종류</th>
                            <th>사용구분</th>
                            <th>이용회원명</th>
                            <th>최근사용일시</th>
                            <th></th>
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
    <script src='../js/device/list.js'></script>
</body>
</html>