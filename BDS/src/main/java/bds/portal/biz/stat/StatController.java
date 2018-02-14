package bds.portal.biz.stat;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bds.portal.biz.locker.LockerService;

/**
 * stat controller
 *
 * @author ihmoon
 * @created 2018. 01. 10.
 */
@Controller
@RequestMapping(value = "/stat")
public class StatController {

    /**
     * 생성자
     */
    public StatController() {
        // Default Constructor
    }

    /**
     * statService
     */
    @Autowired
    private StatService statService;

    @Autowired
    private LockerService lockerService;

    //////////////////////////////////// 화면 ////////////////////////////////////

    /**
     * 통계 > 사용자 통계 페이지 이동
     */
    @RequestMapping(value = "/userStat")
    public ModelAndView viewUserStat() {
        ModelAndView mav = new ModelAndView("stat/userStat");
        return mav;
    }

    //////////////////////////////////// JSON ////////////////////////////////////

    /**
     * 사용자 통계 목록 조회
     */
    @ResponseBody
    @RequestMapping(value = "/searchUserStat")
    public Map<String, Object> searchUserStat(@RequestParam Map<String, Object> pMap) {
        return statService.searchUserStat(pMap);
    }

    /**
     * 통계 > 락커 사용 통계 페이지 이동
     */
    @RequestMapping(value = "/lockerStat")
    public ModelAndView viewLockerStat() {
        ModelAndView mav = new ModelAndView("stat/lockerStat");
        mav.addObject("lockerTypeList", lockerService.searchLockerTypeList());
        return mav;
    }

    /**
     * 락커 사용 통계 목록 조회
     */
    @ResponseBody
    @RequestMapping(value = "/searchLockerStat")
    public Map<String, Object> searchLockerStat(@RequestParam Map<String, Object> pMap) {
        return statService.searchLockerStat(pMap);
    }

    /**
     * 통계 > 대쉬보드 페이지 이동
     */
    @RequestMapping(value = "/dashBoard")
    public ModelAndView viewdashBoard() {
        ModelAndView mav = new ModelAndView("stat/dashBoard");
        return mav;
    }

    /**
     * 통계 > 대쉬보드 차트 데이터
     */
    @ResponseBody
    @RequestMapping(value = "/searchUseUser")
    public List<Object> searchUseUser(@RequestParam Map<String, Object> pMap) {
        return statService.searchUseUser(pMap);
    }

    /**
     * 통계 > 대쉬보드 이용자 현재시각 까지의 체크인 , 체크아웃 총 카운트
     */
    @ResponseBody
    @RequestMapping(value = "/searchTodayChkInAndOutCount")
    public Map<String, Object> searchTodayChkInAndOutCount(@RequestParam Map<String, Object> pMap) {
        return statService.searchTodayChkInAndOutCount(pMap);
    }

}
