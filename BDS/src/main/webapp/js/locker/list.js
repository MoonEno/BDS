/**
 * 락커 목록 조회
 */
function searchLockerList(pageNo, lockerTypeCd, state, keyword) {
    $.ajax({
        url : '/locker/searchLockerList',
        type : 'post',
        dataType : 'json',
        data : {
            pageNo : pageNo,
            lockerTypeCd : lockerTypeCd,
            state : state,
            keyword : keyword
        },
        error : function() {
            alert('error!');
        },
        success : function(json) {
            
            var list = json.list;
            $('#lockerList').data(list);
            var totalCount = json.totalCount || 0;
            $('.totalCount').html(totalCount);
            var pageNo = json.pageNo || 1;
            $('input[name="pageNo"]').val(pageNo);
            var listSize = json.listSize || 15;
            
            var el = '';
            var len = list.length;
            if (len) {
                for (var i = 0; i < len; i++) {
                    var locker = list[i];
                    el += '<tr data-i="' + i + '">';
                    el += '<td>' + getIndex(pageNo, totalCount, listSize, i) + '</td>';
                    el += '<td>' + locker.LOCKER_ID + '</td>';
                    el += '<td>' + locker.LOCKER_NO + '</td>';
                    el += '<td>' + locker.LOCKER_TYPE_NM + '</td>';
                    el += '<td>' + locker.PRIORITY + '</td>';
                    el += '<td>' + (locker.STATE_CD?'사용중':'대기중') + '</td>';
                    el += '<td>' + (locker.STATE_NM||'') + '</td>';
                    el += '<td>' + (locker.USER_NAME||(locker.STATE_CD?'<span class="patch_free">티켓</span>':'')) + '</td>';
                    el += '<td>' + (locker.UPDT_DT||'') + '</td>';
                    el += '<td><a class="td_btn open">열기</a></td>';
                    el += '<td>' + (locker.STATE_CD?'':'<a class="td_btn upd">수정</a>') + '</td>';
                    el += '<td>' + (locker.STATE_CD?'':'<a class="td_btn del">삭제</a>') + '</td>';
                    el += '</tr>';
                }
                $('#lockerList tbody').html(el);
                $('ul.shp2_paging').o_paging(pageNo, totalCount, listSize, 5);
            } else {
                $('#lockerList tbody').o_nodata($('ul.shp2_paging'));
            }
        }
    });
}

$(function() {
    var pageNo = $('input[name="pageNo"]').val();
    var lockerTypeCd = $('input[name="lockerTypeCd"]').val();
    var state = $('input[name="state"]').val();
    var keyword = $('input[name="keyword"]').val();
    
    $('#lockerTypeCd').val(lockerTypeCd);
    $('#state').val(state);
    $('#keyword').val(keyword);
    
    // 락커 목록 조회
    searchLockerList(pageNo, lockerTypeCd, state, keyword);
});

/**
 * 검색
 */
$(document).on('click', '#searchBtn', function() {
    
    var keyword = $('#keyword').val().trim();
    if (checkKeyword(keyword)) {
        
        var pageNo = '1';
        var lockerTypeCd = $('#lockerTypeCd').val();
        var state = $('#state').val();
        $('input[name="pageNo"]').val(pageNo);
        $('input[name="lockerTypeCd"]').val(lockerTypeCd);
        $('input[name="state"]').val(state);
        $('input[name="keyword"]').val(keyword);
        
        // 락커 목록 조회
        searchLockerList(pageNo, lockerTypeCd, state, keyword);
    }
});

/**
 * 페이지 선택
 */
$(document).on('click', '.shp2_paging a', function() {
    
    var pageNo = $(this).data('page');
    $('input[name="pageNo"]').val(pageNo);
    
    var lockerTypeCd = $('input[name="lockerTypeCd"]').val();
    var state = $('input[name="state"]').val();
    var keyword = $('input[name="keyword"]').val();
    
    // 락커 목록 조회
    searchLockerList(pageNo, lockerTypeCd, state, keyword);
});

/**
 * 열기
 */
$(document).on('click', '#lockerList a.open', function() {
    
    var i = $(this).parents('tr:first').data('i');
    var locker = $('#lockerList').data()[i];
    var lockerId = locker.LOCKER_ID;
    if (confirm(lockerId + ' : ' + locker.LOCKER_TYPE_NM + '-' + locker.LOCKER_NO + '번을 여시겠습니까?')) {
        $.ajax({
            url : '/locker/openLocker',
            type : 'post',
            dataType : 'json',
            data : {
                lockerId : lockerId
            },
            error : function() {
                alert('error!');
            },
            success : function(json) {
                if (json) {
                    alert('락커를 열었습니다.');
                    $('.shp2_paging a.on').click();
                } else {
                    alert('락커 열기를 실패하였습니다.');
                }
            }
        });
    }
});

/**
 * 수정
 */
$(document).on('click', '#lockerList a.upd', function() {
    
    var i = $(this).parents('tr:first').data('i');
    var locker = $('#lockerList').data()[i];
    $('#upd').data(locker);
    searchLocker(locker);
    location.href = '#upd';
});

function searchLocker(locker) {
    $('.popup.upd .lockerId').text(locker.LOCKER_ID);
    $('.popup.upd .lockerTypeCd').val(locker.LOCKER_TYPE_CD);
    $('.popup.upd .lockerNo').val(locker.LOCKER_NO);
    $('.popup.upd .priority').val(locker.PRIORITY);
}

/**
 * 삭제
 */
$(document).on('click', '#lockerList a.del', function() {
    
    var i = $(this).parents('tr:first').data('i');
    removeLocker($('#lockerList').data()[i]);
});

function removeLocker(locker) {
    
    var lockerId = locker.LOCKER_ID;
    if (lockerId && confirm(lockerId + ' : ' + locker.LOCKER_TYPE_NM + '-' + locker.LOCKER_NO + '번을 삭제 하시겠습니까?')) {
        $.ajax({
            url : '/locker/removeLocker',
            type : 'post',
            dataType : 'json',
            data : {
                lockerId : lockerId
            },
            error : function() {
                alert('error!');
            },
            success : function(json) {
                if (json) {
                    if (json > 0) {
                        alert('락커를 삭제 하였습니다.');
                    } else {
                        alert('락커가 사용중입니다.');
                    }
                    $('.popup.upd .close').click();
                    $('.shp2_paging a.on').click();
                } else {
                    alert('락커 삭제를 실패하였습니다.');
                }
            }
        });
    }
}

/**
 * 락커 등록하기 팝업 초기화
 */
$(document).on('click', '.popup.add .reset', function() {
    $('.popup.add .lockerId').val('');
    $('.popup.add .lockerTypeCd').val('');
    $('.popup.add .lockerNo').val('');
    $('.popup.add .priority').val(0);
});

/**
 * 락커 등록하기 팝업 확인
 */
$(document).on('click', '.popup.add .ok', function() {

    var lockerId = $('.popup.add .lockerId').val().trim();
    var lockerTypeCd = $('.popup.add .lockerTypeCd').val();
    var lockerNo = $('.popup.add .lockerNo').val().trim();
    var priority = $('.popup.add .priority').val();
    
    if (!lockerId) {
        alert('락커아이디를 입력해주세요.');
        $('.popup.add .lockerId').val('');
        return false;
    }
    
    if (lockerId.length > 20) {
        alert('락커아이디는 20자까지 입력해주세요.');
        return false;
    }
    
    if (!lockerNo) {
        alert('락커번호를 입력해주세요.');
        $('.popup.add .lockerNo').val('');
        return false;
    }
    
    if (lockerNo.length > 20) {
        alert('락커번호는 20자까지 입력해주세요.');
        return false;
    }
    
    $.ajax({
        url : '/locker/createLocker',
        type : 'post',
        dataType : 'json',
        data : {
            lockerId : lockerId,
            lockerTypeCd : lockerTypeCd,
            lockerNo : lockerNo,
            priority : priority
        },
        error : function() {
            alert('error!');
        },
        success : function(json) {
            if (json) {
                alert('락커를 등록 하였습니다.');
                $('.popup.add .close').click();
                $('.popup.add .cancel').click();
                
                // 락커 목록 조회
                var pageNo = $('input[name="pageNo"]').val();
                var lockerTypeCd = $('input[name="lockerTypeCd"]').val();
                var state = $('input[name="state"]').val();
                var keyword = $('input[name="keyword"]').val();
                searchLockerList(pageNo, lockerTypeCd, state, keyword);
            } else {
                alert('락커 등록을 실패하였습니다.');
            }
        }
    });
});

/**
 * 락커 수정하기 팝업 삭제
 */
$(document).on('click', '.popup.upd .del', function() {
    removeLocker($('#upd').data());
});

/**
 * 락커 수정하기 팝업 초기화
 */
$(document).on('click', '.popup.upd .reset', function() {
    searchLocker($('#upd').data());
});

/**
 * 락커 수정하기 팝업 확인
 */
$(document).on('click', '.popup.upd .ok', function() {

    var lockerTypeCd = $('.popup.upd .lockerTypeCd').val();
    var lockerNo = $('.popup.upd .lockerNo').val().trim();
    var priority = $('.popup.upd .priority').val();
    
    if (!lockerNo) {
        alert('락커번호를 입력해주세요.');
        $('.popup.add .lockerNo').val('');
        return false;
    }
    
    if (lockerNo.length > 20) {
        alert('락커번호는 20자까지 입력해주세요.');
        return false;
    }
    
    var locker = $('#upd').data();
    var lockerId = locker.LOCKER_ID;
    if (lockerId && confirm(lockerId + ' : ' + locker.LOCKER_TYPE_NM + '-' + locker.LOCKER_NO + '번을 수정 하시겠습니까?')) {
        
        $.ajax({
            url : '/locker/modifyLocker',
            type : 'post',
            dataType : 'json',
            data : {
                lockerId : lockerId,
                lockerTypeCd : lockerTypeCd,
                lockerNo : lockerNo,
                priority : priority
            },
            error : function() {
                alert('error!');
            },
            success : function(json) {
                if (json) {
                    if (json > 0) {
                        alert('락커를 수정 하였습니다.');
                    } else {
                        alert('락커가 사용중입니다.');
                    }
                    $('.popup.upd .close').click();
                    $('.shp2_paging a.on').click();
                } else {
                    alert('락커 수정을 실패하였습니다.');
                }
            }
        });
    }
});