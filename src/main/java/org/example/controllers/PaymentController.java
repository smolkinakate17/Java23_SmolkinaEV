package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.controllers.dtos.PaymentDTO;
import org.example.model.entities.Category;
import org.example.model.entities.Payment;
import org.example.model.entities.PaymentCategory;
import org.example.model.enums.PaymentSorting;
import org.example.model.statistic.manager.CategoryManager;
import org.example.model.statistic.manager.PaymentManager;
import org.example.model.statistic.manager.PaymentMethodManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/adminpanel/payments")
public class PaymentController {
    @Autowired
    private final PaymentManager paymentManager;

    private final CategoryManager categoryManager;
    private final PaymentMethodManager paymentMethodManager;

    @GetMapping
    public String getPaginal(Model model,
                             @RequestParam(required = false) String sorting,
                             @RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer pageSize,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        if (fromDate == null) {
            fromDate = LocalDate.of(2024, 1, 1);
        }
        if (toDate == null) {
            toDate = LocalDate.of(2024, 1, 31);
        }
        long paymentsCount = paymentManager.getPaymentListByDate(fromDate, toDate).size();
        long pagesCount = paymentsCount / pageSize + (paymentsCount % pageSize > 0 ? 1 : 0);
        if (page == null || page < 0) {
            page = 0;
        } else if (page > pagesCount) {
            page = (int) pagesCount;
        }
        PaymentSorting paymentSorting = (sorting == null)
                ? PaymentSorting.DATETIME
                : PaymentSorting.valueOf(sorting
                .toUpperCase()
                .replace("ASC", "")
                .replace("DESC", "")
        );
        if (sorting == null) {
            sorting = "desc";
        }
        boolean desc = sorting.toLowerCase().endsWith("desc");

        List<Payment> payments = paymentManager.getPaymentListByDateSortedPaginal(paymentSorting, desc, page, pageSize, fromDate, toDate);
        model.addAttribute("payments", payments);
        model.addAttribute("pagesCount", pagesCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("fromDate", fromDate.toString());
        model.addAttribute("toDate", toDate.toString());
        return "/adminpanel/payments/index";

    }

    @GetMapping("create")
    public String getCreateForm(Model model) {

        model.addAttribute("payment", new PaymentDTO());
        model.addAttribute("methods",paymentMethodManager.findAll());
        Map<Category,Boolean> categoryBooleanMap=new HashMap<>();
        List<Category> categoryList=categoryManager.findAll();
        for(Category c : categoryList){
            categoryBooleanMap.put(c,Boolean.FALSE);
        }

        model.addAttribute("categories",categoryBooleanMap);
        return "/adminpanel/payments/create";
    }

    @GetMapping("edit")
    public String getEditForm(@RequestParam Long id, Model model) {
        Payment payment = paymentManager.find(id).get();
        Map<Category,Boolean> categoryBooleanMap=new HashMap<>();
        List<Category> categoryList=categoryManager.findAll();
        for(Category c : categoryList){
            if(payment.getPaymentCategories().contains(new PaymentCategory(payment,c))){
                categoryBooleanMap.put(c,Boolean.TRUE);
            }
            else{
                categoryBooleanMap.put(c,Boolean.FALSE);
            }

        }
        model.addAttribute("methods",paymentMethodManager.findAll());
        model.addAttribute("categories",categoryBooleanMap);
        model.addAttribute("payment", mapPaymentDTO(payment));
        return "/adminpanel/payments/edit";
    }

    @PostMapping("create")
    public String postCreate(@ModelAttribute("payment") PaymentDTO dto) {
        paymentManager.add(mapPaymentDTO(dto));
        return "redirect:/adminpanel/payments";
    }
    @PostMapping("edit")
    public String postEdit(@ModelAttribute("payment") PaymentDTO dto,@RequestParam Long id) {
        dto.setId(id);
        paymentManager.update(mapPaymentDTO(dto));
        return "redirect:/adminpanel/payments";
    }

    @GetMapping("delete")
    public String delete(@RequestParam Long id){
        paymentManager.delete(id);
        return "redirect:/adminpanel/payments";
    }

    private Payment mapPaymentDTO(PaymentDTO dto) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setDatetime(dto.getDateTime());
        payment.setSupplier(dto.getSupplier());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(paymentMethodManager.find(dto.getMethod().getTitle()).get());
        for (Long c : dto.getCategoryList()) {
            payment.addCategory(categoryManager.find(c).get());
        }
        return payment;
    }

    private PaymentDTO mapPaymentDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setMethod(payment.getPaymentMethod());
        dto.setSupplier(payment.getSupplier());
        dto.setDateTime(payment.getDatetime());
        dto.setAmount(payment.getAmount());
        List<Long> dtolist=new ArrayList<>();
        List<Category> categoryList=categoryManager.findAll();
        for (Category c : categoryList) {
           if(payment.getPaymentCategories().contains(new PaymentCategory(payment,c))){
               dtolist.add(c.getId());
           }
           /*else{
               categoryBooleanMap.put(c,Boolean.FALSE);
           }*/
        }

        dto.setCategoryList(dtolist);
        return dto;
    }
}
