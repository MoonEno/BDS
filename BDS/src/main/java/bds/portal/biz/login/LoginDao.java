package bds.portal.biz.login;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bds.portal.com.vo.SessVo;

/**
 * 로그인 dao
 *
 * @author ihmoon
 * @created 2018. 01. 05.
 */
@Repository
public class LoginDao {

    /**
     * 생성자
     */
    public LoginDao() {
        // Default Constructor
    }

    /**
     * sqlSession
     */
    @Autowired
    private SqlSession sqlSession;

    /**
     * 로그인
     */
    public SessVo login(Map<String, Object> pMap) {
        return sqlSession.selectOne("login.login", pMap);
    }
}
