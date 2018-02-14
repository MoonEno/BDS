package bds.portal.biz.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bds.portal.com.vo.SessVo;

/**
 * 로그인 service
 *
 * @author ihmoon
 * @created 2018. 01. 05.
 */
@Service
public class LoginService {

    /**
     * 생성자
     */
    public LoginService() {
        // Default Constructor
    }

    /**
     * loginDao
     */
    @Autowired
    private LoginDao loginDao;

    /**
     * 로그인
     */
    public SessVo login(Map<String, Object> pMap) {
        return loginDao.login(pMap);
    }
}
