package com.xin.web.user;

import com.xin.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchiveShowController {
    @Autowired
    private BlogsService blogsService;

    @GetMapping("/archives")
    public String archive(Model model) {
        model.addAttribute("archives",blogsService.archiveBlog());
        model.addAttribute("blogCount",blogsService.countBlog());
        return "/archives";
    }
}
