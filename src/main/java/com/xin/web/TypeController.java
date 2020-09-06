package com.xin.web;

import com.xin.pojo.Type;
import com.xin.service.TypeService;
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
public class TypeController {
    @Autowired
    private TypeService typeService;

    /**
     * 实现分页查询
     * @param pageable 查询到的分页数据
     *                 分页大小，排序依据，排序顺序
     * @param model 传数据到页面上
     * @return 返回分页面
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                        Pageable pageable, Model model){
        //typeService.listType(pageable)   查询到的数据
        model.addAttribute("page",typeService.listType(pageable));
        return "/admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "/admin/types-input";
    }

    /**
     * 编辑
     * PathVariable保证请求域中域参数的id相同
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "/admin/types-input";
    }

    /**
     * 创建分类表单提交后的请求
     *  @Valid 实现校验
     * @param type  表单提交的信息
     * @param result 校验完成后返回的信息
     * @param redirectAttributes 重定向的保存发送信息
     * @return 分类页面
     */
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes){
        Type tp = typeService.getTypeName(type.getName());
        if (tp != null){
            result.rejectValue("name","nameError","有一个了，还想要！贪心不得！");
        }

        if (result.hasErrors()){        //是否是错误信息
            return "/admin/types-input";
        }
        //接收type对象的话，表单提交的数据会自动封装再对象里
        Type t= typeService.saveType(type);
        //添加成功失败进行消息的提示
        if (t == null){
           redirectAttributes.addFlashAttribute("message","增加操作失败，在操作一次呗？");
        } else {
            redirectAttributes.addFlashAttribute("message","嘿嘿，增加被你成功了！");
        }
        return "redirect:/admin/types";
    }

    /**
     * 编辑
     */
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,
                           @PathVariable Long id,
                           RedirectAttributes redirectAttributes){
        Type tp = typeService.getTypeName(type.getName());
        if (tp != null){
            result.rejectValue("name","nameError","有一个了，还想要！贪心不得！");
        }
        if (result.hasErrors()){        //是否是错误信息
            return "/admin/types-input";
        }
        //接收type对象的话，表单提交的数据会自动封装再对象里
        Type t= typeService.updateType(id,type);
        //添加成功失败进行消息的提示
        if (t == null){
            redirectAttributes.addFlashAttribute("message","更新操作失败，在操作一次呗？");
        } else {
            redirectAttributes.addFlashAttribute("message","嘿嘿，更新被你成功了！");
        }
        return "redirect:/admin/types";
    }
    /**
     * 删除
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","嘿嘿，删除被你成功了！");
        return "redirect:/admin/types";
    }

}
