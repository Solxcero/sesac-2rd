package com.example.sol.controller;

// import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sol.mapper.UserMapper;
import com.example.sol.model.User;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserMapper userMapper;
    
    // 회원가입
    @GetMapping("join")
    public String join(){
        return "user/join";
    }

    @PostMapping("join")
    public String join(HttpSession session, User user){
        userMapper.join(user);
        return "redirect:/";
    }

    // 아이디 중복 확인 로직 만들기 !!!!!    

    //로그인
    @GetMapping("login")
    public String login(){
        return "user/login";
    }
    @PostMapping("login")
    public String login(HttpSession session, User user){
        String id = user.getUserId();
        String pw = user.getUserPw();
        String getPw = userMapper.getPw(id);

        if(getPw != null){
            if(getPw.equals(pw)){
                User userDate = userMapper.selectUser(id);
                // System.out.println(userDate);
                session.setAttribute("user", userDate);
                return "redirect:/";
            }else{
                return "user/loginFail";
            }
        }else{
            session.setAttribute("user", null);
            return "user/loginFail";
        }
    }

    //내 페이지 이동
    @GetMapping("info")
    public String info(@RequestParam("userId")String userId, Model model, User user){
        User userData = userMapper.selectUser(userId);
        model.addAttribute("user", userData);       
        return "user/info";
    }

    // 로그아웃
    @GetMapping("logout")
    public String logout(HttpSession session, User user){
        session.removeAttribute("user");
        return "redirect:/";
    }

    //탈퇴
    @GetMapping("withdraw")
    public String withdraw(){
        return "/user/withdraw";
    }

    @PostMapping("withdraw")
    public String withdraw(@RequestParam("userId") String userId, User user, HttpSession session){
        User userData = (User)session.getAttribute("user");
        String pw = user.getUserPw();
        String id = user.getUserId();
        if(userData.getUserId().equals(id) && userData.getUserPw().equals(pw)){
            userMapper.withdraw(userId);
            session.setAttribute("user", null);
            return "redirect:/";
        } else {
            return "redirect:/user/withdraw";
        }
    }
}
