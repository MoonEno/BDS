package bds.portal.com.constants;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 상수 정의
 *
 * @author openit
 * @created 2017. 11. 1.
 */
public class PortalConstants {

    /**
     * 생성자
     */
    private PortalConstants() {
        // Default Constructor
    }

    public static final String SESSION_KEY = "sessVo";

    public static final String RESULT = "res";

    public static final class Type {

        /**
         * 생성자
         */
        private Type() {
            // Default Constructor
        }

        /**
         * 키
         */
        public static final String KEY = "type";

        /**
         * 밴드
         */
        public static final String BAND = "1";

        /**
         * 사원증
         */
        public static final String CARD = "2";

        /**
         * 수동 발권
         */
        public static final String MANUAL = "3";

        /**
         * 일일 이용권
         */
        public static final String DAILY = "4";
    }

    public static final class Group {

        /**
         * 생성자
         */
        private Group() {
            // Default Constructor
        }

        /**
         * 키
         */
        public static final String KEY = "groupCd";

        /**
         * 티켓 종류 코드
         */
        public static final String TICKET_TYPE_CD = "100";

        /**
         * 락커 종류 코드
         */
        public static final String LOCKER_TYPE_CD = "200";

        /**
         * 행동 코드
         */
        public static final String ACT_CD = "300";

        /**
         * 디바이스 종류 코드
         */
        public static final String DEVICE_TYPE_CD = "400";
    }

    public static final class LockerType {

        /**
         * 생성자
         */
        private LockerType() {
            // Default Constructor
        }

        /**
         * 키
         */
        public static final String KEY = "lockerTypeCd";

        /**
         * 락커
         */
        public static final String LOCKER = "200001";

        /**
         * 신발장
         */
        public static final String SHOE = "200002";

        public static boolean trans(Map<String, Object> pMap) {

            String lockerType = (String)pMap.get("lockerType");
            if (StringUtils.isNotBlank(lockerType)) {

                if ("1".equals(lockerType)) {
                    // 락커
                    pMap.put(KEY, LOCKER);
                } else if ("2".equals(lockerType)) {
                    // 신발장
                    pMap.put(KEY, SHOE);
                }

                return true;
            }

            return false;
        }
    }

    public static final class LockerState {

        /**
         * 생성자
         */
        private LockerState() {
            // Default Constructor
        }

        /**
         * 키
         */
        public static final String KEY = "stateCd";

        /**
         * 발권
         */
        public static final String TICKETING = "210001";

        /**
         * 열림
         */
        public static final String OPEN = "210002";

        /**
         * 잠김
         */
        public static final String CLOSE = "210003";

        /**
         * 예약
         */
        public static final String BOOK = "210004";
    }

    public static final class Act {

        /**
         * 생성자
         */
        private Act() {
            // Default Constructor
        }

        /**
         * 키
         */
        public static final String KEY = "actCd";

        /**
         * 발권
         */
        public static final String TICKETING = "300001";

        /**
         * 열림
         */
        public static final String OPEN = "300002";

        /**
         * 잠김
         */
        public static final String CLOSE = "300003";

        /**
         * 퇴장
         */
        public static final String OUT = "300004";
    }
}
