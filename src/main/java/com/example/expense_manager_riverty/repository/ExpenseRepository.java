package com.example.expense_manager_riverty.repository;

import com.example.expense_manager_riverty.entity.Expense;
import com.example.expense_manager_riverty.entity.ExpenseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    Page<Expense> findByCategory(ExpenseCategory category, Pageable pageable);

    Page<Expense> findByDateBetween(Date startDate, Date endDate, Pageable pageable);

    Page<Expense> findByDateGreaterThanEqual(Date startDate, Pageable pageable);

    Page<Expense> findByDateLessThanEqual(Date endDate, Pageable pageable);

    Page<Expense> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    // Custom query to get expenses for current month (different format)
    @Query("SELECT e FROM Expense e WHERE MONTH(e.date) = MONTH(CURRENT_DATE) AND YEAR(e.date) = YEAR(CURRENT_DATE)")
    List<Expense> findExpensesForCurrentMonth();

    Page<Expense> findByCategoryAndDateBetween(ExpenseCategory category, Date startDate, Date endDate, Pageable pageable);

    Page<Expense> findByCategoryAndDateGreaterThanEqual(ExpenseCategory category, Date startDate, Pageable pageable);

    Page<Expense> findByCategoryAndDateLessThanEqual(ExpenseCategory category, Date endDate, Pageable pageable);
}
