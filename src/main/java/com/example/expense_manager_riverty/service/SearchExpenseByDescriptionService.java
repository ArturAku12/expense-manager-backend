package com.example.expense_manager_riverty.service;

import com.example.expense_manager_riverty.entity.Expense;
import com.example.expense_manager_riverty.entity.entityDTO.ExpenseDTO;
import com.example.expense_manager_riverty.repository.ExpenseRepository;
import com.example.expense_manager_riverty.service.inputs.SearchExpenseByDescriptionInput;
import com.example.expense_manager_riverty.service.interfaces.Query;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SearchExpenseByDescriptionService implements Query<SearchExpenseByDescriptionInput, Page<ExpenseDTO>> {
    private final ExpenseRepository expenseRepository;

    public SearchExpenseByDescriptionService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    @Override
    public ResponseEntity<Page<ExpenseDTO>> execute(SearchExpenseByDescriptionInput input) {
        Page<Expense> expensePage = expenseRepository.findByDescriptionContainingIgnoreCase(
                input.getDescription(),
                input.getPageable()
        );
        return ResponseEntity.ok(expensePage.map(ExpenseDTO::new));
    }
}
