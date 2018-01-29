<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  - PROGRAM ID  : info.jsp
  - AUTHOR      : openit
  - CREATED     : 2017. 11. 1.
  - DESCRIPTION : 회원정보관리 > 회원 정보 화면
--%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/view/com/header.jsp" %>
<body class="user">

    <!-- 락커 변경하기 -->
    <!-- 딤처리 -->
    <div class="overlay" id="locker"></div>
    <!--// 딤처리 -->

    <div class="popup" style="width:200px;">
        <!-- 팝업 타이틀 -->
        <h1 class="pop_tit">변경하기</h1>
        <span class="close popup_close" onclick="location.href='#detail'"><img src="../images/btn_close.png" alt="닫기"/></span>
        <!--// 팝업 타이틀 -->

        <!-- 팝업 콘텐츠 -->
        <div class="pop_container">

            <table id="lockerList" class="list01">
                <thead>
                    <tr>
                        <th>락커번호</th>
                    </tr>
                </thead>
                <tbody></tbody>
            </table>

        </div>
        <!--// 팝업 콘텐츠 -->

    </div>
    <!--// 락커 변경하기 -->

    <!-- 디바이스 추가하기 -->
    <!-- 딤처리 -->
    <div class="overlay" id="device"></div>
    <!--// 딤처리 -->

    <div class="popup" style="width:300px;">
        <!-- 팝업 타이틀 -->
        <h1 class="pop_tit">추가하기</h1>
        <span class="close popup_close" onclick="location.href='#detail'"><img src="../images/btn_close.png" alt="닫기"/></span>
        <!--// 팝업 타이틀 -->

        <!-- 팝업 콘텐츠 -->
        <div class="pop_container">

            <table id="deviceList" class="list01">
                <colgroup>
                    <col width="*">
                    <col width="40%">
                </colgroup>
                <thead>
                    <tr>
                        <th>디바이스아이디</th>
                        <th>디바이스종류</th>
                    </tr>
                </thead>
                <tbody></tbody>
            </table>

        </div>
        <!--// 팝업 콘텐츠 -->

    </div>
    <!--// 디바이스 추가하기 -->

    <div class="wrap">
        <div class="container">
            <!-- LNB -->
            <%@ include file="/WEB-INF/view/com/menu.jsp" %>
            <!--// LNB -->

            <!-- body centents -->
            <div class="right_body">
                <!-- 본문 페이지 타이틀 & 로케이션 맵 -->
                <div class="con_tit">
                    <h1 class="title"><span>회원조회</span></h1>
                    <div class="area">회원정보관리 &gt; <span>회원조회</span></div>
                </div>
                <!-- 본문 페이지 타이틀 & 로케이션 맵 -->

                <form method="post">
                    <input type="hidden" name="pageNo" value="${param.pageNo != null ? param.pageNo : 1}" />
                    <input type="hidden" name="basCd" value="${param.basCd}" />
                    <input type="hidden" name="state" value="${param.state}" />
                    <input type="hidden" name="keyword" value="${param.keyword}" />
                    <input type="hidden" name="userId" value="${param.userId}" />
                </form>

                <table id="user" class="list_search">
                    <colgroup>
                        <col width="15%">
                        <col width="34%">
                        <col width="15%">
                        <col width="35%">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th class="h30">회원아이디</th>
                            <td class="userId"></td>
                            <th>회원명</th>
                            <td><input type="text" class="w100 userName" /></td>
                        </tr>
                        <tr>
                            <th>사번</th>
                            <td><input type="text" class="w100 empNo" /></td>
                            <th>성별</th>
                            <td>
                                <select class="sex">
                                    <option value="M">남</option>
                                    <option value="F">여</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th class="h30">종목</th>
                            <td>
                                <select class="w50 basCd">
                                    <c:forEach var="bas" items="${basList}">
                                    <option value="${bas.BAS_CD}">${bas.BAS_NM}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <th>휴대폰번호</th>
                            <td><input type="text" class="w100 cellNo" /></td>
                        </tr>
                        <tr>
                            <th class="h30">출입횟수</th>
                            <td class="inCnt"></td>
                            <th>회원상태</th>
                            <td class="state"></td>
                        </tr>
                        <tr style="display:none;">
                            <th class="h30">락커번호</th>
                            <td>
                                <input type="text" class="readonly lockerNo1" readonly />
                                <a class="td_btn w10 h15 ml_20 lockerType lockerType1">변경</a>
                            </td>
                            <th>신발장번호</th>
                            <td>
                                <input type="text" class="readonly lockerNo2" readonly />
                                <a class="td_btn w10 h15 ml_20 lockerType lockerType2">변경</a>
                            </td>
                        </tr>
                        <tr>
                            <th class="h130">비고</th>
                            <td colspan="3"><textarea class="w100 h120 memo"></textarea></td>
                        </tr>
                    </tbody>
                </table>

                <table id="userDeviceList" class="list01">
                    <colgroup>
                        <col width="20%">
                        <col width="20%">
                        <col width="*">
                        <col width="10%">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>디바이스아이디</th>
                            <th>디바이스종류</th>
                            <th>최근사용일시</th>
                            <th><a class="td_btn add">추가</a></th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>

                <div class="result_header">
                    <div class="btn_wrap">
                        <button id="out" type="button" class="no_ico" style="display:none;"></button>
                        <button id="removeUser" type="button" class="no_ico" style="display:none;">삭제</button>
                        <button id="reset" type="button" class="no_ico">초기화</button>
                        <button id="modifyUser" type="button" class="no_ico">저장</button>
                        <button id="searchUserHistList" type="button" class="no_ico">이용이력조회</button>
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
    <script src='../js/user/info.js'></script>
</body>
</html>