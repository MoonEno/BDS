$(function() {
    $('#userDeviceList').data({
        addList : []
    });
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
 * 저장
 */
$(document).on('click', '#createUser', function() {

    var userId = $('.userId').val().trim();
    var userName = $('.userName').val().trim();
    var empNo = $('.empNo').val().trim();
    var sex = $('.sex').val();
    var basCd = $('.basCd').val();
    var basNm = $('.basCd option:selected').text();
    var cellNo = $('.cellNo').val().trim();
    var memo = $('.memo').val();
    
    var addList = $('#userDeviceList').data().addList;
    
    if (!userId) {
        alert('회원아이디를 입력해주세요.');
        $('.userId').val('');
        return false;
    }
    
    if (userId.length > 45) {
        alert('회원아이디는 45자까지 입력해주세요.');
        return false;
    }
    
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
    
    if (confirm('등록 하시겠습니까?')) {
        
        $.ajax({
            url : '/user/info/createUser',
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
                addList : addList
            },
            error : function() {
                alert('error!');
            },
            success : function(json) {
                
                if (json.res) {
                    var msg = '등록 하였습니다.';
                    
                    if (json.device) {
                        msg += '\n할당중인 기기가 있으니 다시 추가하여 주시기 바랍니다.';
                    }
                    
                    alert(msg);
                    $('#reset').click();
                } else {
                    var msg = '등록을 실패하였습니다.';
                    
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
    $('form').attr('action', '/user/add').submit();
});

/**
 * 목록
 */
$(document).on('click', '#searchUserList', function() {
    $('form').attr('action', '/user').submit();
});