package com.yupi.yuojbackenduserservice.controller.inner;


import com.yupi.yuojbackenduserservice.service.UserService;
import com.yupi.yuojbackendmodel.model.entity.User;
import com.yupi.yuojbackendserviceclient.service.UserFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;


/**
 * 该服务仅内部调用, 不提供给前端
 */
@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {


    @Resource
    private UserService userService;


    @Override
    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") long userId) {
        return userService.getById(userId);
    }

    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList) {
        return userService.listByIds(idList);
    }
}
