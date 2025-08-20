package com.example.expense_manager_riverty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(Integer id) {
        super(ErrorMessages.EXPENSE_NOT_FOUND.getMessage() + ": " + id);
    }
}
