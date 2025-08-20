package com.example.expense_manager_riverty.entity.entityDTO;

import com.example.expense_manager_riverty.entity.Expense;
import com.example.expense_manager_riverty.entity.ExpenseCategory;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ExpenseDTO {

    private Integer id;

    private BigDecimal amount;

    private String description;

    private Date date;

    private ExpenseCategory category;

    private List<String> tags;

    public ExpenseDTO() {
    }

    public ExpenseDTO(Expense expense) {
        this.id = expense.getId();
        this.amount = expense.getAmount();
        this.description = expense.getDescription();
        this.category = expense.getCategory();
        this.date = expense.getDate();
        this.tags = expense.getTags();
    }
}
