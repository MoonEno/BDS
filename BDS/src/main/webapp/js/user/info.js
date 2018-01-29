$(function() {
    $.ajax({
        url : '/user/info/searchUser',
        type : 'post',
        dataType : 'json',
        data : {
          userId : $('input[name="userId"]').val()
        },
        error : function() {
            alert('error!');
        },
        success : function(json) {
            
            $('#user').data(json);
            $('.userId').text(json.USER_ID);
            $('.userName').val(json.USER_NAME);
            $('.empNo').val(json.EMP_NO);
            $('.sex').val(json.SEX);
            $('.basCd').val(json.BAS_CD);
            $('.cellNo').val(json.CELL_NO||'');
            $('.inCnt').text(json.IN_CNT);
            var state = '미이용';
            if (json.STATE == 'B') {
                state = '예약중';
                $('#out').text('예약취소').show();
            } else if (json.STATE == 'T') {
                state = '이용중';
                $('#out').text('퇴장').show();
            } else {
                $('#removeUser').show();
            }
            $('.state').text(state);
            $('.lockerNo1').val(json.LOCKER_NO1||'');
            $('.lockerNo2').val(json.LOCKER_NO2||'');
            if (json.LOCKER_NO1) {
                $('.lockerNo1').parents('tr:first').show();
            }
            $('.memo').val(json.MEMO||'');
            
            var list = json.deviceList;
            $('#userDeviceList').data({
                list : list,
                addList : [],
                delList : []
            });
            
            var el = '';
            var len = list.length;
            if (len) {
                for (var i = 0; i < len; i++) {
                    var device = list[i];
                    el += '<tr data-i="' + i + '">';
                    el += '<td>' + (device.DEVICE_ID||'-') + '</td>';
                    el += '<td>' + device.DEVICE_TYPE_NM + '</td>';
                    el += '<td>' + (device.UPDT_DT||'') + '</td>';
                    el += '<td><a class="td_btn del">삭제</a></td>';
                    el += '</tr>';
                }
                $('#userDeviceList tbody').html(el);
            } else {
                $('#userDeviceList tbody').o_nodata();
            }
        }
    });
});

/**
 * 락커 변경
 */
$(document).on('click', '#user a.lockerType', function() {
    
    var lockerType = $(this).is('.lockerType1') ? '1' : '2';
    $.ajax({
        url : '/user/info/searchLockerList',
        type : 'post',
        dataType : 'json',
        data : {
            lockerType : lockerType
        },
        error : function() {
            alert('error!');
        },
        success : function(list) {
            
            $('#lockerList').data({
                type : lockerType,
                list : list
            });
            
            var el = '';
            var len = list.length;
            if (len) {
                for (var i = 0; i < len; i++) {
                    var locker = list[i];
                    el += '<tr data-i="' + i + '">';
                    el += '<td><a>' + locker.LOCKER_NO + '</a></td>';
                    el += '</tr>';
                }
                $('#lockerList tbody').html(el);
            } else {
                $('#lockerList tbody').o_nodata();
            }
            
            location.href = '#locker';
        }
    });
});

/**
 * 락커 선택
 */
$(document).on('click', '#lockerList a', function() {
    
    var i = $(this).parents('tr:first').data('i');
    var locker = $('#lockerList').data().list[i];
    var type = $('#lockerList').data().type;
    
    if (type == '1') {
        $('#user').data().NEW_LOCKER_ID1 = locker.LOCKER_ID;
        $('.lockerNo1').val(locker.LOCKER_NO);
    } else {
        $('#user').data().NEW_LOCKER_ID2 = locker.LOCKER_ID;
        $('.lockerNo2').val(locker.LOCKER_NO);
    }
    
    $('.popup .close').click();
});

/**
 * 디바이스 추가
 */
$(document).on('click', '#userDeviceList a.add', function() {
    
    $.ajax({
        url : '/user/info/searchDeviceList',
        type : 'post',
        dataType : 'json',
        error : function() {
            alert('error!');
        },
        success : function(list) {
            
            $('#deviceList').data(list);
            
            var el = '';
            var len = list.length;
            if (len) {
                
                var addList = $('#userDeviceList').data().addList;
                
                for (var i = 0; i < len; i++) {
                    var device = list[i];
                    var add = false;
                    for (var j in addList) {
                        if (device.UID === addList[j]) {
                            add = true;
                            break;
                        }
                    }
                    if (!add) {
                        el += '<tr data-i="' + i + '">';
                        el += '<td><a>' + (device.DEVICE_ID||'-') + '</a></td>';
                        el += '<td>' + device.DEVICE_TYPE_NM + '</td>';
                        el += '</tr>';
                    }
                }
            }
            
            if (el) {
                $('#deviceList tbody').html(el);
            } else {
                $('#deviceList tbody').o_nodata();
            }
            
            location.href = '#device';
        }
    });
});

/**
 * 디바이스 선택
 */
$(document).on('click', '#deviceList a', function() {
    
    var i = $(this).parents('tr:first').data('i');
    var device = $('#deviceList').data()[i];
    
    var addList = $('#userDeviceList').data().addList;
    var el = '';
    el += '<tr data-i="' + addList.length + '">';
    el += '<td>' + (device.DEVICE_ID||'-') + '</td>';
    el += '<td>' + device.DEVICE_TYPE_NM + '</td>';
    el += '<td>' + (device.UPDT_DT||'') + '</td>';
    el += '<td><a class="td_btn can">취소</a></td>';
    el += '</tr>';
    
    if ($('#userDeviceList tbody tr a').size()) {
        $('#userDeviceList tbody').append(el);
    } else {
        $('#userDeviceList tbody').html(el);
    }
    addList.push(device.UID);
    
    $('.popup .close').click();
});

/**
 * 디바이스 취소
 */
$(document).on('click', '#userDeviceList a.can', function() {
    
    var addList = $('#userDeviceList').data().addList;
    var i = $(this).parents('tr:first').data('i');
    addList[i] = null;
    
    $(this).parents('tr:first').remove();
    if (!$('#userDeviceList tbody tr a').size()) {
        $('#userDeviceList tbody').o_nodata();
    }
});

/**
 * 디바이스 삭제
 */
$(document).on('click', '#userDeviceList a.del', function() {
    
    var delList = $('#userDeviceList').data().delList;
    var i = $(this).parents('tr:first').data('i');
    delList.push($('#userDeviceList').data().list[i].UID);
    
    $(this).parents('tr:first').remove();
    if (!$('#userDeviceList tbody tr a').size()) {
        $('#userDeviceList tbody').o_nodata();
    }
});

/**
 * 퇴장
 */
$(document).on('click', '#out', function() {
    
    var user = $('#user').data();
    var userId = user.USER_ID;
    var text = $(this).text();
    if (userId && confirm(text + ' 하시겠습니까?')) {
        
        $.ajax({
            url : '/user/info/removeUserLocker',
            type : 'post',
            dataType : 'json',
            data : {
                userId : userId
            },
            error : function() {
                alert('error!');
            },
            success : function(json) {
                alert(text + ' 하였습니다.');
                $('#reset').click();
            }
        });
    }
});

/**
 * 삭제
 */
$(document).on('click', '#removeUser', function() {
    
    var user = $('#user').data();
    var userId = user.USER_ID;
    if (userId && confirm('삭제 하시겠습니까?')) {
        
        $.ajax({
            url : '/user/info/removeUser',
            type : 'post',
            dataType : 'json',
            data : {
                userId : userId
            },
            error : function() {
                alert('error!');
            },
            success : function(json) {
                
                if (json) {
                    if (json > 0) {
                        alert('삭제 하였습니다.');
                        $('#searchUserList').click();
                    } else {
                        alert('이용중인 회원입니다.');
                        $('#reset').click();
                    }
                } else {
                    alert('삭제를 실패 하였습니다.');
                }
            }
        });
    }
});

/**
 * 저장
 */
$(document).on('click', '#modifyUser', function() {

    var userName = $('.userName').val().trim();
    var empNo = $('.empNo').val().trim();
    var sex = $('.sex').val();
    var basCd = $('.basCd').val();
    var basNm = $('.basCd option:selected').text();
    var cellNo = $('.cellNo').val().trim();
    var memo = $('.memo').val();
    
    var addList = $('#userDeviceList').data().addList;
    var delList = $('#userDeviceList').data().delList;
    
    if (!userName) {
        alert('회원명을 입력해주세요.');
        $('.userName').val('');
        return false;
    }
    
    if (userName.length > 50) {
        alert('회원명은 50자까지 입력해주세요.');
        return false;
    }
    
    if (!empNo) {
        alert('사번을 입력해주세요.');
        $('.empNo').val('');
        return false;
    }
    
    if (empNo.length > 45) {
        alert('사번은 45자까지 입력해주세요.');
        return false;
    }
    
    var user = $('#user').data();
    var userId = user.USER_ID;
    if (userId && confirm('수정 하시겠습니까?')) {
        
        $.ajax({
            url : '/user/info/modifyUser',
            type : 'post',
            dataType : 'json',
            data : {
                userId : userId,
                userName : userName,
                empNo : empNo,
                sex : sex,
                basCd : basCd,
                basNm : basNm,
                cellNo : cellNo,
                memo : memo,
                lockerId1 : user.LOCKER_ID1,
                newLockerId1 : user.NEW_LOCKER_ID1,
                lockerId2 : user.LOCKER_ID2,
                newLockerId2 : user.NEW_LOCKER_ID2,
                addList : addList,
                delList : delList
            },
            error : function() {
                alert('error!');
            },
            success : function(json) {
                
                if (json.res) {
                    var msg = '수정 하였습니다.';
                    
                    if (json.locker == 'E') {
                        msg += '\n사용중인 락커이니 다시 변경하여 주시기 바랍니다.';
                    } else if (json.locker == 'N') {
                        msg += '\n미이용 상태에서는 락커 변경을 할 수 없습니다.';
                    } else if (json.locker == 'F') {
                        msg += '\n락커 변경을 실패 하였습니다.';
                    }
                    
                    if (json.shoe == 'E') {
                        msg += '\n사용중인 신발장이니 다시 변경하여 주시기 바랍니다.';
                    } else if (json.shoe == 'N') {
                        msg += '\n미이용 상태에서는 신발장 변경을 할 수 없습니다.';
                    } else if (json.shoe == 'F') {
                        msg += '\n신발장 변경을 실패 하였습니다.';
                    }
                    
                    if (json.device) {
                        msg += '\n할당중인 기기가 있으니 다시 추가하여 주시기 바랍니다.';
                    }

                    alert(msg);
                    $('#reset').click();
                } else {
                    var msg = '수정을 실패하였습니다.';
                    
                    if (json.userId) {
                        msg += '\n이미 등록된 회원 입니다.';
                    }
                    
                    if (json.cellNo) {
                        msg += '\n등록된 휴대폰번호 입니다. : ' + json.cellNo;
                    }
                    
                    alert(msg);
                }
            }
        });
    }
});

/**
 * 초기화
 */
$(document).on('click', '#reset', function() {
    $('form').attr('action', '/user/info').submit();
});

/**
 * 이용이력조회
 */
$(document).on('click', '#searchUserHistList', function() {
    $('form').attr('action', '/user/hist').submit();
});

/**
 * 목록
 */
$(document).on('click', '#searchUserList', function() {
    $('form').attr('action', '/user').submit();
});