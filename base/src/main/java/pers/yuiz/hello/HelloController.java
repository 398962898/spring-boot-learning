package pers.yuiz.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yuiz.common.util.ResultUtil;
import pers.yuiz.common.vo.Result;
import pers.yuiz.customer.entity.User;
import pers.yuiz.customer.service.UserService;

import java.util.*;

@RestController
public class HelloController {

    @Autowired
    private UserService userService;

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

    /**
     * 测试Mybatis保存
     *
     * @param name
     * @return
     */
    @GetMapping("/user")
    public Result user(String name) {
        User user = new User();
        user.setName(name + UUID.randomUUID().toString().substring(0, 5));
        user.setPassword(name + UUID.randomUUID().toString().substring(0, 5));
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        userService.saveOne(user);
        List<User> list = userService.listAll();
        return ResultUtil.success(list);
    }

    /**
     * 测试Mysql数据库事务
     *
     * @return
     */
    @GetMapping("/tx")
    public Result tx() {
        userService.txTest();
        return ResultUtil.success();
    }

}
