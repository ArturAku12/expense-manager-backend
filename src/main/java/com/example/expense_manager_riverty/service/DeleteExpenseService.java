package com.example.expense_manager_riverty.service;

import com.example.expense_manager_riverty.exception.ExpenseNotFoundException;
import com.example.expense_manager_riverty.repository.ExpenseRepository;
import com.example.expense_manager_riverty.service.interfaces.Command;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeleteExpenseService implements Command<Integer, String> {
    private final ExpenseRepository expenseRepository;

    public DeleteExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<String> execute(Integer id) {
        if (!expenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException(id);
        }
        expenseRepository.deleteById(id);
        return ResponseEntity.ok("Expense deleted successfully");
    }
}
