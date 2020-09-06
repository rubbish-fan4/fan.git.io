package com.xin.web.user;

import com.xin.pojo.Tag;
import com.xin.pojo.Type;
import com.xin.service.BlogsService;
import com.xin.service.TagService;
import com.xin.service.TypeService;
import com.xin.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogsService blogsService;


    /**
     * 通过标签的id来查询博客并返回对应的页
     * @param pageable 页的设置
     * @param id
     * @param model 保存处理后的数据
     * @return 返回标签分类页
     */
    @GetMapping("/tag/{id}")
    public String tg(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogsService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "/tags";
    }
}
