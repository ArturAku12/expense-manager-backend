package com.example.expense_manager_riverty.service.inputs;

import lombok.Getter;

import java.util.Date;

@Getter
public class DateRange {
    private Date startDate;
    private Date endDate;

    public DateRange(Date startDate) {
        this.startDate = startDate;
        this.endDate = new Date();
    }
}
