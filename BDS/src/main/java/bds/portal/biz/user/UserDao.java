package bds.portal.biz.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bds.portal.com.utils.PagingUtils;

/**
 * 회원 dao
 *
 * @author ihmoon
 * @created 2018. 01. 15.
 */
@Repository
public class UserDao {

    /**
     * 생성자
     */
    public UserDao() {
        // Default Constructor
    }

    /**
     * sqlSession
     */
    @Autowired
    private SqlSession sqlSession;

    /**
     * 코드 목록 조회
     */
    public List<Object> selectCodeList(String groupCd) {
        return sqlSession.selectList("user.selectCodeList", groupCd);
    }

    /**
     * 종목 목록 조회
     */
    public List<Object> selectUserBasList() {
        return sqlSession.selectList("user.selectUserBasList");
    }

    /**
     * 회원 목록 조회
     */
    public Map<String, Object> selectUserList(Map<String, Object> pMap) {
        return PagingUtils.selectPageList(sqlSession, "user.selectUserList", pMap);
    }

    /**
     * 회원 갱신
     */
    public int updateUserCfc(Map<String, Object> pMap) {
        return sqlSession.update("user.updateUserCfc", pMap);
    }

    /**
     * 회원 조회
     */
    public Map<String, Object> selectUser(Map<String, Object> pMap) {
        return sqlSession.selectOne("user.selectUser", pMap);
    }

    /**
     * 회원 디바이스 목록 조회
     */
    public List<Object> selectUserDeviceList(Map<String, Object> pMap) {
        return sqlSession.selectList("user.selectUserDeviceList", pMap);
    }

    /**
     * 락커 목록 조회
     */
    public List<Object> selectLockerList(Map<String, Object> pMap) {
        return sqlSession.selectList("user.selectLockerList", pMap);
    }

    /**
     * 디바이스 목록 조회
     */
    public List<Object> selectDeviceList() {
        return sqlSession.selectList("user.selectDeviceList");
    }

    /**
     * 회원 락커 상태 조회
     */
    public List<Object> selectUserLockerState(Map<String, Object> pMap) {
        return sqlSession.selectList("user.selectUserLockerState", pMap);
    }

    /**
     * 회원 락커 삭제
     */
    public int deleteUserLocker(Map<String, Object> pMap) {
        return sqlSession.delete("user.deleteUserLocker", pMap);
    }

    /**
     * 회원 삭제
     */
    public int deleteUser(Map<String, Object> pMap) {
        return sqlSession.delete("user.deleteUser", pMap);
    }

    /**
     * 회원 이력 등록
     */
    public int insertUserHist(Map<String, Object> pMap) {
        return sqlSession.insert("user.insertUserHist", pMap);
    }

    /**
     * 회원 퇴장 일시 수정
     */
    public int updateUserExit(Map<String, Object> pMap) {
        return sqlSession.update("user.updateUserExit", pMap);
    }

    /**
     * 락커 사용 여부
     */
    public int checkLocker(Map<String, Object> pMap) {
        return sqlSession.selectOne("user.checkLocker", pMap);
    }

    /**
     * 회원 락커 조회
     */
    public Map<String, Object> selectUserLocker(Map<String, Object> pMap) {
        return sqlSession.selectOne("user.selectUserLocker", pMap);
    }

    /**
     * 회원 락커 변경
     */
    public int changeUserLocker(Map<String, Object> pMap) {
        return sqlSession.update("user.changeUserLocker", pMap);
    }

    /**
     * 회원 수정
     */
    public int updateUser(Map<String, Object> pMap) {
        return sqlSession.update("user.updateUser", pMap);
    }

    /**
     * 휴대폰번호 중복 확인
     */
    public String checkCellNo(Map<String, Object> pMap) {
        return sqlSession.selectOne("user.checkCellNo", pMap);
    }

    /**
     * 회원 아이디 중복 확인
     */
    public int checkUserId(Map<String, Object> pMap) {
        return sqlSession.selectOne("user.checkUserId", pMap);
    }

    /**
     * 회원 등록
     */
    public int insertUser(Map<String, Object> pMap) {
        return sqlSession.insert("user.insertUser", pMap);
    }

    /**
     * 회원 디바이스 목록 등록
     */
    public int insertUserDeviceList(Map<String, Object> pMap) {
        return sqlSession.insert("user.insertUserDeviceList", pMap);
    }

    /**
     * 회원 디바이스 목록 삭제
     */
    public int deleteUserDeviceList(Map<String, Object> pMap) {
        return sqlSession.delete("user.deleteUserDeviceList", pMap);
    }

    /**
     * 회원 이력 목록 조회
     */
    public Map<String, Object> selectUserHistList(Map<String, Object> pMap) {
        return PagingUtils.selectPageList(sqlSession, "user.selectUserHistList", pMap);
    }
}
