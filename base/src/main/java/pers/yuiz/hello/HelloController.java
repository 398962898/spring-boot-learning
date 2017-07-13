package pers.yuiz.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yuiz.common.util.ResultUtil;
import pers.yuiz.common.vo.Result;
import pers.yuiz.customer.entity.Role;
import pers.yuiz.customer.entity.User;
import pers.yuiz.customer.vo.LoginInfo;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @GetMapping("/getSession")
    public Result getSession(HttpSession session) {
        Object msg = session.getAttribute("msg");
        return ResultUtil.success(msg + ":" + session.getId());
    }

    @GetMapping("/setSession")
    public Result setSession(String msg, HttpSession session) {
        msg = msg + new Date();
        System.out.println(msg);
        session.setAttribute("msg", msg);
        return ResultUtil.success(msg + ":" + session.getId());
    }

    @GetMapping("/testBeanUtil")
    public Result testBeanUtil(String username) {
        User user = new User();
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        user.setUsername(username);
        return ResultUtil.success(user);
    }

    @GetMapping("/testBeanUtil2")
    public Result testBeanUtil2(String username, Byte sex) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername(username);
        loginInfo.setHeadImg("headImg");
        loginInfo.setSex(sex);
        List list = new ArrayList();
        Role role1 = new Role();
        role1.setRoleName("role1");
        role1.setId(1L);
        Role role2 = new Role();
        role2.setRoleName("role2");
        role2.setId(2L);
        Role role3 = new Role();
        role3.setRoleName("role3");
        role3.setId(3L);
        list.add(role1);
        list.add(role2);
        list.add(role3);
        loginInfo.setRoleVoList(list);
        return ResultUtil.success(loginInfo);
    }
}
