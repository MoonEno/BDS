package bds.portal.biz.device;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bds.portal.com.utils.PagingUtils;

/**
 * device dao
 *
 * @author ihmoon
 * @created 2018. 01. 25.
 */
@Repository
public class DeviceDao {

    /**
     * 생성자
     */
    public DeviceDao() {
        // Default Constructor
    }

    /**
     * sqlSession
     */
    @Autowired
    private SqlSession sqlSession;

    /**
     * 디바이스 목록 조회
     */
    public Map<String, Object> selectDeviceList(Map<String, Object> pMap) {
        return PagingUtils.selectPageList(sqlSession, "device.selectDeviceList", pMap);
    }

    /**
     * 디바이스 등록
     */
    public int insertDevice(Map<String, Object> pMap) {
        return sqlSession.insert("device.insertDevice", pMap);
    }

    /**
     * 디바이스 사용 여부
     */
    public int checkDevice(Map<String, Object> pMap) {
        return sqlSession.selectOne("device.checkDevice", pMap);
    }

    /**
     * 디바이스 삭제
     */
    public int deleteDevice(Map<String, Object> pMap) {
        return sqlSession.delete("device.deleteDevice", pMap);
    }

    /**
     * 디바이스 수정
     */
    public int updateDevice(Map<String, Object> pMap) {
        return sqlSession.update("device.updateDevice", pMap);
    }
}
