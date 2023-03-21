package org.example.dto;

import org.springframework.hateoas.RepresentationModel;

public class SubscriptionResponseDto extends RepresentationModel<SubscriptionResponseDto> {
    private Long id;
    private Long userId;
    private String startDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
