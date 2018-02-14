package bds.portal.biz.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 회원 controller
 *
 * @author ihmoon
 * @created 2018. 01. 15.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    /**
     * 생성자
     */
    public UserController() {
        // Default Constructor
    }

    /**
     * userService
     */
    @Autowired
    private UserService userService;

    //////////////////////////////////// 화면 ////////////////////////////////////

    /**
     * 회원정보관리 > 회원 목록 화면
     */
    @RequestMapping
    public ModelAndView list() {

        ModelAndView mav = new ModelAndView("user/list");
        mav.addObject("basList", userService.searchUserBasList());
        return mav;
    }

    /**
     * 회원정보관리 > 회원 등록 화면
     */
    @RequestMapping(value = "/add")
    public ModelAndView add() {

        ModelAndView mav = new ModelAndView("user/add");
        mav.addObject("basList", userService.searchUserBasList());
        return mav;
    }

    /**
     * 회원정보관리 > 회원 정보 화면
     */
    @RequestMapping(value = "/info")
    public ModelAndView info() {

        ModelAndView mav = new ModelAndView("user/info");
        mav.addObject("basList", userService.searchUserBasList());
        return mav;
    }

    /**
     * 회원정보관리 > 회원 이용 이력 목록 화면
     */
    @RequestMapping(value = "/hist")
    public ModelAndView hist() {

        ModelAndView mav = new ModelAndView("user/hist");
        mav.addObject("actList", userService.searchActList());
        return mav;
    }

    //////////////////////////////////// JSON ////////////////////////////////////

    /**
     * 회원 목록 조회
     */
    @ResponseBody
    @RequestMapping(value = "/searchUserList")
    public Map<String, Object> searchUserList(@RequestParam Map<String, Object> pMap) {
        return userService.searchUserList(pMap);
    }

    /**
     * 회원 목록 수정
     */
    @ResponseBody
    @RequestMapping(value = "/modifyUserList")
    public int modifyUserList(Object rMap) {
        return userService.modifyUserList((String)((Map<String, Object>)rMap).get("sessAdminId"));
    }

    /**
     * 회원 조회
     */
    @ResponseBody
    @RequestMapping(value = "/info/searchUser")
    public Map<String, Object> searchUser(@RequestParam Map<String, Object> pMap) {
        return userService.searchUser(pMap);
    }

    /**
     * 회원 락커 삭제
     */
    @ResponseBody
    @RequestMapping(value = "/info/removeUserLocker")
    public int removeUserLocker(Object rMap) {
        return userService.removeUserLocker((Map<String, Object>)rMap);
    }

    /**
     * 회원 삭제
     */
    @ResponseBody
    @RequestMapping(value = "/info/removeUser")
    public int removeUser(Object rMap) {
        return userService.removeUser((Map<String, Object>)rMap);
    }

    /**
     * 회원 수정
     */
    @ResponseBody
    @RequestMapping(value = "/info/modifyUser")
    public Map<String, Object> modifyUser(Object rMap) {
        return userService.modifyUser((Map<String, Object>)rMap);
    }

    /**
     * 회원 등록
     */
    @ResponseBody
    @RequestMapping(value = "/info/createUser")
    public Map<String, Object> createUser(Object rMap) {
        return userService.createUser((Map<String, Object>)rMap);
    }

    /**
     * 락커 목록 조회
     */
    @ResponseBody
    @RequestMapping(value = "/info/searchLockerList")
    public List<Object> searchLockerList(@RequestParam Map<String, Object> pMap) {
        return userService.searchLockerList(pMap);
    }

    /**
     * 디바이스 목록 조회
     */
    @ResponseBody
    @RequestMapping(value = "/info/searchDeviceList")
    public List<Object> searchDeviceList() {
        return userService.searchDeviceList();
    }

    /**
     * 회원 이력 목록 조회
     */
    @ResponseBody
    @RequestMapping(value = "/hist/searchUserHistList")
    public Map<String, Object> searchUserHistList(@RequestParam Map<String, Object> pMap) {
        return userService.searchUserHistList(pMap);
    }
}
