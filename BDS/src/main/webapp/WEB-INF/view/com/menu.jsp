<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  - PROGRAM ID  : menu.jsp
  - AUTHOR      : openit
  - CREATED     : 2017. 11. 1.
  - DESCRIPTION : menu
--%>
 <div class="left_wrap">
     <ul class="left_menu">
         <li><a href="/stat/dashBoard" class="depth_01 dashBoard">대시보드</a></li>
         <li><a href="/user" class="depth_01 user">회원정보관리</a></li>
         <li><a href="/locker" class="depth_01 locker">락커정보관리</a></li>
         <li><a href="/device" class="depth_01 device">디바이스정보관리</a></li>
         <li><a href="/ticket" class="depth_01 ticket">티켓정보관리</a></li>
         <li><a href="/stat/userStat" class="depth_01 userStat">사용자 통계</a></li>
         <li><a href="/stat/lockerStat" class="depth_01 lockerStat">락커 사용 통계</a></li>
         <li><a href="/login" class="depth_01">로그아웃</a></li>
     </ul>
 </div>
 
 <script>
 $(function() {
    $('.left_menu .' + $('body').attr('class')).addClass('on');
 });
 </script>