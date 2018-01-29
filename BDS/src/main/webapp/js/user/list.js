/**
 * 회원 목록 조회
 */
function searchUserList(pageNo, basCd, state, keyword) {
    $.ajax({
        url : '/user/searchUserList',
        type : 'post',
        dataType : 'json',
        data : {
            pageNo : pageNo,
            basCd : basCd,
            state : state,
            keyword : keyword
        },
        error : function() {
            alert('error!');
        },
        success : function(json) {
            
            var list = json.list;
            $('#userList').data(list);
            var totalCount = json.totalCount || 0;
            $('.totalCount').html(totalCount);
            var pageNo = json.pageNo || 1;
            $('input[name="pageNo"]').val(pageNo);
            var listSize = json.listSize || 15;
            
            var el = '';
            var len = list.length;
            if (len) {
                for (var i = 0; i < len; i++) {
                    var user = list[i];
                    el += '<tr data-i="' + i + '">';
                    el += '<td>' + getIndex(pageNo, totalCount, listSize, i) + '</td>';
                    el += '<td>' + user.USER_ID + '</td>';
                    el += '<td>' + user.USER_NAME + '</td>';
                    el += '<td>' + user.EMP_NO + '</td>';
                    el += '<td>' + (user.BAS_NM||'') + '</td>';
                    el += '<td>' + user.IN_CNT + '</td>';
                    el += '<td>';
                    if (user.STATE) {
                        if (user.STATE == 'B') {
                            el += '예약중';
                        } else {
                            el += '이용중';
                        }
                        el += '<br>락커 : ' + user.LOCKER_NO1 + ', 신발장 : ' + user.LOCKER_NO2;
                    }
                    el += '</td>';
                    el += '<td>' + (user.LAST_ENTER_DT||'') + '<br>' + (user.LOCKER_NO1?'<br>':(user.LAST_EXIT_DT||'<br>')) + '</td>';
                    el += '<td><a class="td_btn">Detail</a></td>';
                    el += '</tr>';
                }
                $('#userList tbody').html(el);
                $('ul.shp2_paging').o_paging(pageNo, totalCount, listSize, 5);
            } else {
                $('#userList tbody').o_nodata($('ul.shp2_paging'));
            }
        }
    });
}

$(function() {
    var pageNo = $('input[name="pageNo"]').val();
    var basCd = $('input[name="basCd"]').val();
    var state = $('input[name="state"]').val();
    var keyword = $('input[name="keyword"]').val();
    
    $('#basCd').val(basCd);
    $('#state').val(state);
    $('#keyword').val(keyword);
    
    // 회원 목록 조회
    searchUserList(pageNo, basCd, state, keyword);
});

/**
 * 검색
 */
$(document).on('click', '#searchBtn', function() {
    
    var keyword = $('#keyword').val().trim();
    if (checkKeyword(keyword)) {
        
        var pageNo = '1';
        var basCd = $('#basCd').val();
        var state = $('#state').val();
        $('input[name="pageNo"]').val(pageNo);
        $('input[name="basCd"]').val(basCd);
        $('input[name="state"]').val(state);
        $('input[name="keyword"]').val(keyword);
        
        // 회원 목록 조회
        searchUserList(pageNo, basCd, state, keyword);
    }
});

/**
 * 페이지 선택
 */
$(document).on('click', '.shp2_paging a', function() {
    
    var pageNo = $(this).data('page');
    $('input[name="pageNo"]').val(pageNo);
    
    var basCd = $('input[name="basCd"]').val();
    var state = $('input[name="state"]').val();
    var keyword = $('input[name="keyword"]').val();
    
    // 회원 목록 조회
    searchUserList(pageNo, basCd, state, keyword);
});

/**
 * 회원정보 갱신하기
 */
$(document).on('click', '#modifyUserList', function() {
    
    if (confirm('회원정보를 갱신 하시겠습니까?')) {
        $.ajax({
            url : '/user/modifyUserList',
            type : 'post',
            dataType : 'json',
            error : function() {
                alert('error!');
            },
            success : function(json) {
                if (json) {
                    alert(json + '명의 회원정보를 갱신하였습니다.');
                    location.href = '';
                } else {
                    alert('회원정보 갱신을 실패하였습니다.');
                }
            }
        });
    }
});

/**
 * 등록하기
 */
$(document).on('click', '#createUser', function() {
    $('form').attr('action', '/user/add').submit();
});

/**
 * Detail
 */
$(document).on('click', '#userList a.td_btn', function() {
    
    var i = $(this).parents('tr:first').data('i');
    var user = $('#userList').data()[i];
    $('input[name="userId"]').val(user.USER_ID);
    $('form').attr('action', '/user/info').submit();
});