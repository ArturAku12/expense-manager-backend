package com.example.expense_manager_riverty.service;

import com.example.expense_manager_riverty.entity.Expense;
import com.example.expense_manager_riverty.entity.ExpenseCategory;
import com.example.expense_manager_riverty.entity.entityDTO.ExpenseDTO;
import com.example.expense_manager_riverty.repository.ExpenseRepository;
import com.example.expense_manager_riverty.service.inputs.GetExpensesInput;
import com.example.expense_manager_riverty.service.interfaces.Query;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

@Service
@Validated
public class GetExpensesService implements Query<GetExpensesInput, Page<ExpenseDTO>> {

    private final ExpenseRepository expenseRepository;
    private final int defaultPageSize;

    public GetExpensesService(ExpenseRepository expenseRepository,
                              @Value("${spring.data.web.pageable.default-page-size:20}") int defaultPageSize) {
        this.expenseRepository = expenseRepository;
        this.defaultPageSize = defaultPageSize;
    }

    @Override
    public ResponseEntity<Page<ExpenseDTO>> execute(@Valid GetExpensesInput input) {
        Pageable pageable = input.getPageable();
        Sort sortByDateDesc = Sort.by(Sort.Direction.DESC, "date");
        if (pageable == null) {
            pageable = PageRequest.of(0, defaultPageSize, sortByDateDesc);
        } else if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortByDateDesc);
        }
        Page<Expense> expensePage = fetchExpensesBasedOnFilters(input, pageable);
        Page<ExpenseDTO> expenseDTOPage = expensePage.map(ExpenseDTO::new);
        return ResponseEntity.ok(expenseDTOPage);
    }

    private Page<Expense> fetchExpensesBasedOnFilters(GetExpensesInput input, Pageable pageable) {
        ExpenseCategory category = input.getCategory();
        Date startDate = input.getStartDate();
        Date endDate = input.getEndDate();

        if (category != null) {
            if (startDate != null && endDate != null) {
                return expenseRepository.findByCategoryAndDateBetween(category, startDate, endDate, pageable);
            }
            if (startDate != null) {
                return expenseRepository.findByCategoryAndDateGreaterThanEqual(category, startDate, pageable);
            }
            if (endDate != null) {
                return expenseRepository.findByCategoryAndDateLessThanEqual(category, endDate, pageable);
            }
            return expenseRepository.findByCategory(category, pageable);
        }

        if (startDate != null && endDate != null) {
            return expenseRepository.findByDateBetween(startDate, endDate, pageable);
        }
        if (startDate != null) {
            return expenseRepository.findByDateGreaterThanEqual(startDate, pageable);
        }
        if (endDate != null) {
            return expenseRepository.findByDateLessThanEqual(endDate, pageable);
        }

        return expenseRepository.findAll(pageable);
    }
}
