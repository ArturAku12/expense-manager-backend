package com.example.expense_manager_riverty.controller;

import com.example.expense_manager_riverty.entity.Expense;
import com.example.expense_manager_riverty.entity.ExpenseCategory;
import com.example.expense_manager_riverty.entity.entityDTO.ExpenseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ExpenseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addExpense_andRetrieveById() throws Exception {
        Expense expense = new Expense();
        expense.setAmount(new BigDecimal("20.00"));
        expense.setDescription("Dinner");
        expense.setDate(new Date());
        expense.setCategory(ExpenseCategory.FOOD);

        String json = objectMapper.writeValueAsString(expense);

        MvcResult result = mockMvc.perform(post("/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        ExpenseDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), ExpenseDTO.class);

        mockMvc.perform(get("/expense/" + dto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Dinner")))
                .andExpect(jsonPath("$.amount", is(20.0)));
    }
}
