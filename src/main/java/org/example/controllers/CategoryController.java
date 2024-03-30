package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.model.entities.Category;
import org.example.model.statistic.manager.CategoryManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/adminpanel/categories")
public class CategoryController {
    private final CategoryManager categoryManager;

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("categories",categoryManager.findAll());
        return "/adminpanel/categories/index";
    }
    @GetMapping("create")
    public String getCreateForm(Model model){
        model.addAttribute("category",new Category());
        return "/adminpanel/categories/create";
    }
    @PostMapping("create")
    public String postCreate(@ModelAttribute("category") Category category){
        categoryManager.add(category);
        return "redirect:/adminpanel/categories";
    }
    @GetMapping("edit")
    public String getEditForm(@RequestParam Long id, Model model){
        model.addAttribute("category",categoryManager.find(id).get());
        return "/adminpanel/categories/edit";
    }
    @PostMapping("edit")
    public String postEdit(@ModelAttribute("category") Category category,@RequestParam Long id){
        category.setId(id);
        categoryManager.update(category);
        return "redirect:/adminpanel/categories";
    }
    @GetMapping("delete")
    public String delete(@RequestParam Long id){
        categoryManager.delete(id);
        return "redirect:/adminpanel/categories";
    }
}
