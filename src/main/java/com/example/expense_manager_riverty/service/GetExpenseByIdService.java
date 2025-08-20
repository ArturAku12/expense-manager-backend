package com.example.expense_manager_riverty.service;

import com.example.expense_manager_riverty.entity.Expense;
import com.example.expense_manager_riverty.entity.entityDTO.ExpenseDTO;
import com.example.expense_manager_riverty.exception.ExpenseNotFoundException;
import com.example.expense_manager_riverty.repository.ExpenseRepository;
import com.example.expense_manager_riverty.service.interfaces.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetExpenseByIdService implements Query<Integer, ExpenseDTO> {

    private final ExpenseRepository expenseRepository;

    public GetExpenseByIdService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public ResponseEntity<ExpenseDTO> execute(Integer id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));
        return ResponseEntity.ok(new ExpenseDTO(expense));
    }
}
