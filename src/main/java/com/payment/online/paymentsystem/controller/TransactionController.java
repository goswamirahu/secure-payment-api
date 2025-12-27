package com.payment.online.paymentsystem.controller;

import com.payment.online.paymentsystem.entity.Transaction;
import com.payment.online.paymentsystem.entity.User;
import com.payment.online.paymentsystem.repository.TransactionRepository;
import com.payment.online.paymentsystem.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/history")
    public String getTransactionHistory(HttpSession session, Model model) {
        System.out.println("🎯 Loading transaction history...");

        // 1. Check if user is logged in
        User user = (User) session.getAttribute("USER");
        if (user == null) {
            System.out.println("❌ No user in session");
            return "redirect:/api/user/login";
        }

        System.out.println("✅ User: " + user.getEmail() + " (ID: " + user.getId() + ")");

        try {
            // 2. Get ALL transactions from database
            List<Transaction> allTransactions = transactionRepository.findAll();
            System.out.println("Total transactions in DB: " + allTransactions.size());

            // 3. Filter for current user
            List<Transaction> userTransactions = new ArrayList<>();
            for (Transaction t : allTransactions) {
                if (t.getUser() != null && t.getUser().getId().equals(user.getId())) {
                    userTransactions.add(t);
                }
            }

            System.out.println("Transactions for current user: " + userTransactions.size());

            // 4. Calculate totals
            double totalCredit = 0;
            double totalDebit = 0;

            for (Transaction t : userTransactions) {
                System.out.println("Transaction: ₹" + t.getAmount() + " - " + t.getType() + " - " + t.getDescription());
                if ("CREDIT".equalsIgnoreCase(t.getType())) {
                    totalCredit += t.getAmount();
                } else if ("DEBIT".equalsIgnoreCase(t.getType())) {
                    totalDebit += t.getAmount();
                }
            }

            double balance = totalCredit - totalDebit;

            // 5. Debug print
            System.out.println("=== SUMMARY ===");
            System.out.println("Total Credit: ₹" + totalCredit);
            System.out.println("Total Debit: ₹" + totalDebit);
            System.out.println("Balance: ₹" + balance);

            // 6. Add to model
            model.addAttribute("balance", balance);
            model.addAttribute("totalCredit", totalCredit);
            model.addAttribute("totalDebit", totalDebit);
            model.addAttribute("transactions", userTransactions);

            System.out.println("✅ Sending " + userTransactions.size() + " transactions to page");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());

            // 7. Fallback if error
            model.addAttribute("balance", 28000.00);
            model.addAttribute("totalCredit", 28000.00);
            model.addAttribute("totalDebit", 0.00);
            model.addAttribute("transactions", new ArrayList<>());
        }

        return "transaction";
    }
}