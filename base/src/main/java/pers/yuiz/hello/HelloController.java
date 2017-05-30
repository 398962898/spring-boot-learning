package pers.yuiz.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yuiz.common.util.ResultUtil;
import pers.yuiz.common.vo.Result;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
public class HelloController {

    /**
     * 测试Web接口
     *
     * @param name
     * @return
     */
    @GetMapping("/hi")
    public String sayHi(String name) {
        return "你好," + name;
    }

    @GetMapping("getSession")
    public Result getSession(HttpSession session) {
        Object msg = session.getAttribute("msg");
        return ResultUtil.success(msg + ":" + session.getId());
    }

    @GetMapping("setSession")
    public Result setSession(String msg, HttpSession session) {
        msg = msg + new Date();
        System.out.println(msg);
        session.setAttribute("msg", msg);
        return ResultUtil.success(msg + ":" + session.getId());
    }

}
