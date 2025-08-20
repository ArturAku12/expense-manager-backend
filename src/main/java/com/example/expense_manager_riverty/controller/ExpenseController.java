package com.example.expense_manager_riverty.controller;

import com.example.expense_manager_riverty.entity.Expense;
import com.example.expense_manager_riverty.entity.entityDTO.ExpenseDTO;
import com.example.expense_manager_riverty.entity.ExpenseCategory;
import com.example.expense_manager_riverty.entity.entityDTO.MonthlySummaryResponse;
import com.example.expense_manager_riverty.service.*;
import com.example.expense_manager_riverty.service.inputs.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@Validated
public class ExpenseController {

    private final GetExpensesService getExpensesService;
    private final GetExpenseByIdService getExpenseByIdService;
    private final AddExpenseService addExpenseService;
    private final UpdateExpenseService updateExpenseService;
    private final DeleteExpenseService deleteExpenseService;
    private final SearchExpenseByDescriptionService searchExpenseByDescriptionService;
    private final CalculateCurrentSummaryService calculateCurrentSummaryService;

    public ExpenseController(GetExpensesService getExpensesService,
                             GetExpenseByIdService getExpenseByIdService,
                             AddExpenseService addExpenseService,
                             UpdateExpenseService updateExpenseService,
                             DeleteExpenseService deleteExpenseService,
                             SearchExpenseByDescriptionService searchExpenseByDescriptionService,
                             CalculateCurrentSummaryService calculateCurrentSummaryService) {
        this.getExpensesService = getExpensesService;
        this.getExpenseByIdService = getExpenseByIdService;
        this.addExpenseService = addExpenseService;
        this.updateExpenseService = updateExpenseService;
        this.deleteExpenseService = deleteExpenseService;
        this.searchExpenseByDescriptionService = searchExpenseByDescriptionService;
        this.calculateCurrentSummaryService = calculateCurrentSummaryService;
    }

    @GetMapping("/expenses")
    public ResponseEntity<Page<ExpenseDTO>> getExpenses(
            @PageableDefault(size = 15, sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) ExpenseCategory category,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") @PastOrPresent Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") @PastOrPresent Date endDate) {

        GetExpensesInput input = new GetExpensesInput(pageable, category, startDate, endDate);

        return getExpensesService.execute(input);
    }

    @GetMapping("/expense/{id}")
    public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable @Positive Integer id) {
        return getExpenseByIdService.execute(id);
    }

    @GetMapping("/expense/search")
    public ResponseEntity<Page<ExpenseDTO>> searchExpenseByDescription(@RequestParam String description, Pageable pageable) {
        return searchExpenseByDescriptionService.execute(new SearchExpenseByDescriptionInput(description, pageable));
    }

    @GetMapping("/expense/summary")
    public ResponseEntity<MonthlySummaryResponse> calculateCurrentSummary(@RequestParam @NotNull @Positive Double budget) {
        return calculateCurrentSummaryService.calculate(budget);
    }

    @PostMapping("/expense")
    @CacheEvict(value = "expenses", allEntries = true)
    public ResponseEntity<ExpenseDTO> addExpense(@RequestBody @Valid Expense inputExpense) {
        return addExpenseService.execute(inputExpense);
    }

    @PutMapping("/expense/{id}")
    @CacheEvict(value = "expenses", allEntries = true)
    public ResponseEntity<ExpenseDTO> updateExpense(@PathVariable @Positive Integer id, @RequestBody @Valid Expense inputExpense) {
        return updateExpenseService.execute(new UpdateExpenseCommand(inputExpense, id));
    }

    @DeleteMapping("/expense/{id}")
    @CacheEvict(value = "expenses", allEntries = true)
    public ResponseEntity<String> deleteExpense(@PathVariable @Positive Integer id) {
        return deleteExpenseService.execute(id);
    }
}
