package com.example.expense_manager_riverty.service.inputs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class SearchExpenseByDescriptionInput {

    @NotBlank
    @Size(min = 1, max = 100)
    private final String description;
    private final Pageable pageable;

    public SearchExpenseByDescriptionInput(String description, Pageable pageable) {
        this.description = description;
        this.pageable = pageable;
    }
}
