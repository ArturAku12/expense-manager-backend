package com.example.expense_manager_riverty.service.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface Query<I, O> {
    ResponseEntity<O> execute(@Valid I input);
}
