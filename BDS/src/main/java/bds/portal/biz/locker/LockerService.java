package bds.portal.biz.locker;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bds.portal.biz.user.UserDao;
import bds.portal.com.constants.PortalConstants;
import bds.portal.com.utils.UnilockUtils;

/**
 * locker service
 *
 * @author ihmoon
 * @created 2018. 01. 08.
 */
@Service
public class LockerService {

    /**
     * 생성자
     */
    public LockerService() {
        // Default Constructor
    }

    /**
     * lockerDao
     */
    @Autowired
    private LockerDao lockerDao;

    /**
     * userDao
     */
    @Autowired
    private UserDao userDao;

    /**
     * 락커 종류 목록 조회
     */
    public List<Object> searchLockerTypeList() {
        return userDao.selectCodeList(PortalConstants.Group.LOCKER_TYPE_CD);
    }

    /**
     * 락커 목록 조회
     */
    public Map<String, Object> searchLockerList(Map<String, Object> pMap) {
        return lockerDao.selectLockerList(pMap);
    }

    /**
     * 락커 등록
     */
    public int createLocker(Map<String, Object> pMap) {
        return lockerDao.insertLocker(pMap);
    }

    /**
     * 락커 삭제
     */
    public int removeLocker(Map<String, Object> pMap) {

        int res = lockerDao.checkLocker(pMap);
        if (res == 0) {
            return lockerDao.deleteLocker(pMap);
        }
        return -1;
    }

    /**
     * 락커 수정
     */
    public int modifyLocker(Map<String, Object> pMap) {

        int res = lockerDao.checkLocker(pMap);
        if (res == 0) {
            return lockerDao.updateLocker(pMap);
        }
        return -1;
    }

    /**
     * 락커 열기
     */
    public int openLocker(Map<String, Object> pMap) {

        pMap.put(PortalConstants.LockerState.KEY, PortalConstants.LockerState.BOOK);
        int cnt = lockerDao.checkLocker(pMap);

        String lockerId = (String)pMap.get("lockerId");
        int res = UnilockUtils.check(lockerId, cnt);
        UnilockUtils.check(lockerId, 8 + cnt);

        return res;
    }
}
