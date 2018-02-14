package bds.portal.biz.ticket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * ticket controller
 *
 * @author ihmoon
 * @created 2018. 01. 13.
 */
@Controller
@RequestMapping(value = "/ticket")
public class TicketController {

    /**
     * 생성자
     */
    public TicketController() {
        // Default Constructor
    }

    /**
     * ticketService
     */
    @Autowired
    private TicketService ticketService;

    //////////////////////////////////// 화면 ////////////////////////////////////

    /**
     * 티켓정보관리 > 티켓 목록 화면
     */
    @RequestMapping
    public ModelAndView list() {

        ModelAndView mav = new ModelAndView("ticket/list");
        mav.addObject("ticketTypeList", ticketService.searchTicketTypeList());
        return mav;
    }

    //////////////////////////////////// JSON ////////////////////////////////////

    /**
     * 티켓 목록 조회
     */
    @ResponseBody
    @RequestMapping(value = "/searchTicketList")
    public Map<String, Object> searchTicketList(@RequestParam Map<String, Object> pMap) {
        return ticketService.searchTicketList(pMap);
    }

    /**
     * 티켓 락커 삭제
     */
    @ResponseBody
    @RequestMapping(value = "/removeTicketLocker")
    public int removeTicketLocker(Object rMap) {
        return ticketService.removeTicketLocker((Map<String, Object>)rMap);
    }
}
