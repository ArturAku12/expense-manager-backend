package com.example.expense_manager_riverty.service;

import com.example.expense_manager_riverty.entity.Expense;
import com.example.expense_manager_riverty.entity.ExpenseCategory;
import com.example.expense_manager_riverty.entity.entityDTO.ExpenseDTO;
import com.example.expense_manager_riverty.exception.ExpenseNotFoundException;
import com.example.expense_manager_riverty.repository.ExpenseRepository;
import com.example.expense_manager_riverty.service.inputs.UpdateExpenseCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    private UpdateExpenseService updateExpenseService;

    @BeforeEach
    void setUp() {
        updateExpenseService = new UpdateExpenseService(expenseRepository);
    }

    @Test
    void execute_updatesExistingExpenseAndReturnsDto() {
        Integer id = 1;
        Expense existing = new Expense();
        when(expenseRepository.findById(id)).thenReturn(Optional.of(existing));

        Expense updated = new Expense();
        updated.setAmount(new BigDecimal("20.00"));
        updated.setDescription("Coffee");
        updated.setDate(new Date());
        updated.setCategory(ExpenseCategory.FOOD);

        UpdateExpenseCommand command = new UpdateExpenseCommand(updated, id);

        ResponseEntity<ExpenseDTO> response = updateExpenseService.execute(command);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ExpenseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(id, body.getId());
        assertEquals(updated.getAmount(), body.getAmount());
        assertEquals(updated.getDescription(), body.getDescription());
        assertEquals(updated.getDate(), body.getDate());
        assertEquals(updated.getCategory(), body.getCategory());

        ArgumentCaptor<Expense> captor = ArgumentCaptor.forClass(Expense.class);
        verify(expenseRepository, times(1)).save(captor.capture());
        Expense saved = captor.getValue();
        assertEquals(id, saved.getId());
        assertEquals(updated.getAmount(), saved.getAmount());
        assertEquals(updated.getDescription(), saved.getDescription());
        assertEquals(updated.getDate(), saved.getDate());
        assertEquals(updated.getCategory(), saved.getCategory());
    }

    @Test
    void execute_throwsWhenExpenseNotFound() {
        Integer id = 1;
        Expense updated = new Expense();
        updated.setAmount(new BigDecimal("20.00"));
        updated.setDescription("Coffee");
        updated.setDate(new Date());
        updated.setCategory(ExpenseCategory.FOOD);

        when(expenseRepository.findById(id)).thenReturn(Optional.empty());

        UpdateExpenseCommand command = new UpdateExpenseCommand(updated, id);

        assertThrows(ExpenseNotFoundException.class, () -> updateExpenseService.execute(command));
        verify(expenseRepository, never()).save(any());
    }
}
