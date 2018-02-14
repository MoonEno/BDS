package bds.portal.biz.ticket;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bds.portal.com.utils.PagingUtils;

/**
 * ticket dao
 *
 * @author ihmoon
 * @created 2018. 01. 15.
 */
@Repository
public class TicketDao {

    /**
     * 생성자
     */
    public TicketDao() {
        // Default Constructor
    }

    /**
     * sqlSession
     */
    @Autowired
    private SqlSession sqlSession;

    /**
     * 티켓 목록 조회
     */
    public Map<String, Object> selectTicketList(Map<String, Object> pMap) {
        return PagingUtils.selectPageList(sqlSession, "ticket.selectTicketList", pMap);
    }

    /**
     * 티켓 락커 상태 조회
     */
    public List<Object> selectTicketLockerState(Map<String, Object> pMap) {
        return sqlSession.selectList("ticket.selectTicketLockerState", pMap);
    }

    /**
     * 티켓 락커 삭제
     */
    public int deleteTicketLocker(Map<String, Object> pMap) {
        return sqlSession.delete("ticket.deleteTicketLocker", pMap);
    }

    /**
     * 회원 이력 등록
     */
    public int insertUserHist(Map<String, Object> pMap) {
        return sqlSession.insert("ticket.insertUserHist", pMap);
    }
}
