package org.mxwj.service.impl;

import org.mxwj.dao.UserDAO;
import org.mxwj.entity.User;
import org.mxwj.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserDAO userDAO;

    @Override
    public void register(User user) {
        //根据输入的用户名判断用户是否存在
        User userDB = userDAO.findByUserName(user.getUsername());
        if(userDB == null){
            //生成用户状态
            user.setStatus("已激活");
            //设置用户的注册时间
            user.setRegisterTime(new Date());
            //调用DAO
            userDAO.save(user);
        }  else {
            throw new RuntimeException("用户名已经被注册");
        }

    }

    @Override
    public User login(User user) {
        //根据用户输入的用户名进行查询
        User userDB =  userDAO.findByUserName(user.getUsername());
        if(!ObjectUtils.isEmpty(userDB)){
            if(user.getPassword().equals(userDB.getPassword())){
                return userDB;
            } else {
                throw new RuntimeException("密码输入不正确");
            }
        } else {
            throw new RuntimeException("用户名输入错误");
        }
    }
}
