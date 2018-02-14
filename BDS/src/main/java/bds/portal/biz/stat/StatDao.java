package bds.portal.biz.stat;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bds.portal.com.utils.PagingUtils;

/**
 * stat dao 통계 페이지
 *
 * @author ihmoon
 * @created 2018. 01. 10.
 */
@Repository
public class StatDao {

    /**
     * 생성자
     */
    public StatDao() {
        // Default Constructor
    }

    /**
     * sqlSession
     */
    @Autowired
    private SqlSession sqlSession;

    /**
     * 사용자 통계 목록 조회
     */
    public Map<String, Object> selectUserStat(Map<String, Object> pMap) {
        return PagingUtils.selectPageList(sqlSession, "stat.selectUserStat", pMap);
    }

    /**
     * 락커 이용 통계 목록 조회
     */
    public Map<String, Object> selectLockerStat(Map<String, Object> pMap) {
        return PagingUtils.selectPageList(sqlSession, "stat.selectLockerStat", pMap);
    }

    /**
     * 통계 > 대쉬보드 이용자 일일 시간별 통계
     */
    public List<Object> selectHourlyUseUserCount(String searchDay) {
        return sqlSession.selectList("stat.selectHourlyUseUserCount", searchDay);
    }

    /**
     * 통계 > 대쉬보드 이용자 월 일별 통계
     */
    public List<Object> selectDailyUseUserCount(String searchMonth) {
        return sqlSession.selectList("stat.selectDailyUseUserCount", searchMonth);
    }

    /**
     * 통계 > 대쉬보드 이용자 현재시각 까지의 체크인 , 체크아웃 총 카운트
     */
    public Map<String, Object> selectTodayChkInAndOutCount(Map<String, Object> pMap) {
        return sqlSession.selectOne("stat.selectTodayChkInAndOutCount", pMap);
    }
}
