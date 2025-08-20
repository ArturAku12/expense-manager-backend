package com.example.expense_manager_riverty.entity.entityDTO;

import lombok.Data;

import java.time.YearMonth;
import java.util.List;

@Data
public class MonthlySummaryResponse {
    private Double totalExpense;
    private Double budgetAmount;
    private Double budgetPercentageUsed;
    private YearMonth month;
    private List<CategoryBreakdownDTO> categoryBreakdownDTOList;

    public MonthlySummaryResponse(Double totalExpense, Double budgetAmount, Double budgetPercentageUsed, YearMonth month, List<CategoryBreakdownDTO> categoryBreakdownDTOList) {
        this.totalExpense = totalExpense;
        this.budgetAmount = budgetAmount;
        this.budgetPercentageUsed = budgetPercentageUsed;
        this.month = month;
        this.categoryBreakdownDTOList = categoryBreakdownDTOList;
    }
}
