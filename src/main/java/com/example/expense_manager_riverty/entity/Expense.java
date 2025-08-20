package com.example.expense_manager_riverty.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "expense")
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Expense amount cannot be null")
    @DecimalMin(value = "0.01", message = "Expense amount must be at least 0.01")
    @DecimalMax(value = "999999.99", message = "Expense amount cannot exceed 999,999.99")
    @Digits(integer = 6, fraction = 2, message = "Amount must have at most 6 digits before decimal and 2 after")
    @Column(name = "amount", nullable = false, precision = 8, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "Expense description cannot be null")
    @NotBlank(message = "Expense description cannot be blank")
    @Size(min = 3, max = 500, message = "Expense description must be between 3 and 500 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,!?'-]+$", message = "Description contains invalid characters")
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @NotNull(message = "Expense date cannot be null")
    @PastOrPresent(message = "Expense date cannot be in the future")
    @Column(name = "date", nullable = false)
    private Date date;

    @NotNull(message = "Expense category cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 50)
    private ExpenseCategory category;

    @Size(max = 10, message = "Cannot have more than 10 tags")
    @Column(name = "tags")
    private List<@NotBlank(message = "Tag cannot be blank") 
                  @Size(min = 1, max = 30, message = "Each tag must be between 1 and 30 characters")
                  @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Tags can only contain letters, numbers, hyphens, and underscores") String> tags;
}
