package com.payment.online.paymentsystem.repository;

import com.payment.online.paymentsystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // ✅ Change to findByUser_Id (Spring Data JPA will understand)
    List<Transaction> findByUser_Id(Long userId);

    // OR use @Query (choose one)
    // @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId ORDER BY t.timestamp DESC")
    // List<Transaction> findByUserId(@Param("userId") Long userId);
}