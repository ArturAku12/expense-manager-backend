package com.example.expense_manager_riverty.service;

import com.example.expense_manager_riverty.entity.Expense;
import com.example.expense_manager_riverty.entity.entityDTO.ExpenseDTO;
import com.example.expense_manager_riverty.repository.ExpenseRepository;
import com.example.expense_manager_riverty.service.interfaces.Command;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddExpenseService implements Command<Expense, ExpenseDTO> {
    private final ExpenseRepository expenseRepository;

    public AddExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    @Override
    @Transactional
    public ResponseEntity<ExpenseDTO> execute(Expense inputExpense) {
        Expense savedExpense = expenseRepository.save(inputExpense);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ExpenseDTO(savedExpense));
    }
}
