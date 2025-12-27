package com.payment.online.paymentsystem.service;

import com.payment.online.paymentsystem.entity.Transaction;
import com.payment.online.paymentsystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByUserId(Long userId) {
        // ✅ Call the correct repository method
        return transactionRepository.findByUser_Id(userId);
    }
}