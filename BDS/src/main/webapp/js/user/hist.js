/**
 * 회원 이력 목록 조회
 */
function searchUserHistList(pageNo, startDt, endDt, actCd, keyword) {
    $.ajax({
        url : '/user/hist/searchUserHistList',
        type : 'post',
        dataType : 'json',
        data : {
            userId : $('input[name="userId"]').val(),
            pageNo : pageNo,
            startDt : moment(startDt).format('YYYYMMDD'),
            endDt : moment(endDt).format('YYYYMMDD'),
            actCd : actCd,
            keyword : keyword
        },
        error : function() {
            alert('error!');
        },
        success : function(json) {
            
            var list = json.list;
            var totalCount = json.totalCount || 0;
            $('.totalCount').html(totalCount);
            var pageNo = json.pageNo || 1;
            $('input.hist[name="pageNo"]').val(pageNo);
            var listSize = json.listSize || 15;
            
            var el = '';
            var len = list.length;
            if (len) {
                for (var i = 0; i < len; i++) {
                    var hist = list[i];
                    el += '<tr>';
                    el += '<td>' + getIndex(pageNo, totalCount, listSize, i) + '</td>';
                    el += '<td>' + hist.ACT_NM + '</td>';
                    el += '<td>' + hist.ACT_DT + '</td>';
                    el += '<td>' + hist.USER_NAME + '</td>';
                    el += '<td>' + hist.EMP_NO + '</td>';
                    el += '<td>';
                    if (hist.DEVICE_ID) {
                        if (hist.DEVICE_TYPE_NM) {
                            el += hist.DEVICE_TYPE_NM + '-';
                        }
                        el += hist.DEVICE_ID;
                    }
                    el += '<td>';
                    if (hist.LOCKER_NO) {
                        if (hist.LOCKER_TYPE_NM) {
                            el += hist.LOCKER_TYPE_NM + '-';
                        }
                        el += hist.LOCKER_NO;
                    }
                    el += '</td>';
                    el += '</tr>';
                }
                $('#userHistList tbody').html(el);
                $('ul.shp2_paging').o_paging(pageNo, totalCount, listSize, 5);
            } else {
                $('#userHistList tbody').o_nodata($('ul.shp2_paging'));
            }
        }
    });
}

$(function() {
    
    var startDt = moment().subtract(1, 'weeks').format('YYYY-MM-DD');
    var endDt = moment().format('YYYY-MM-DD');
    
    $('#startDt, input.hist[name="startDt"]').val(startDt);
    $('#endDt, input.hist[name="endDt"]').val(endDt);
    
    // 회원 이력 목록 조회
    searchUserHistList('1', startDt, endDt);
});

/**
 * 검색
 */
$(document).on('click', '#searchBtn', function() {
    
    var keyword = $('#keyword').val().trim();
    if (checkKeyword(keyword)) {
        
        var pageNo = '1';
        var startDt = $('#startDt').val();
        var endDt = $('#endDt').val();
        var actCd = $('#actCd').val();
        $('input.hist[name="pageNo"]').val(pageNo);
        $('input.hist[name="startDt"]').val(startDt);
        $('input.hist[name="endDt"]').val(endDt);
        $('input.hist[name="actCd"]').val(actCd);
        $('input.hist[name="keyword"]').val(keyword);
        
        // 회원 이력 목록 조회
        searchUserHistList(pageNo, startDt, endDt, actCd, keyword);
    }
});

/**
 * 페이지 선택
 */
$(document).on('click', '.shp2_paging a', function() {
    
    var pageNo = $(this).data('page');
    $('input.hist[name="pageNo"]').val(pageNo);
    
    var startDt = $('input.hist[name="startDt"]').val();
    var endDt = $('input.hist[name="endDt"]').val();
    var actCd = $('input.hist[name="actCd"]').val();
    var keyword = $('input.hist[name="keyword"]').val();
    
    // 회원 이력 목록 조회
    searchUserHistList(pageNo, startDt, endDt, actCd, keyword);
});

/**
 * 목록
 */
$(document).on('click', '#searchUserList', function() {
    $('form').submit();
});