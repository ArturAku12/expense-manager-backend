package com.example.expense_manager_riverty.service.inputs;

import com.example.expense_manager_riverty.entity.Expense;
import lombok.Getter;

@Getter
public class UpdateExpenseCommand {
    private Integer id;
    private Expense expense;

    public UpdateExpenseCommand(Expense expense, Integer id) {
        this.expense = expense;
        this.id = id;
    }
}
