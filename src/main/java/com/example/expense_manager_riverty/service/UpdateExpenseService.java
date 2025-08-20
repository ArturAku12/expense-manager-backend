package com.example.expense_manager_riverty.service;

import com.example.expense_manager_riverty.entity.Expense;
import com.example.expense_manager_riverty.entity.entityDTO.ExpenseDTO;
import com.example.expense_manager_riverty.exception.ExpenseNotFoundException;
import com.example.expense_manager_riverty.repository.ExpenseRepository;
import com.example.expense_manager_riverty.service.interfaces.Command;
import com.example.expense_manager_riverty.service.inputs.UpdateExpenseCommand;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateExpenseService implements Command<UpdateExpenseCommand, ExpenseDTO> {

    private final ExpenseRepository expenseRepository;

    public UpdateExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<ExpenseDTO> execute(UpdateExpenseCommand command) {
        Optional<Expense> expenseOptional = expenseRepository.findById(command.getId());

        if (expenseOptional.isPresent()) {
            Expense expense = command.getExpense();
            expense.setId(command.getId());
            expenseRepository.save(expense);
            return ResponseEntity.ok().body(new ExpenseDTO(expense));
        }

        throw new ExpenseNotFoundException(command.getId());
    }
}
