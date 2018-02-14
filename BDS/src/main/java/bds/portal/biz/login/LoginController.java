package bds.portal.biz.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bds.portal.com.constants.PortalConstants;
import bds.portal.com.vo.SessVo;

/**
 * 로그인 controller
 *
 * @author ihmoon
 * @created 2018. 01. 05.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    /**
     * 생성자
     */
    public LoginController() {
        // Default Constructor
    }

    /**
     * loginService
     */
    @Autowired
    private LoginService loginService;

    //////////////////////////////////// 화면 ////////////////////////////////////

    /**
     * 로그인 화면
     */
    @RequestMapping
    public String login(HttpSession session) {
        session.invalidate();
        return "login/login";
    }

    //////////////////////////////////// JSON ////////////////////////////////////

    /**
     * 로그인
     */
    @ResponseBody
    @RequestMapping(value = "/login")
    public Map<String, Object> login(@RequestParam Map<String, Object> pMap, HttpSession session) {
        Map<String, Object> rMap = new HashMap<>();

        SessVo sessVo = loginService.login(pMap);
        if (sessVo != null) {
            session.setAttribute(PortalConstants.SESSION_KEY, sessVo);
            rMap.put(PortalConstants.RESULT, true);
        } else {
            rMap.put(PortalConstants.RESULT, false);
        }

        return rMap;
    }
}
