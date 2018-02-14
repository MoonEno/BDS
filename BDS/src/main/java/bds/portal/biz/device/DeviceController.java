package bds.portal.biz.device;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * device controller
 *
 * @author ihmoon
 * @created 2018. 01. 25.
 */
@Controller
@RequestMapping(value = "/device")
public class DeviceController {

    /**
     * 생성자
     */
    public DeviceController() {
        // Default Constructor
    }

    /**
     * deviceService
     */
    @Autowired
    private DeviceService deviceService;

    //////////////////////////////////// 화면 ////////////////////////////////////

    /**
     * 디바이스정보관리 > 디바이스 목록 화면
     */
    @RequestMapping
    public ModelAndView list() {

        ModelAndView mav = new ModelAndView("device/list");
        mav.addObject("deviceTypeList", deviceService.searchDeviceTypeList());
        return mav;
    }

    //////////////////////////////////// JSON ////////////////////////////////////

    /**
     * 디바이스 목록 조회
     */
    @ResponseBody
    @RequestMapping(value = "/searchDeviceList")
    public Map<String, Object> searchDeviceList(@RequestParam Map<String, Object> pMap) {
        return deviceService.searchDeviceList(pMap);
    }

    /**
     * 디바이스 등록
     */
    @ResponseBody
    @RequestMapping(value = "/createDevice")
    public int createDevice(Object rMap) {
        return deviceService.createDevice((Map<String, Object>)rMap);
    }

    /**
     * 디바이스 삭제
     */
    @ResponseBody
    @RequestMapping(value = "/removeDevice")
    public int removeDevice(@RequestParam Map<String, Object> pMap) {
        return deviceService.removeDevice(pMap);
    }

    /**
     * 디바이스 수정
     */
    @ResponseBody
    @RequestMapping(value = "/modifyDevice")
    public int modifyDevice(Object rMap) {
        return deviceService.modifyDevice((Map<String, Object>)rMap);
    }
}
