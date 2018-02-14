package bds.portal.biz.locker;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * locker controller
 *
 * @author ihmoon
 * @created 2018. 01. 25.
 */
@Controller
@RequestMapping(value = "/locker")
public class LockerController {

    /**
     * 생성자
     */
    public LockerController() {
        // Default Constructor
    }

    /**
     * lockerService
     */
    @Autowired
    private LockerService lockerService;

    //////////////////////////////////// 화면 ////////////////////////////////////

    /**
     * 락커정보관리 > 락커 목록 화면
     */
    @RequestMapping
    public ModelAndView list() {

        ModelAndView mav = new ModelAndView("locker/list");
        mav.addObject("lockerTypeList", lockerService.searchLockerTypeList());
        return mav;
    }

    //////////////////////////////////// JSON ////////////////////////////////////

    /**
     * 락커 목록 조회
     */
    @ResponseBody
    @RequestMapping(value = "/searchLockerList")
    public Map<String, Object> searchLockerList(@RequestParam Map<String, Object> pMap) {
        return lockerService.searchLockerList(pMap);
    }

    /**
     * 락커 등록
     */
    @ResponseBody
    @RequestMapping(value = "/createLocker")
    public int createLocker(Object rMap) {
        return lockerService.createLocker((Map<String, Object>)rMap);
    }

    /**
     * 락커 삭제
     */
    @ResponseBody
    @RequestMapping(value = "/removeLocker")
    public int removeLocker(@RequestParam Map<String, Object> pMap) {
        return lockerService.removeLocker(pMap);
    }

    /**
     * 락커 수정
     */
    @ResponseBody
    @RequestMapping(value = "/modifyLocker")
    public int modifyLocker(Object rMap) {
        return lockerService.modifyLocker((Map<String, Object>)rMap);
    }

    /**
     * 락커 열기
     */
    @ResponseBody
    @RequestMapping(value = "/openLocker")
    public int openLocker(Object rMap) {
        return lockerService.openLocker((Map<String, Object>)rMap);
    }
}
