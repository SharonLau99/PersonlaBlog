package org.sharon.demo.controller.admin;

import org.sharon.demo.bean.Type;
import org.sharon.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 管理员管理分类的controller
 * @author sharon
 * @create 2020-08-31-14:35
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    TypeService typeService;

    //分页展示分类
    @GetMapping("/types")
    public String list(@RequestParam(value="pageNo",defaultValue="1")int pageNo,
//                       @RequestParam(value="pageSize",defaultValue="10")int pageSize,
                       @RequestParam(value = "orderBy",defaultValue = "id asc")String orderBy,
                       Model model) {
        //排序分页，pagesize默认为10，排序默认为id 升序
        model.addAttribute("page", typeService.getTypeByPage(pageNo, 10, orderBy));
        //System.out.println(typeService.getTypeByPage(pageNo, pageSize, orderBy));
        return "admin/types";
    }

    //跳到新增页面
    @GetMapping("/type/input")
    public String input() {
        return "admin/type-input";
    }

    //新增分类处理
    @PostMapping("/type")
    public String add(Type type, RedirectAttributes attributes) {
        Type t = typeService.insertType(type);
        if (t == null) {
            //由于重复分类或其他原因导致的错误（目前基本只可能是分类重复），但写死了，不好维护，后续优化
            attributes.addFlashAttribute("message", "操作失败，分类不能重复。");
            return "redirect:/admin/type/input";
        } else {
            attributes.addFlashAttribute("message", "操作成功");
            return "redirect:/admin/types";
        }
    }

    //删除标签
    @DeleteMapping("/type/{id}")
    public String delete(@PathVariable(value = "id")Long typeId, RedirectAttributes attributes) {
        //删除
        typeService.deleteType(typeId);
        attributes.addFlashAttribute("message", "操作成功");
        return "redirect:/admin/types";
    }

    //更新
    @PutMapping("/type")
    public String put(@RequestParam(value = "id")Long id, Type type, RedirectAttributes attributes, Model model) {
        if (type == null) {
            //处理异常
            return null;
        }

        //根据name获取type
        Type t = typeService.getTypeByName(type.getName());
        if (t == null || t.getId().equals(id)) {
            //新的标签或者没有更改，可以直接更改并保存
            type.setId(id);
            typeService.updateType(type);
            attributes.addFlashAttribute("message", "操作成功");
            return "redirect:/admin/types";
        } else {
            //重复提交
            model.addAttribute("message", "操作失败，重复分类。");
            return "admin/type-input";
        }
    }

    //跳转更新页面并表单回显
    @GetMapping("type/edit")
    public String editPage(@RequestParam(value="id") String id, Model model) {
        Type typeById = typeService.getTypeById(Long.parseLong(id));
        if (typeById != null) {
            model.addAttribute("type", typeById);
        } else {
            model.addAttribute("message", "id不能为空");
        }
        return "admin/type-input";
    }
}
