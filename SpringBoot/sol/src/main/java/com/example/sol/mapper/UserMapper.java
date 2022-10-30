package com.example.sol.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.sol.model.User;

@Mapper
public interface UserMapper {
    public void join(User user);

    public String getPw(String id);

    public User selectUser(String id);

    public void withdraw(String userId);

    public ArrayList<User> userList();
    
}
