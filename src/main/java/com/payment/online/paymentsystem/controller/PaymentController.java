package com.payment.online.paymentsystem.controller;

import com.payment.online.paymentsystem.entity.Transaction;
import com.payment.online.paymentsystem.entity.User;
import com.payment.online.paymentsystem.repository.TransactionRepository;
import com.payment.online.paymentsystem.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Show payment form
    @GetMapping("/make")
    public String showPaymentPage(HttpSession session) {
        User user = (User) session.getAttribute("USER");
        if (user == null) return "redirect:/api/user/login";
        return "payment";
    }

    // Process payment
    @PostMapping("/make")
    public String makePayment(@RequestParam("amount") Double amount,
                              @RequestParam("type") String type,
                              @RequestParam("description") String description,
                              HttpSession session) {

        User sessionUser = (User) session.getAttribute("USER");
        if (sessionUser == null) return "redirect:/api/user/login";

        // Get fresh user from database
        User user = userRepository.findById(sessionUser.getId()).orElse(null);
        if (user == null) return "redirect:/api/user/login";

        System.out.println("💳 Payment by " + user.getEmail());
        System.out.println("Amount: ₹" + amount + ", Type: " + type);

        try {
            // Create transaction
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setType(type);
            transaction.setDescription(description);
            transaction.setUser(user);

            // Save
            Transaction saved = transactionRepository.save(transaction);
            System.out.println("✅ Saved! Transaction ID: " + saved.getId());

            return "redirect:/api/payments/make?success=true";

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            return "redirect:/api/payments/make?error=true";
        }
    }
}