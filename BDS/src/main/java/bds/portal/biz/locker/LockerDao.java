package bds.portal.biz.locker;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bds.portal.com.utils.PagingUtils;

/**
 * locker dao
 *
 * @author ihmoon
 * @created 2018. 01. 08.
 */
@Repository
public class LockerDao {

    /**
     * 생성자
     */
    public LockerDao() {
        // Default Constructor
    }

    /**
     * sqlSession
     */
    @Autowired
    private SqlSession sqlSession;

    /**
     * 락커 목록 조회
     */
    public Map<String, Object> selectLockerList(Map<String, Object> pMap) {
        return PagingUtils.selectPageList(sqlSession, "locker.selectLockerList", pMap);
    }

    /**
     * 락커 등록
     */
    public int insertLocker(Map<String, Object> pMap) {
        return sqlSession.insert("locker.insertLocker", pMap);
    }

    /**
     * 락커 사용 여부
     */
    public int checkLocker(Map<String, Object> pMap) {
        return sqlSession.selectOne("locker.checkLocker", pMap);
    }

    /**
     * 락커 삭제
     */
    public int deleteLocker(Map<String, Object> pMap) {
        return sqlSession.delete("locker.deleteLocker", pMap);
    }

    /**
     * 락커 수정
     */
    public int updateLocker(Map<String, Object> pMap) {
        return sqlSession.update("locker.updateLocker", pMap);
    }
}
