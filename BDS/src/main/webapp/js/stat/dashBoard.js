/**
 * 사용자 통계 조회
 */
function searchTodayChkInAndOutCount(start, end){
    start += " 00:00:00";
    
    $.ajax({
        url : '/stat/searchTodayChkInAndOutCount',
        type : 'post',
        dataType : 'json',
        data : {
            startDt : start,
            endDt : end,
        },
        error : function() {
            alert('error!');
        },
        success : function(data) {
            var useUserCnt = data.CHC_IN - data.CHC_OUT;
            $('.useUserCnt').text(useUserCnt+" 명");
            $('.chkOutCnt').text(data.CHC_OUT+" 명");
        }
    });
    
}

function searchUseUserChart(startDt) {
    // 시간별 , 일별 현황 가져오기
	var searchDay =  moment(startDt).format('YYYYMMDD');
	var searchMonth =  moment(startDt).format('YYYYMM');
	
    $.ajax({
        url : '/stat/searchUseUser',
        type : 'post',
        dataType : 'json',
        data : {
            searchDay : searchDay,
            searchMonth : searchMonth,
        },
        error : function() {
            alert('error!');
        },
        success : function(data) {
            // 차트 초기화
            $('#timeChart').empty();
            $('#dayChart').empty();
            // S : Morris Chart Draw 
            // 시간별 , 일별현황 데이터 세팅
            var hourly = data[0];
            var daily = data[1];
            // 현재시각, 검색시간 확인
            var today = moment(new Date()).format('YYYYMMDD');
            var thisMonth =  moment(new Date()).format('YYYYMM');
            
            var yearVal = moment(new Date(startDt)).format('YYYY');
            var monthVal = moment(new Date(startDt)).format('MM');
            var lastDay = (thisMonth == searchMonth ? moment(new Date()).format('DD') : new Date(yearVal,monthVal, 0).getDate());
            var todayHour = (today == searchDay ? Number(moment(new Date()).format('H'))+1 : 24);
            var startHour = (today == searchDay ? 0 : 5);
            var dayArray = [];
            var monthArray = [];
            for(startHour; startHour <= todayHour ; startHour++) {
                var dayData = {};
                dayData.hour = startHour+"시";
                
                // 해당 시간의 성별별 이용횟수 초기화
                dayData.male = 0;
                dayData.female = 0;
                    
                for(var x = 0; x < hourly.length; x++) {
                    if (hourly[x].STAT_DATE == startHour) {
                        if (hourly[x].SEX == 'M') {
                            dayData.male = hourly[x].CNT;
                        }
                        if (hourly[x].SEX == 'F'){
                            dayData.female = hourly[x].CNT;
                        }
                        
                    }
                }
                dayArray.push(dayData);
            }
            
            for (var z=1; z <= lastDay; z++) {
                var monthData = {};
                monthData.date = z+"일";
                
                monthData.male = 0;
                monthData.female = 0;
                for (var i=0; i < daily.length; i++) {
                    if (daily[i].STAT_DATE == z) {
                        if (daily[i].SEX == 'M') {
                            monthData.male = daily[i].CNT;
                        }
                        if (daily[i].SEX == 'F') {
                            monthData.female = daily[i].CNT;
                        }
                        
                    }
                }
                monthArray.push(monthData);
            }
            
           
            
            // 시간별현황 차트 DRAW
             Morris.Area({
                element: 'timeChart',
                data : dayArray,
                xkey: 'hour',
                ykeys: ['male', 'female'],
                postUnits: '회',
                ymin: 0,
                ymax: 20,
                hideHover: 'auto',
                behaveLikeLine: true,
                labels: ['남자', '여자'],
                lineColors: ['blue','red'],
                parseTime: false,
            });
            // 일별 현황 차트 DRAW
             Morris.Area({
                element: 'dayChart',
                data : monthArray,
                xkey: 'date',
                ykeys: ['male', 'female'],
                hideHover: 'auto',
                behaveLikeLine: true,
                ymin: 0,
                ymax: 60,
                postUnits: '회',
                labels: ['남자', '여자'],
                lineColors: ['blue','red'],
                parseTime: false,
            });
        }
    });
}

$(function() {
	/* S : DEAFULT SETTING */
    $('#startDt').val(moment().format('YYYY-MM-DD'));
    var startDt = $('#startDt').val();
    
    // MorrisChart를 활용한 일,월별 이용자수 통계
    searchUseUserChart(startDt);
    
    // 현재시각 기준 이용자수 통계
    searchTodayChkInAndOutCount(startDt , moment().format('YYYY-MM-DD HH:mm:ss'))
    
//    /* E : DEAFULT SETTING */
//    // 사용자 통계 목록 조회
});

/**
 * 검색
 */
$(document).on('click', '#searchBtn', function() {
    
        var startDt = $('#startDt').val();
        
        searchUseUserChart(startDt);
});
