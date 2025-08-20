package com.example.expense_manager_riverty.exception;


public enum ErrorMessages {
    EXPENSE_NOT_FOUND("Expense not found"),
    EXPENSE_NOT_VALID("Expense is not valid");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
