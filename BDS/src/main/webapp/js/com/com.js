function getIndex(pageNo, totalCount, listSize, i) {
    return (totalCount - (listSize * (pageNo - 1))) - i;
}

function checkKeyword(str) {
    
    if (str.search(/%/) != -1) {
        alert('검색어에 %는 사용할 수 없습니다.');
        return false;
    }
    
    return true;
}

$(function() {
    $('#keyword').o_enter($('#searchBtn'));
    
    $('.datepicker_input').datepicker({
        dateFormat: 'yy-mm-dd',
        showOtherMonths: true,
        selectOtherMonths: true,
        showMonthAfterYear: true
    }).datepicker('widget').wrap('<div class="ll-skin-melon"/>');
})

/**
 * 엔터키 입력
 * @param $searchBtn 검색버튼
 */
$.fn.o_enter = function($searchBtn) {
    $(this).keydown(function(e) {
        if (e.which == 13) {
            e.preventDefault();
            $searchBtn.click();
        }
    });
}

/**
 * 검색결과 없음
 * @param $paging 페이징
 */
$.fn.o_nodata = function($paging) {
    var cols = $(this).siblings('colgroup').children().size();
    $(this).html('<tr><td colspan="' + cols + '" class="h70">검색결과가 없습니다.</td></tr>');
    if ($paging) {
        $paging.html('');
    }
}

/**
 * 페이징
 * @param pageNo 현재 페이지
 * @param totalCount 전체 개수
 * @param listSize 목록 개수
 * @param tabSize 페이지 개수
 */
$.fn.o_paging = function(pageNo, totalCount, listSize, tabSize) {
    
    var tabCount = parseInt((totalCount / listSize), 10);
    if ((totalCount % listSize) > 0) { 
        tabCount++;
    }       

    var startPageNo = parseInt((pageNo / tabSize), 10);
    if ((pageNo % tabSize) > 0) {
        startPageNo++;
    }
    if (startPageNo == 0) {
        startPageNo = 1;
    }
    startPageNo = (startPageNo * tabSize) - (tabSize - 1);

    var endPageNo = startPageNo + (tabSize - 1);
    if (endPageNo > tabCount) {
        endPageNo = tabCount;
    }
    
    //  paging
    var page = '';
    if(totalCount != null && totalCount > 0){
    
        // 이전
        if(startPageNo > 1){
            page += '<li><a class="pre" data-page="' + (startPageNo - tabSize) + '"><img src="../images/pagenate_arrow.png" alt="이전" /></a></li>';
        }
        // 번호
        for(var i = startPageNo; i <= endPageNo; i++) {
            page += '<li><a data-page="' + i + '" class="' + ((i == pageNo) ? 'on' : '') + '">' + i + '</a></li>';
        }
        // 다음
        if(tabCount > endPageNo){
            page += '<li><a class="next" data-page="' + (startPageNo + tabSize) + '"><img src="../images/pagenate_arrow.png" alt="다음" /></a></li>';
        }
    }

    $(this).html(page);
}