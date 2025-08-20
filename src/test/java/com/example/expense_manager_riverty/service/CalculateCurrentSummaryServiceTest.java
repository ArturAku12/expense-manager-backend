package com.example.expense_manager_riverty.service;

import com.example.expense_manager_riverty.entity.Expense;
import com.example.expense_manager_riverty.entity.ExpenseCategory;
import com.example.expense_manager_riverty.entity.entityDTO.CategoryBreakdownDTO;
import com.example.expense_manager_riverty.entity.entityDTO.MonthlySummaryResponse;
import com.example.expense_manager_riverty.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CalculateCurrentSummaryServiceTest {

    @Autowired
    private CalculateCurrentSummaryService calculateCurrentSummaryService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() {
        expenseRepository.deleteAll();
    }

    @Test
    void calculate_returnsSummaryForCurrentMonth() {
        Expense e1 = new Expense();
        e1.setAmount(new BigDecimal("50"));
        e1.setDescription("Groceries");
        e1.setDate(new Date());
        e1.setCategory(ExpenseCategory.FOOD);

        Expense e2 = new Expense();
        e2.setAmount(new BigDecimal("30"));
        e2.setDescription("Bus ticket");
        e2.setDate(new Date());
        e2.setCategory(ExpenseCategory.TRANSPORT);

        expenseRepository.save(e1);
        expenseRepository.save(e2);

        ResponseEntity<MonthlySummaryResponse> response = calculateCurrentSummaryService.calculate(200.0);
        MonthlySummaryResponse summary = response.getBody();

        assertNotNull(summary);
        assertEquals(80.0, summary.getTotalExpense());
        assertEquals(200.0, summary.getBudgetAmount());
        assertEquals(40.0, summary.getBudgetPercentageUsed());

        CategoryBreakdownDTO foodBreakdown = summary.getCategoryBreakdownDTOList().stream()
                .filter(b -> b.getCategory() == ExpenseCategory.FOOD)
                .findFirst()
                .orElseThrow();
        assertEquals(50.0, foodBreakdown.getAmount());

        CategoryBreakdownDTO transportBreakdown = summary.getCategoryBreakdownDTOList().stream()
                .filter(b -> b.getCategory() == ExpenseCategory.TRANSPORT)
                .findFirst()
                .orElseThrow();
        assertEquals(30.0, transportBreakdown.getAmount());
    }
}
