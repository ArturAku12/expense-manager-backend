package com.example.expense_manager_riverty.service;

import com.example.expense_manager_riverty.entity.Expense;
import com.example.expense_manager_riverty.entity.ExpenseCategory;
import com.example.expense_manager_riverty.entity.entityDTO.ExpenseDTO;
import com.example.expense_manager_riverty.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AddExpenseServiceTest {

    @Autowired
    private AddExpenseService addExpenseService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() {
        expenseRepository.deleteAll();
    }

    @Test
    void execute_savesExpenseAndReturnsDto() {
        Expense expense = new Expense();
        expense.setAmount(new BigDecimal("10.50"));
        expense.setDescription("Lunch");
        expense.setDate(new Date());
        expense.setCategory(ExpenseCategory.FOOD);

        ResponseEntity<ExpenseDTO> response = addExpenseService.execute(expense);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ExpenseDTO body = response.getBody();
        assertNotNull(body);
        assertNotNull(body.getId());
        assertEquals(expense.getAmount(), body.getAmount());
        assertEquals(1, expenseRepository.count());
    }
}
