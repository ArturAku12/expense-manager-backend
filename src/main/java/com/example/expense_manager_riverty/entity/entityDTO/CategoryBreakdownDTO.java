package com.example.expense_manager_riverty.entity.entityDTO;

import com.example.expense_manager_riverty.entity.ExpenseCategory;
import lombok.Data;


@Data

public class CategoryBreakdownDTO {
    private ExpenseCategory category;
    private Double amount;
    private Double percentageOfTotal;
    private Double percentageOfBudget;

    public CategoryBreakdownDTO(ExpenseCategory category, Double amount, Double percentageOfTotal, Double percentageOfBudget) {
        this.category = category;
        this.amount = amount;
        this.percentageOfTotal = percentageOfTotal;
        this.percentageOfBudget = percentageOfBudget;
    }
}
