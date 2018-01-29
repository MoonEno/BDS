/**
 * userStat 초기화
 * */
function reset() {
	 // 1. 통계기준일시 선택 
	 $('#statChoice1').attr('checked', true);
	 // 2. DATEPICKER > TODAY 설정 
	 $('#startDt').val(moment().format('YYYY-MM-DD'));
	 // 3. 성별 > ALL 설정 
	 $('#sex').val('');
	 // 4. 나이대 > ALL 설정
	 $('#age').val('');
	 
}

/**
 * 사용자 통계 조회
 */
function searchUserStat(pageNo, startDt, dateTypeChoice, age, sex) {
	var startDtTp;
	switch (dateTypeChoice) {
	    case 'day' :
	    	startDtTp = moment(startDt).format('YYYYMM');
	        break;
	        
	    case 'month' :
	    	startDtTp = moment(startDt).format('YYYY');
	        break;
	    
	    default :
	    	startDtTp = moment(startDt).format('YYYYMMDD');
	        break;
	}
	
    $.ajax({
        url : '/stat/searchUserStat',
        type : 'post',
        dataType : 'json',
        data : {
            pageNo : pageNo,
            startDt : startDtTp,
            dateTypeChoice : dateTypeChoice,
            age : age,
            sex : sex
        },
        error : function() {
            alert('error!');
        },
        success : function(json) {
        	// 검색화면 세팅
            $('#startDt').val(moment(startDt).format('YYYY-MM-DD'));
            $('input[name="dateTypeChoice"]:checked').val(dateTypeChoice);
            $('#age').val(age);
            $('#sex').val(sex);
            
            var list = json.list;
            var totalCount = json.totalCount || 0;
            $('.totalCount').html(totalCount);
            var pageNo = json.pageNo || 1;
            var pageNum = pageNo == 1?'':pageNo;
            $('input[name="pageNo"]').val(pageNo);
            var listSize = json.listSize || 15;
            var contEl = '';
            var totalUseCnt = 0;
            var len = list.length;
            if (len) {
                for (var i = 0; i < len; i++) {
                    var stat = list[i];
                    contEl += '<tr>';
                    contEl += '<td>' + getIndex(pageNo, totalCount, listSize, i) + '</td>';
                    contEl += '<td>' + (dateTypeChoice=="time"?stat.STAT_DATE+'시': stat.STAT_DATE)+ '</td>';
                    if (stat.SEX === 'F') {
                        contEl += '<td>여자</td>';
                    } else {
                        contEl += '<td>남자</td>';
                    }
                    
                    if (stat.AGE == '60') {
                        contEl += '<td>' + stat.AGE + '대 이상</td>'
                    } else if (stat.AGE == '0') {
                        contEl += '<td>기타</td>';
                    } else {
                        contEl += '<td>' + stat.AGE + '대</td>';
                    }
                    contEl += '<td>' + stat.USE_CNT + '회</td>';
                    contEl += '<td></td>';
                    contEl += '</tr>';
                    totalUseCnt += stat.USE_CNT;
                }
                $('#userStatList tbody').html(contEl);
                $('ul.shp2_paging').o_paging(pageNo, totalCount, listSize, 5);
                $('.totalCnt').html(totalUseCnt);
            } else {
                $('#userStatList tbody').o_nodata($('ul.shp2_paging'));
            }
        }
    });
}

$(function() {
	/* S : DEAFULT SETTING */
	 reset();
    /* E : DEAFULT SETTING */
    var startDt = moment().format('YYYY-MM-DD');
    var dateTypeChoice = $('input[name="dateTypeChoice"]:checked').val()
    // 사용자 통계 목록 조회
    searchUserStat('1', startDt, dateTypeChoice);
});

/**
 * 검색
 */
$(document).on('click', '#searchBtn', function() {
    
        var pageNo = '1';
        var startDt = $('#startDt').val();
        var dateTypeChoice = $('input[name="dateTypeChoice"]:checked').val();
        var age = $('#age').val();
        var sex = $('#sex').val();
        // 사용자 통계 조회
        searchUserStat('1', startDt, dateTypeChoice, age, sex );
});

/**
 * 검색 초기화
 */
$(document).on('click', '#clearBtn', function() {
    reset();
});

/**
 * 페이지 선택
 */
$(document).on('click', '.shp2_paging a', function() {
    
    var pageNo = $(this).data('page');
    $('input[name="pageNo"]').val(pageNo);
    
    var startDt = $('#startDt').val();
    var dateTypeChoice = $('input[name="dateTypeChoice"]:checked').val();
    var age = $('#age').val();
    var sex = $('#sex').val();
    
    // 회원 이력 목록 조회
    searchUserStat(pageNo, startDt, dateTypeChoice, age, sex );
});

/**
 * 목록
 */
$(document).on('click', '#searchUserList', function() {
    $('form').submit();
});