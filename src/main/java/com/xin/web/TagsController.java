package com.xin.web;


import com.xin.pojo.Tag;
import com.xin.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagsController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                       Model model){
        model.addAttribute("page",tagService.listPage(pageable));
        return "/admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "/admin/tags-input";
    }
    //编辑
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "/admin/tags-input";
    }
    //增加
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result,
                       RedirectAttributes redirectAttributes){

        Tag tagByName = tagService.getTagByName(tag.getName());
        if (tagByName != null){
            result.rejectValue("name","nameError","有一个了，还想要！贪心不得！");
        }

        if (result.hasErrors()){
            return "/admin/tags-input";
        }

        Tag t = tagService.saveTag(tag);
        if (t == null){
            redirectAttributes.addFlashAttribute("message","增加操作失败，在操作一次呗？");
        } else {
            redirectAttributes.addFlashAttribute("message","嘿嘿，增加被你成功了！");
        }
        return "redirect:/admin/tags";
    }


    @PostMapping("/tags/{id}")
    public String post(@Valid Tag tag, BindingResult result,
                       @PathVariable Long id,
                       RedirectAttributes redirectAttributes){

        Tag tagByName = tagService.getTagByName(tag.getName());
        if (tagByName != null){
            result.rejectValue("name","nameError","有一个了，还想要！贪心不得！");
        }

        if (result.hasErrors()){
            return "/admin/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if (t == null){
            redirectAttributes.addFlashAttribute("message","修改操作失败，在操作一次呗？");
        } else {
            redirectAttributes.addFlashAttribute("message","嘿嘿，修改被你成功了！");
        }
        redirectAttributes.addFlashAttribute("activeTypeId",id);
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message","嘿嘿，删除被你成功了！");
        return "redirect:/admin/tags";
    }
}
