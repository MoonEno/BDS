<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  - PROGRAM ID  : login.jsp
  - AUTHOR      : openit
  - CREATED     : 2017. 11. 1.
  - DESCRIPTION : 로그인 화면
--%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/view/com/header.jsp" %>
<body style="height: auto; min-height: auto;">

    <div class="wrap txt_c w60" style="margin:200px auto 0px;">
        <div class="container w50"style="margin:0px auto;">

            <img class="h50" style="margin-bottom:30px;" alt="로고" src="../images/top_logo_b.png" />
            <table id="user" class="list_search" >
                <colgroup>
                    <col width="30%">
                    <col width="*">
                </colgroup>
                <tbody>
                    <tr>
                        <th>아이디</th>
                        <td><input type="text" class="w100 adminId" value="admin" /></td>
                    </tr>
                    <tr>
                        <th>비밀번호</th>
                        <td><input type="password" class="w100 password" value="admin123" /></td>
                    </tr>
                </tbody>
            </table>
            <a class="td_btn" style="float:right; margin-top:10px; width:50px; height:20px; padding-top:10px;">로그인</a>

        </div>

        <!-- footer -->
        <%@ include file="/WEB-INF/view/com/footer.jsp" %>
        <!--// footer -->
    </div>
<script type="text/javascript">
$(function() {
    $('input').o_enter($('a'));
});

$(document).on('click', 'a', function() {
    $.ajax({
        url : '/login/login',
        type : 'post',
        dataType : 'json',
        data : {
            adminId : $('.adminId').val(),
            password : $('.password').val()
        },
        error : function() {
            alert('error!');
        },
        success : function(json) {
            
            if (json.res) {
                location.href = '/stat/dashBoard';
            } else {
                alert('아이디 및 비밀번호가 올바르지 않습니다.');
            }
        }
    });
});
</script>
</body>
</html>