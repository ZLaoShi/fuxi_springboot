package org.mxwj.controller;

import lombok.extern.slf4j.Slf4j;
import org.mxwj.entity.User;
import org.mxwj.service.UserService;
import org.mxwj.utils.VerifyCodeUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin //允许跨域
@RequestMapping("user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    //处理用户登录
    @PostMapping("login")
    public Map<String, Object> login(@RequestBody User user) {
        log.info("当前登录用户的信息：[{}]", user.toString());
        Map<String, Object> map = new HashMap<>();
        try {
            User UserDB = userService.login(user);
            map.put("state", true);
            map.put("msg", "登录成功");
            map.put("user", UserDB);
        } catch (Exception e){
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", e.getMessage());
        }

        return map;
    }

    //处理用户注册
    @PostMapping("register")
    public Map<String,Object> register(@RequestBody User user,String code, HttpServletRequest request) {
        log.info("用户信息:[{}]", user.toString());
        log.info("用户输入的验证码信息:[{}]", code);
        Map<String, Object> map = new HashMap<>();
        //调用业务方法
        try {
            String key = (String) request.getServletContext().getAttribute("code");
            if(key.equalsIgnoreCase(code)){
                userService.register(user);
                map.put("state", true);
                map.put("msg", "提示：注册成功");
            } else {
                throw new RuntimeException("验证码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", "提示：" + e.getMessage());
        }

        return map;
    }


    //生成验证码图片
    @GetMapping("getImage")
    public String getImageCode(HttpServletRequest request) throws IOException {
        //使用工具类生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //将验证码放入servletContext作用域
        request.getServletContext().setAttribute("code", code);
        //将图片转为base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//内存中输出流
        VerifyCodeUtils.outputImage(120, 30, byteArrayOutputStream, code);
        return "data:image/jpeg;base64," + Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
    }
}
