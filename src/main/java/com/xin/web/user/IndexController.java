package com.xin.web.user;

import com.xin.service.BlogsService;
import com.xin.service.TagService;
import com.xin.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogsService blogsService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",blogsService.listBlog(pageable));    //拿到分页的数据
        model.addAttribute("types",typeService.listTypeTop(5)); //获取几个分类
        model.addAttribute("tags",tagService.listTagTop(5));
        model.addAttribute("recommendBlogs",blogsService.listRecommendBlogTop(8));
        return "/index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                     Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogsService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "/search";
    }
    @GetMapping("/search/{query}")
    public String searchs(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                 Pageable pageable,
                         @PathVariable String query, Model model){
        model.addAttribute("page",blogsService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "/search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogsService.getAndConvert(id));
        return "/blog";
    }

    @GetMapping("/footer/newblog")
    public String newBlog(Model model){
        model.addAttribute("newblogs",blogsService.listRecommendBlogTop(3));
        return "_fragments :: newBlogList";
    }
}
