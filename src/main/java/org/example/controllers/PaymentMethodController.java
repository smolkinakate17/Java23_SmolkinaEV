package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.model.entities.PaymentMethod;
import org.example.model.statistic.manager.PaymentMethodManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/adminpanel/paymentmethods")
public class PaymentMethodController {
    private final PaymentMethodManager paymentMethodManager;

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("methods",paymentMethodManager.findAll());
        return "/adminpanel/paymentmethods/index";
    }
    @GetMapping("create")
    public String getCreateForm(Model model){
        model.addAttribute("method",new PaymentMethod());
        return "/adminpanel/paymentmethods/create";
    }
    @PostMapping("create")
    public String postCreate(@ModelAttribute("method") PaymentMethod method){
        paymentMethodManager.add(method);
        return "redirect:/adminpanel/paymentmethods";
    }
    @GetMapping("edit")
    public String getEditForm(@RequestParam Long id, Model model){
        model.addAttribute("method",paymentMethodManager.find(id).get());
        return "/adminpanel/paymentmethods/edit";
    }
    @PostMapping("edit")
    public String postEdit(@ModelAttribute("method") PaymentMethod method,@RequestParam Long id){
        method.setId(id);
        paymentMethodManager.update(method);
        return "redirect:/adminpanel/paymentmethods";
    }
    @GetMapping("delete")
    public String delete(@RequestParam Long id){
        paymentMethodManager.delete(id);
        return "redirect:/adminpanel/paymentmethods";
    }
}
