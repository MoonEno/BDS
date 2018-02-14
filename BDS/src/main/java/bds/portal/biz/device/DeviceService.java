package bds.portal.biz.device;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bds.portal.biz.user.UserDao;
import bds.portal.com.constants.PortalConstants;

/**
 * device service
 *
 * @author ihmoon
 * @created 2017. 01. 25.
 */
@Service
public class DeviceService {

    /**
     * 생성자
     */
    public DeviceService() {
        // Default Constructor
    }

    /**
     * deviceDao
     */
    @Autowired
    private DeviceDao deviceDao;

    /**
     * userDao
     */
    @Autowired
    private UserDao userDao;

    /**
     * 디바이스 종류 목록 조회
     */
    public List<Object> searchDeviceTypeList() {
        return userDao.selectCodeList(PortalConstants.Group.DEVICE_TYPE_CD);
    }

    /**
     * 디바이스 목록 조회
     */
    public Map<String, Object> searchDeviceList(Map<String, Object> pMap) {
        return deviceDao.selectDeviceList(pMap);
    }

    /**
     * 디바이스 등록
     */
    public int createDevice(Map<String, Object> pMap) {

        String deviceId = (String)pMap.get("deviceId");
        pMap.put("majorId", deviceId.substring(0, 5));
        pMap.put("minorId", deviceId.substring(5));
        return deviceDao.insertDevice(pMap);
    }

    /**
     * 디바이스 삭제
     */
    public int removeDevice(Map<String, Object> pMap) {

        int res = deviceDao.checkDevice(pMap);
        if (res == 0) {
            return deviceDao.deleteDevice(pMap);
        }
        return 0;
    }

    /**
     * 디바이스 수정
     */
    public int modifyDevice(Map<String, Object> pMap) {

        String deviceId = (String)pMap.get("deviceId");
        pMap.put("majorId", deviceId.substring(0, 5));
        pMap.put("minorId", deviceId.substring(5));
        int res = deviceDao.checkDevice(pMap);
        if (res == 0) {
            return deviceDao.updateDevice(pMap);
        }
        return 0;
    }
}
