package com.payment.online.paymentsystem.controller;

import com.payment.online.paymentsystem.entity.User;
import com.payment.online.paymentsystem.entity.Transaction;
import com.payment.online.paymentsystem.repository.UserRepository;
import com.payment.online.paymentsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TransactionService transactionService;  // ✅ Add this

    // ========== PAGES ==========
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("USER");
        if (user == null) {
            return "redirect:/api/user/login";
        }
        model.addAttribute("user", user);
        return "dashboard";
    }

    // ✅ PAYMENT PAGE
    @GetMapping("/payment")
    public String paymentPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("USER");
        if (user == null) {
            return "redirect:/api/user/login";
        }
        model.addAttribute("user", user);
        return "payment";
    }

    // ✅ PAYMENT PROCESSING - TRANSACTION SAVE KARO
    @PostMapping("/payments/make")
    public String makePayment(
            HttpSession session,
            @RequestParam double amount,
            @RequestParam String type,
            @RequestParam String description) {

        User user = (User) session.getAttribute("USER");
        if (user == null) {
            return "redirect:/api/user/login";
        }

        System.out.println("💰 PAYMENT SAVING TO DATABASE...");
        System.out.println("User ID: " + user.getId());
        System.out.println("Amount: ₹" + amount);
        System.out.println("Type: " + type);
        System.out.println("Description: " + description);

        // ✅ Create and save transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setDescription(description);
        transaction.setUser(user);  // ✅ User object set karo

        transactionService.saveTransaction(transaction);

        System.out.println("✅ Transaction saved with ID: " + transaction.getId());

        return "redirect:/api/user/payment?success=true";
    }

    // ✅ TRANSACTIONS PAGE - REAL DATA
    @GetMapping("/transactions")
    public String transactionsPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("USER");
        if (user == null) {
            return "redirect:/api/user/login";
        }

        // ✅ REAL DATA from database
        List<Transaction> transactions = transactionService.getTransactionsByUserId(user.getId());

        System.out.println("📊 Found " + (transactions != null ? transactions.size() : 0) +
                " transactions for user ID: " + user.getId());

        // Calculate totals from REAL transactions
        double totalCredit = 0;
        double totalDebit = 0;

        if (transactions != null && !transactions.isEmpty()) {
            for (Transaction t : transactions) {
                if ("CREDIT".equals(t.getType())) {
                    totalCredit += t.getAmount();
                } else if ("DEBIT".equals(t.getType())) {
                    totalDebit += t.getAmount();
                }
            }
        }

        double balance = totalCredit - totalDebit;

        // Add ALL required variables to model
        model.addAttribute("transactions", transactions);
        model.addAttribute("balance", String.format("%.2f", balance));
        model.addAttribute("totalCredit", String.format("%.2f", totalCredit));
        model.addAttribute("totalDebit", String.format("%.2f", totalDebit));
        model.addAttribute("user", user);

        return "transactions";
    }

    // ========== AUTH ACTIONS ==========
    @PostMapping("/register")
    public String registerAction(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) {

        System.out.println("📝 REGISTER: " + email);

        // Check duplicate email
        if (userRepository.findByEmail(email) != null) {
            return "redirect:/api/user/register?error=duplicate";
        }

        // Create user
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        // Save
        userRepository.save(user);
        System.out.println("✅ User saved: " + user.getId());

        return "redirect:/api/user/login?success=registered";
    }

    @PostMapping("/do-login")
    public String loginAction(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        System.out.println("🔑 LOGIN ATTEMPT: " + email);

        // Find user
        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("❌ User not found");
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }

        // Check password
        boolean passwordOk = passwordEncoder.matches(password, user.getPassword());
        if (!passwordOk) {
            System.out.println("❌ Wrong password");
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }

        // Login successful
        session.setAttribute("USER", user);
        System.out.println("✅ LOGIN SUCCESS: " + user.getName() + " (ID: " + user.getId() + ")");

        return "redirect:/api/user/dashboard";
    }

    @GetMapping("/logout")
    public String logoutAction(HttpSession session) {
        session.removeAttribute("USER");
        session.invalidate();
        return "redirect:/api/user/login?logout=true";
    }

    // ========== TEST ==========
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "UserController OK - " + new java.util.Date();
    }
}