package bds.portal.biz.stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * stat service
 *
 * @author ihmoon
 * @created 2018. 01. 10.
 */
@Service
public class StatService {

    /**
     * 생성자
     */
    public StatService() {
        // Default Constructor
    }

    /**
     * statDao
     */
    @Autowired
    private StatDao statDao;

    /**
     * 사용자 통계 조회
     */
    public Map<String, Object> searchUserStat(Map<String, Object> pMap) {
        return statDao.selectUserStat(pMap);
    }

    /**
     * 락커 이용 통계 조회
     */
    public Map<String, Object> searchLockerStat(Map<String, Object> pMap) {
        return statDao.selectLockerStat(pMap);
    }

    /**
     * 통계 > 대쉬보드 이용자 통계
     */
    public List<Object> searchUseUser(Map<String, Object> pMap) {
        String searchDay = String.valueOf(pMap.get("searchDay"));
        String searchMonth = String.valueOf(pMap.get("searchMonth"));
        List<Object> rObj = new ArrayList<>();

        // 일 > 시간별 이용자 현황
        rObj.add(statDao.selectHourlyUseUserCount(searchDay));
        // 월 > 일별 이용자 현황
        rObj.add(statDao.selectDailyUseUserCount(searchMonth));

        return rObj;
    }

    /**
     * 통계 > 대쉬보드 이용자 현재시각 까지의 체크인 , 체크아웃 총 카운트
     */
    public Map<String, Object> searchTodayChkInAndOutCount(Map<String, Object> pMap) {
        return statDao.selectTodayChkInAndOutCount(pMap);
    }
}
