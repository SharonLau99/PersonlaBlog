package org.sharon.demo.controller.admin;

import org.sharon.demo.bean.Tag;
import org.sharon.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 管理页面中对tag进行管理的controller
 * @author sharon
 * @create 2020-08-31-22:03
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    TagService tagService;

    //分页展示分类
    @GetMapping("/tags")
    public String list(@RequestParam(value="pageNo",defaultValue="1")int pageNo,
//                       @RequestParam(value="pageSize",defaultValue="10")int pageSize,
                       @RequestParam(value = "orderBy",defaultValue = "id asc")String orderBy,
                       Model model) {
        //排序分页，pagesize默认为10，排序默认为id 升序
        model.addAttribute("page", tagService.getTagByPage(pageNo, 10, orderBy));
        //System.out.println(tagService.getTagByPage(pageNo, pageSize, orderBy));
        return "admin/tags";
    }

    //跳到新增页面
    @GetMapping("/tag/input")
    public String input() {
        return "admin/tag-input";
    }

    //新增分类处理
    @PostMapping("/tag")
    public String add(Tag tag, RedirectAttributes attributes) {
        Tag t = tagService.insertTag(tag);
        if (t == null) {
            //由于重复标签或其他原因导致的错误（目前基本只可能是标签重复），但写死了，不好维护，后续优化
            attributes.addFlashAttribute("message", "操作失败，标签不能重复。");
            return "redirect:/admin/tag/input";
        } else {
            attributes.addFlashAttribute("message", "操作成功");
            return "redirect:/admin/tags";
        }
    }

    //删除标签
    @DeleteMapping("/tag/{id}")
    public String delete(@PathVariable(value = "id")Long tagId, RedirectAttributes attributes) {
        //获取id，并删除
        tagService.deleteTag(tagId);
        attributes.addFlashAttribute("message", "操作成功");
        return "redirect:/admin/tags";
    }

    //更新
    @PutMapping("/tag")
    public String update(@RequestParam(value = "id")Long id, Tag tag, RedirectAttributes attributes, Model model) {
        if (tag == null) {
            //应该自定义异常类，处理异常
            return null;
        }
        //根据name获取tag
        Tag t = tagService.getTagByName(tag.getName());
        if (t == null || t.getId().equals(id)) {
            //新的标签或者没有更改，可以直接更改并保存
            tag.setId(id);
            tagService.updateTag(tag);
            attributes.addFlashAttribute("message", "操作成功");
            return "redirect:/admin/tags";
        } else {
            //重复提交
            model.addAttribute("message", "操作失败，重复标签。");
            return "admin/tag-input";
        }
    }

    //跳转更新页面并表单回显
    @GetMapping("/tag/edit")
    public String editPage(@RequestParam(value="id") Long id, Model model) {
        Tag tagById = tagService.getTagById(id);
        if (tagById != null) {
            model.addAttribute("tag", tagById);
        } else {
            model.addAttribute("message", "id不能为空");
        }
        return "admin/tag-input";
    }
}
