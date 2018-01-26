package bds.portal.com.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * web에서 사용되는 paging과 관련된 유틸을 제공한다.
 * 
 * @author openit
 * @created 2017. 11. 1.
 */
public final class PagingUtils {

    /**
     * 생성자
     */
    private PagingUtils() {
        // default constructor
    }

    /**
     * paging 목록을 Map에 담아 반환한다.
     */
    public static Map<String, Object> selectPageList(SqlSession sqlSession, String sqlId, Map<String, Object> pMap) {

        String argPageNo = (String)pMap.get("pageNo");
        String paramListSize = (String)pMap.get("listSize");

        int pageNo = (StringUtils.isEmpty(argPageNo)) ? 1 : Integer.parseInt(argPageNo);
        int listSize = (StringUtils.isEmpty(paramListSize)) ? 15 : Integer.parseInt(paramListSize);

        Map<String, Object> rMap = new HashMap<String, Object>();
        int totalCount = (Integer)sqlSession.selectOne(StringUtils.appendIfMissing(sqlId, "Count"), pMap);
        int checkPageNo = (totalCount - 1) / listSize + 1;
        if (pageNo > checkPageNo) {
            pageNo = checkPageNo;
        }

        if (totalCount > 0) {

            pMap.put("firstLimit", Integer.valueOf((pageNo - 1) * listSize));
            pMap.put("lastLimit", Integer.valueOf(listSize));
            rMap.put("list", sqlSession.selectList(sqlId, pMap));
        } else {
            rMap.put("list", new ArrayList<>());
        }
        rMap.put("totalCount", totalCount);
        rMap.put("pageNo", String.valueOf(pageNo));
        rMap.put("listSize", String.valueOf(listSize));

        return rMap;
    }
}
