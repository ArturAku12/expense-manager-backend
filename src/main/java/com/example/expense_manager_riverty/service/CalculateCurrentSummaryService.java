package com.example.expense_manager_riverty.service;

import com.example.expense_manager_riverty.entity.Expense;
import com.example.expense_manager_riverty.entity.ExpenseCategory;
import com.example.expense_manager_riverty.entity.entityDTO.CategoryBreakdownDTO;
import com.example.expense_manager_riverty.entity.entityDTO.MonthlySummaryResponse;
import com.example.expense_manager_riverty.repository.ExpenseRepository;
import com.example.expense_manager_riverty.service.interfaces.Calculate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalculateCurrentSummaryService implements Calculate<Double, ResponseEntity<MonthlySummaryResponse>> {

    private final ExpenseRepository expenseRepository;

    public CalculateCurrentSummaryService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public ResponseEntity<MonthlySummaryResponse> calculate(Double budget) {
        // Get the current month for response
        YearMonth currentMonth = YearMonth.now();

        // Get all expenses for the current month
        List<Expense> monthlyExpenses = expenseRepository.findExpensesForCurrentMonth();

        // Calculate total expense amount
        double totalExpense = monthlyExpenses.stream()
                .mapToDouble(expense -> expense.getAmount().doubleValue())
                .sum();

        // Calculate budget percentage used
        double budgetPercentageUsed = budget > 0 ? (totalExpense / budget) * 100 : 0;

        // Group expenses by category and calculate
        Map<ExpenseCategory, Double> categoryTotals = monthlyExpenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(expense -> expense.getAmount().doubleValue())
                ));

        // Calculate breakdowns for each category
        List<CategoryBreakdownDTO> categoryBreakdowns = new ArrayList<>();
        for (ExpenseCategory category : ExpenseCategory.values()) {
            double categoryAmount = categoryTotals.getOrDefault(category, 0.0);
            double percentageOfTotal = totalExpense > 0 ? (categoryAmount / totalExpense) * 100 : 0;
            double percentageOfBudget = budget > 0 ? (categoryAmount / budget) * 100 : 0;

            categoryBreakdowns.add(new CategoryBreakdownDTO(
                    category,
                    categoryAmount,
                    percentageOfTotal,
                    percentageOfBudget
            ));
        }

        MonthlySummaryResponse response = new MonthlySummaryResponse(
                totalExpense,
                budget,
                budgetPercentageUsed,
                currentMonth,
                categoryBreakdowns
        );

        return ResponseEntity.ok(response);
    }
}
