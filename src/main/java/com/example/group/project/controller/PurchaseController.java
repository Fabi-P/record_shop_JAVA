package com.example.group.project.controller;

import com.example.group.project.service.impl.PurchaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class PurchaseController {

    @Autowired
    private PurchaseServiceImpl purchaseServiceImpl;

    // Displays the purchase form
    @GetMapping("/purchase")
    public String purchaseForm(@RequestParam Long recordId, Model model) {
        model.addAttribute("recordId", recordId);
        return "purchase";
    }

    // Handles form submission and completes the purchase
    @PostMapping("/purchases")
    public String completePurchase(@RequestParam Long recordId,
                                   @RequestParam String customerName,
                                   @RequestParam(required = false) String discount,
                                   Model model) {
        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", recordId);
        userPurchase.put("customer", customerName);
        if (discount != null && !discount.isEmpty()) {
            userPurchase.put("discount", discount);
        }

        try {
            // Check if record ID exists and is in stock
            if (!purchaseServiceImpl.checkIdExists(userPurchase)) {
                model.addAttribute("error", "Record not found.");
                return "purchase";
            }

            if (!purchaseServiceImpl.checkStock(userPurchase)) {
                model.addAttribute("error", "Record out of stock.");
                return "purchase";
            }

            // Commit the purchase and handle success
            Long purchaseId = purchaseServiceImpl.commitPurchase(userPurchase);
            model.addAttribute("message", "Purchase successful! Purchase ID: " + purchaseId);
            return "purchase_confirmation";

        } catch (Exception e) {
            log.error("Error processing purchase: {}", e.getMessage());
            model.addAttribute("error", "An error occurred while processing the purchase.");
            return "purchase";
        }
    }
}
