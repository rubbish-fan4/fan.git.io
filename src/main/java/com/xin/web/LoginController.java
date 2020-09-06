package com.xin.web;

import com.xin.pojo.User;
import com.xin.service.BlogsService;
import com.xin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private BlogsService blogsService;

    /**
     * 登入后台管理登入页面
     * @return 返回后台管理登入页面
     */
    @GetMapping
    public String loginPage(){
        return "/admin/login";
    }

    /**
     * 登入验证
     * @param username 在表单请求中回去用户名
     * @param password 在表单请求中获取密码
     * @param session 会话
     * @param attributes 存储错误信息，这里不能使用Moddle，因为重定向数据不共享
     * @return 返回后台管理首页
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes
                        ){
        User user = userService.checkUser(username, password);
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "/admin/index";
        } else {
            attributes.addFlashAttribute("messages","这不是你该来的地方");
            return "redirect:/admin";
        }
    }

    /**
     * 注销用户
     * @param session  会话中的用户信息
     * @return  重定向回登入界面
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        model.addAttribute("newblogs",blogsService.listRecommendBlogTop(3));
        return "/admin/_fragments :: newBlogList";
    }
}
