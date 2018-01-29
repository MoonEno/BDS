/**
 * 디바이스 목록 조회
 */
function searchDeviceList(pageNo, deviceTypeCd, state, keyword) {
    $.ajax({
        url : '/device/searchDeviceList',
        type : 'post',
        dataType : 'json',
        data : {
            pageNo : pageNo,
            deviceTypeCd : deviceTypeCd,
            state : state,
            keyword : keyword
        },
        error : function() {
            alert('error!');
        },
        success : function(json) {
            
            var list = json.list;
            $('#deviceList').data(list);
            var totalCount = json.totalCount || 0;
            $('.totalCount').html(totalCount);
            var pageNo = json.pageNo || 1;
            $('input[name="pageNo"]').val(pageNo);
            var listSize = json.listSize || 15;
            
            var el = '';
            var len = list.length;
            if (len) {
                for (var i = 0; i < len; i++) {
                    var device = list[i];
                    el += '<tr data-i="' + i + '">';
                    el += '<td>' + getIndex(pageNo, totalCount, listSize, i) + '</td>';
                    el += '<td>' + device.DEVICE_ID + '</td>';
                    el += '<td>' + device.DEVICE_TYPE_NM + '</td>';
                    el += '<td>' + (device.USER_NAME?'할당':'미할당') + '</td>';
                    el += '<td>' + (device.USER_NAME||'') + '</td>';
                    el += '<td>' + (device.UPDT_DT||'') + '</td>';
                    el += '<td>' + (device.USER_NAME?'':'<a class="td_btn upd">수정</a>') + '</td>';
                    el += '<td>' + (device.USER_NAME?'':'<a class="td_btn del">삭제</a>') + '</td>';
                    el += '</tr>';
                }
                $('#deviceList tbody').html(el);
                $('ul.shp2_paging').o_paging(pageNo, totalCount, listSize, 5);
            } else {
                $('#deviceList tbody').o_nodata($('ul.shp2_paging'));
            }
        }
    });
}

$(function() {
    var pageNo = $('input[name="pageNo"]').val();
    var deviceTypeCd = $('input[name="deviceTypeCd"]').val();
    var state = $('input[name="state"]').val();
    var keyword = $('input[name="keyword"]').val();
    
    $('#deviceTypeCd').val(deviceTypeCd);
    $('#state').val(state);
    $('#keyword').val(keyword);
    
    // 디바이스 목록 조회
    searchDeviceList(pageNo, deviceTypeCd, state, keyword);
});

/**
 * 검색
 */
$(document).on('click', '#searchBtn', function() {
    
    var keyword = $('#keyword').val().trim();
    if (checkKeyword(keyword)) {
        
        var pageNo = '1';
        var deviceTypeCd = $('#deviceTypeCd').val();
        var state = $('#state').val();
        $('input[name="pageNo"]').val(pageNo);
        $('input[name="deviceTypeCd"]').val(deviceTypeCd);
        $('input[name="state"]').val(state);
        $('input[name="keyword"]').val(keyword);
        
        // 디바이스 목록 조회
        searchDeviceList(pageNo, deviceTypeCd, state, keyword);
    }
});

/**
 * 페이지 선택
 */
$(document).on('click', '.shp2_paging a', function() {
    
    var pageNo = $(this).data('page');
    $('input[name="pageNo"]').val(pageNo);
    
    var deviceTypeCd = $('input[name="deviceTypeCd"]').val();
    var state = $('input[name="state"]').val();
    var keyword = $('input[name="keyword"]').val();
    
    // 디바이스 목록 조회
    searchDeviceList(pageNo, deviceTypeCd, state, keyword);
});

/**
 * 수정
 */
$(document).on('click', '#deviceList a.upd', function() {
    
    var i = $(this).parents('tr:first').data('i');
    var device = $('#deviceList').data()[i];
    $('#upd').data(device);
    searchDevice(device);
    location.href = '#upd';
});

function searchDevice(device) {
    $('.popup.upd .uid').text(device.UID);
    $('.popup.upd .deviceTypeCd').val(device.DEVICE_TYPE_CD);
    $('.popup.upd .deviceId').val(device.DEVICE_ID);
}

/**
 * 삭제
 */
$(document).on('click', '#deviceList a.del', function() {
    
    var i = $(this).parents('tr:first').data('i');
    removeDevice($('#deviceList').data()[i]);
});

function removeDevice(device) {
    
    var uid = device.UID;
    if (uid && confirm(uid + ' : ' + device.DEVICE_TYPE_NM + '-' + device.DEVICE_ID +'를 삭제 하시겠습니까?')) {
        $.ajax({
            url : '/device/removeDevice',
            type : 'post',
            dataType : 'json',
            data : {
                uid : uid
            },
            error : function() {
                alert('error!');
            },
            success : function(json) {
                if (json) {
                    if (json > 0) {
                        alert('디바이스를 삭제 하였습니다.');
                    } else {
                        alert('디바이스가 사용중입니다.');
                    }
                    $('.popup.upd .close').click();
                    $('.shp2_paging a.on').click();
                } else {
                    alert('디바이스 삭제를 실패하였습니다.');
                }
            }
        });
    }
}

/**
 * 디바이스 등록하기 팝업 초기화
 */
$(document).on('click', '.popup.add .reset', function() {
    $('.popup.add .uid').val('');
    $('.popup.add .deviceTypeCd').val('');
    $('.popup.add .deviceId').val('');
});

/**
 * 디바이스 등록하기 팝업 확인
 */
$(document).on('click', '.popup.add .ok', function() {

    var uid = $('.popup.add .uid').val().trim();
    var deviceTypeCd = $('.popup.add .deviceTypeCd').val();
    var deviceId = $('.popup.add .deviceId').val().trim();
    
    if (!uid) {
        alert('UID를 입력해주세요.');
        $('.popup.add .uid').val('');
        return false;
    }
    
    if (uid.length > 45) {
        alert('UID는 45자까지 입력해주세요.');
        return false;
    }
    
    if (!deviceId) {
        alert('디바이스아이디를 입력해주세요.');
        $('.popup.add .deviceId').val('');
        return false;
    }
    
    if (deviceId.length != 10) {
        alert('디바이스아이디는 10자로 입력해주세요.');
        return false;
    }
    
    $.ajax({
        url : '/device/createDevice',
        type : 'post',
        dataType : 'json',
        data : {
            uid : uid,
            deviceTypeCd : deviceTypeCd,
            deviceId : deviceId
        },
        error : function() {
            alert('error!');
        },
        success : function(json) {
            if (json) {
                alert('디바이스를 등록 하였습니다.');
                $('.popup.add .close').click();
                $('.popup.add .cancel').click();
                
                // 디바이스 목록 조회
                var pageNo = $('input[name="pageNo"]').val();
                var deviceTypeCd = $('input[name="deviceTypeCd"]').val();
                var state = $('input[name="state"]').val();
                var keyword = $('input[name="keyword"]').val();
                searchDeviceList(pageNo, deviceTypeCd, state, keyword);
            } else {
                alert('디바이스 등록을 실패하였습니다.');
            }
        }
    });
});

/**
 * 디바이스 수정하기 팝업 삭제
 */
$(document).on('click', '.popup.upd .del', function() {
    removeDevice($('#upd').data());
});

/**
 * 디바이스 수정하기 팝업 초기화
 */
$(document).on('click', '.popup.upd .reset', function() {
    searchDevice($('#upd').data());
});

/**
 * 디바이스 수정하기 팝업 확인
 */
$(document).on('click', '.popup.upd .ok', function() {

    var deviceTypeCd = $('.popup.upd .deviceTypeCd').val();
    var deviceId = $('.popup.upd .deviceId').val().trim();

    if (!deviceId) {
        alert('디바이스아이디를 입력해주세요.');
        $('.popup.upd .deviceId').val('');
        return false;
    }
    
    if (deviceId.length != 10) {
        alert('디바이스아이디는 10자로 입력해주세요.');
        return false;
    }
    
    var device = $('#upd').data();
    var uid = device.UID;
    if (uid && confirm(uid + ' : ' + device.DEVICE_TYPE_NM + '-' + device.DEVICE_ID + '를 수정 하시겠습니까?')) {
        
        $.ajax({
            url : '/device/modifyDevice',
            type : 'post',
            dataType : 'json',
            data : {
                uid : uid,
                deviceTypeCd : deviceTypeCd,
                deviceId : deviceId
            },
            error : function() {
                alert('error!');
            },
            success : function(json) {
                if (json) {
                    if (json > 0) {
                        alert('디바이스를 수정 하였습니다.');
                    } else {
                        alert('디바이스가 사용중입니다.');
                    }
                    $('.popup.upd .close').click();
                    $('.shp2_paging a.on').click();
                } else {
                    alert('디바이스 수정을 실패하였습니다.');
                }
            }
        });
    }
});