package bds.portal.biz.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bds.portal.com.constants.PortalConstants;
import bds.portal.com.utils.CfcUtils;
import bds.portal.com.utils.UnilockUtils;

/**
 * 회원 service
 * 
 * @author ihmoon
 * @created 2017. 11. 1.
 */
@Service
public class UserService {

    /**
     * 생성자
     */
    public UserService() {
        // Default Constructor
    }

    /**
     * userDao
     */
    @Autowired
    private UserDao userDao;

    /**
     * 종목 목록 조회
     */
    public List<Object> searchUserBasList() {
        return userDao.selectUserBasList();
    }

    /**
     * 회원 목록 조회
     */
    public Map<String, Object> searchUserList(Map<String, Object> pMap) {
        pMap.put(PortalConstants.LockerState.KEY, PortalConstants.LockerState.BOOK);
        pMap.put("lockerType1", PortalConstants.LockerType.LOCKER);
        pMap.put("lockerType2", PortalConstants.LockerType.SHOE);
        return userDao.selectUserList(pMap);
    }

    /**
     * 휴대폰번호 변환
     */
    private String transCellNo(Object hphNo) {

        String cellNo = (String)hphNo;
        if (StringUtils.isNotBlank(cellNo)) {
            cellNo = StringUtils.replace(cellNo, " ", "").replace("+", "").replace("-", "");
            if (cellNo.startsWith("82")) {
                cellNo = cellNo.substring(2);
            }
            if (cellNo.startsWith("1")) {
                cellNo = "0" + cellNo;
            }
            return cellNo;
        }
        return null;
    }

    /**
     * 회원 목록 수정
     */
    public int modifyUserList(String sessAdminId) {

        int res = 0;

        List<Object> rList = CfcUtils.getUserList();
        if (rList != null && !rList.isEmpty()) {
            for (int i = 0, len = rList.size(); i < len; i++) {
                Map<String, Object> pMap = (Map<String, Object>)rList.get(i);
                pMap.put("sessAdminId", sessAdminId);
                pMap.put("cellNo", transCellNo(pMap.get("hphNo")));
                try {
                    userDao.updateUserCfc(pMap);
                    res++;
                } catch (Exception e) {
                }
            }
        }

        return res;
    }

    /**
     * 회원 조회
     */
    public Map<String, Object> searchUser(Map<String, Object> pMap) {

        pMap.put(PortalConstants.LockerState.KEY, PortalConstants.LockerState.BOOK);
        pMap.put("lockerType1", PortalConstants.LockerType.LOCKER);
        pMap.put("lockerType2", PortalConstants.LockerType.SHOE);
        Map<String, Object> userMap = userDao.selectUser(pMap);
        if (userMap != null && !userMap.isEmpty()) {
            userMap.put("deviceList", userDao.selectUserDeviceList(pMap));
        }

        return userMap;
    }

    /**
     * 락커 목록 조회
     */
    public List<Object> searchLockerList(Map<String, Object> pMap) {
        PortalConstants.LockerType.trans(pMap);
        return userDao.selectLockerList(pMap);
    }

    /**
     * 디바이스 목록 조회
     */
    public List<Object> searchDeviceList() {
        return userDao.selectDeviceList();
    }

    /**
     * 회원 락커 삭제
     */
    public int removeUserLocker(Map<String, Object> pMap) {

        List<Object> rList = userDao.selectUserLockerState(pMap);
        int res = userDao.deleteUserLocker(pMap);
        if (res > 0) {

            Map<String, Object> tMap = (Map<String, Object>)rList.get(0);
            if (!PortalConstants.LockerState.BOOK.equals(tMap.get("STATE_CD"))) {

                userDao.updateUserExit(pMap);
                pMap.put("uid", tMap.get("UID"));
                pMap.put(PortalConstants.Act.KEY, PortalConstants.Act.OUT);
                userDao.insertUserHist(pMap);

                String lockerId1 = (String)tMap.get("LOCKER_ID");
                UnilockUtils.check(lockerId1, 0);

                tMap = (Map<String, Object>)rList.get(1);
                String lockerId2 = (String)tMap.get("LOCKER_ID");
                UnilockUtils.check(lockerId2, 0);
            }
        }

        return res;
    }

    /**
     * 회원 삭제
     */
    public int removeUser(Map<String, Object> pMap) {

        int res = -1;
        List<Object> rList = userDao.selectUserLockerState(pMap);
        if (rList == null || rList.isEmpty()) {
            res = userDao.deleteUser(pMap);
        }

        return res;
    }

    /**
     * 회원 락커 변경
     */
    private String changeUserLocker(Map<String, Object> pMap, String lockerId, String newLockerId) {

        String locker = "S";
        if (StringUtils.isNotBlank(newLockerId)) {

            locker = "F";

            pMap.put("newLockerId", newLockerId);
            pMap.put("lockerId", lockerId);

            int res = userDao.checkLocker(pMap);
            if (res == 0) {

                Map<String, Object> lockerMap = userDao.selectUserLocker(pMap);
                if (lockerMap != null && !lockerMap.isEmpty()) {

                    String type = PortalConstants.Type.CARD;
                    String key = (String)lockerMap.get("PASSWORD");
                    if (StringUtils.isBlank(key)) {
                        type = PortalConstants.Type.BAND;
                        key = (String)lockerMap.get("UID");
                    }

                    res = UnilockUtils.setKey(newLockerId, type, key);
                    if (res > 0) {

                        String state = (String)lockerMap.get("STATE_CD");
                        if (!PortalConstants.LockerState.BOOK.equals(state)) {

                            UnilockUtils.check(newLockerId, 1);
                            UnilockUtils.check(lockerId, 0);
                        }

                        res = userDao.changeUserLocker(pMap);
                        if (res > 0) {
                            locker = "S";
                        } else {
                            if (!PortalConstants.LockerState.BOOK.equals(state)) {
                                UnilockUtils.check(newLockerId, 0);
                                UnilockUtils.check(lockerId, 1);
                            }
                        }
                    }
                } else {
                    locker = "N";
                }
            } else {
                locker = "E";
            }
        }

        return locker;
    }

    /**
     * 회원 수정
     */
    public Map<String, Object> modifyUser(Map<String, Object> pMap) {

        Map<String, Object> rMap = new HashMap<>();

        String cellNo = transCellNo(pMap.get("cellNo"));
        pMap.put("cellNo", cellNo);
        if (cellNo != null) {
            String userId = userDao.checkCellNo(pMap);
            if (userId != null && !userId.equals(pMap.get("userId"))) {
                rMap.put("cellNo", userId);
                rMap.put(PortalConstants.RESULT, false);
                return rMap;
            }
        }

        int res = userDao.updateUser(pMap);
        if (res > 0) {

            String locker = changeUserLocker(pMap, (String)pMap.get("lockerId1"), (String)pMap.get("newLockerId1"));
            rMap.put("locker", locker);

            String shoe = changeUserLocker(pMap, (String)pMap.get("lockerId2"), (String)pMap.get("newLockerId2"));
            rMap.put("shoe", shoe);

            String[] addList = (String[])pMap.get("addList");
            if (addList != null && addList.length > 0) {
                res = userDao.insertUserDeviceList(pMap);
                if (res != addList.length) {
                    rMap.put("device", "exist");
                }
            }

            String[] delList = (String[])pMap.get("delList");
            if (delList != null && delList.length > 0) {
                userDao.deleteUserDeviceList(pMap);
            }

            rMap.put(PortalConstants.RESULT, true);
        } else {
            rMap.put(PortalConstants.RESULT, false);
        }
        return rMap;
    }

    /**
     * 회원 등록
     */
    public Map<String, Object> createUser(Map<String, Object> pMap) {

        Map<String, Object> rMap = new HashMap<>();

        if (userDao.checkUserId(pMap) > 0) {
            rMap.put("userId", "exist");
            rMap.put(PortalConstants.RESULT, false);
            return rMap;
        }

        String cellNo = transCellNo(pMap.get("cellNo"));
        pMap.put("cellNo", cellNo);
        if (cellNo != null) {
            String userId = userDao.checkCellNo(pMap);
            if (userId != null) {
                rMap.put("cellNo", userId);
                rMap.put(PortalConstants.RESULT, false);
                return rMap;
            }
        }

        int res = userDao.insertUser(pMap);
        if (res > 0) {

            String[] addList = (String[])pMap.get("addList");
            if (addList != null && addList.length > 0) {
                res = userDao.insertUserDeviceList(pMap);
                if (res != addList.length) {
                    rMap.put("device", "exist");
                }
            }

            rMap.put(PortalConstants.RESULT, true);
        } else {
            rMap.put(PortalConstants.RESULT, false);
        }
        return rMap;
    }

    /**
     * 회원 이력 목록 조회
     */
    public Map<String, Object> searchUserHistList(Map<String, Object> pMap) {
        return userDao.selectUserHistList(pMap);
    }

    /**
     * 행동 목록 조회
     */
    public List<Object> searchActList() {
        return userDao.selectCodeList(PortalConstants.Group.ACT_CD);
    }
}
