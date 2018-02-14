package bds.portal.biz.ticket;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bds.portal.biz.user.UserDao;
import bds.portal.com.constants.PortalConstants;
import bds.portal.com.utils.UnilockUtils;

/**
 * ticket service
 *
 * @author ihmoon
 * @created 2018. 01. 15.
 */
@Service
public class TicketService {

    /**
     * 생성자
     */
    public TicketService() {
        // Default Constructor
    }

    /**
     * ticketDao
     */
    @Autowired
    private TicketDao ticketDao;

    /**
     * userDao
     */
    @Autowired
    private UserDao userDao;

    /**
     * 티켓 종류 목록 조회
     */
    public List<Object> searchTicketTypeList() {
        return userDao.selectCodeList(PortalConstants.Group.TICKET_TYPE_CD);
    }

    /**
     * 티켓 목록 조회
     */
    public Map<String, Object> searchTicketList(Map<String, Object> pMap) {
        pMap.put(PortalConstants.LockerState.KEY, PortalConstants.LockerState.BOOK);
        pMap.put("lockerType1", PortalConstants.LockerType.LOCKER);
        pMap.put("lockerType2", PortalConstants.LockerType.SHOE);
        return ticketDao.selectTicketList(pMap);
    }

    /**
     * 티켓 락커 삭제
     */
    public int removeTicketLocker(Map<String, Object> pMap) {

        List<Object> rList = ticketDao.selectTicketLockerState(pMap);
        int res = ticketDao.deleteTicketLocker(pMap);
        if (res > 0) {

            Map<String, Object> tMap = (Map<String, Object>)rList.get(0);
            if (!PortalConstants.LockerState.BOOK.equals(tMap.get("STATE_CD"))) {

                pMap.put(PortalConstants.Act.KEY, PortalConstants.Act.OUT);
                ticketDao.insertUserHist(pMap);

                String lockerId1 = (String)tMap.get("LOCKER_ID");
                UnilockUtils.check(lockerId1, 0);

                tMap = (Map<String, Object>)rList.get(1);
                String lockerId2 = (String)tMap.get("LOCKER_ID");
                UnilockUtils.check(lockerId2, 0);
            }
        }

        return res;
    }
}
