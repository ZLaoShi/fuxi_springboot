package org.mxwj.dao;

import org.apache.ibatis.annotations.Mapper;
import org.mxwj.entity.User;

@Mapper
public interface UserDAO {
    void save(User user);
    User findByUserName(String username);
}
