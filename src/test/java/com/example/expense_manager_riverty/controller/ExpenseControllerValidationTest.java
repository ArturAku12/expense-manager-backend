package com.example.expense_manager_riverty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addExpense_InvalidInput_ShouldReturnBadRequestAndErrors() throws Exception {
        String futureDate = LocalDate.now().plusDays(1).toString();
        String expenseJson = """
            {
                \"amount\": -10,
                \"description\": \"Lunch\",
                \"date\": \"%s\",
                \"category\": \"FOOD\"
            }
        """.formatted(futureDate);

        mockMvc.perform(post("/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expenseJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.amount").value("Expense amount must be at least 0.01"))
                .andExpect(jsonPath("$.fieldErrors.date").value("Expense date cannot be in the future"));
    }
}