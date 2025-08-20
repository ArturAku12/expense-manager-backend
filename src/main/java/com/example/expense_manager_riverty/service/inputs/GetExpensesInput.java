package com.example.expense_manager_riverty.service.inputs;

import com.example.expense_manager_riverty.entity.ExpenseCategory;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.Date;

@Data
@AllArgsConstructor
public class GetExpensesInput {

    private Pageable pageable;

    private ExpenseCategory category;

    @PastOrPresent(message = "Start date cannot be in the future")
    private Date startDate;

    @PastOrPresent(message = "End date cannot be in the future")
    private Date endDate;

    @AssertTrue(message = "End date must be after start date")
    public boolean isDateRangeValid() {
        return startDate == null || endDate == null || !endDate.before(startDate);
    }
}
