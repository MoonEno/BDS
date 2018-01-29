/**
 * 티켓 목록 조회
 */
function searchTicketList(pageNo, ticketCd, state, keyword) {
    $.ajax({
        url : '/ticket/searchTicketList',
        type : 'post',
        dataType : 'json',
        data : {
            pageNo : pageNo,
            ticketCd : ticketCd,
            state : state,
            keyword : keyword
        },
        error : function() {
            alert('error!');
        },
        success : function(json) {
            
            var list = json.list;
            $('#ticketList').data(list);
            var totalCount = json.totalCount || 0;
            $('.totalCount').html(totalCount);
            var pageNo = json.pageNo || 1;
            $('input[name="pageNo"]').val(pageNo);
            var listSize = json.listSize || 15;
            
            var el = '';
            var len = list.length;
            if (len) {
                for (var i = 0; i < len; i++) {
                    var ticket = list[i];
                    el += '<tr data-i="' + i + '">';
                    el += '<td>' + getIndex(pageNo, totalCount, listSize, i) + '</td>';
                    el += '<td>' + ticket.TICKET_ID + '</td>';
                    el += '<td>' + ticket.TICKET_NM + '</td>';
                    el += '<td>' + ticket.CELL_NO + '</td>';
                    el += '<td>';
                    var out = '';
                    if (ticket.STATE) {
                        if (ticket.STATE == 'B') {
                            el += '예약중';
                            out = '<a class="td_btn out">예약취소</a>';
                        } else {
                            el += '이용중';
                            out = '<a class="td_btn out">퇴장</a>';
                        }
                        el += '<br>락커 : ' + ticket.LOCKER_NO1 + ', 신발장 : ' + ticket.LOCKER_NO2;
                    } else {
                        el += '이용완료';
                    }
                    el += '</td>';
                    el += '<td>' + (ticket.FROM_DT||'') + '<br>' + (ticket.TO_DT||'') + '</td>';
                    el += '<td>' + out + '</td>';
                    el += '</tr>';
                }
                $('#ticketList tbody').html(el);
                $('ul.shp2_paging').o_paging(pageNo, totalCount, listSize, 5);
            } else {
                $('#ticketList tbody').o_nodata($('ul.shp2_paging'));
            }
        }
    });
}

$(function() {
    var pageNo = $('input[name="pageNo"]').val();
    var ticketCd = $('input[name="ticketCd"]').val();
    var state = $('input[name="state"]').val();
    var keyword = $('input[name="keyword"]').val();
    
    $('#ticketCd').val(ticketCd);
    $('#state').val(state);
    $('#keyword').val(keyword);
    
    // 티켓 목록 조회
    searchTicketList(pageNo, ticketCd, state, keyword);
});

/**
 * 검색
 */
$(document).on('click', '#searchBtn', function() {
    
    var keyword = $('#keyword').val().trim();
    if (checkKeyword(keyword)) {
        
        var pageNo = '1';
        var ticketCd = $('#ticketCd').val();
        var state = $('#state').val();
        $('input[name="pageNo"]').val(pageNo);
        $('input[name="ticketCd"]').val(ticketCd);
        $('input[name="state"]').val(state);
        $('input[name="keyword"]').val(keyword);
        
        // 티켓 목록 조회
        searchTicketList(pageNo, ticketCd, state, keyword);
    }
});

/**
 * 페이지 선택
 */
$(document).on('click', '.shp2_paging a', function() {
    
    var pageNo = $(this).data('page');
    $('input[name="pageNo"]').val(pageNo);
    
    var ticketCd = $('input[name="ticketCd"]').val();
    var state = $('input[name="state"]').val();
    var keyword = $('input[name="keyword"]').val();
    
    // 티켓 목록 조회
    searchTicketList(pageNo, ticketCd, state, keyword);
});

/**
 * 퇴장
 */
$(document).on('click', '.out', function() {
    
    var i = $(this).parents('tr:first').data('i');
    var ticket = $('#ticketList').data()[i];
    var ticketId = ticket.TICKET_ID;
    var text = $(this).text();
    if (ticketId && confirm(text + ' 하시겠습니까?')) {
        
        $.ajax({
            url : '/ticket/removeTicketLocker',
            type : 'post',
            dataType : 'json',
            data : {
                ticketId : ticketId
            },
            error : function() {
                alert('error!');
            },
            success : function(json) {
                alert(text + ' 하였습니다.');
                $('.shp2_paging a.on').click();
            }
        });
    }
});