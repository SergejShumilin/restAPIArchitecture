package org.example.controller.maturityLevel_1.requests;

import org.example.dto.SubscriptionRequestDto;
import org.example.entity.Action;

public class SubscriptionRequest {
    private Action action;
    private SubscriptionRequestDto requestDto;

    public SubscriptionRequest() {
    }

    public SubscriptionRequest(Action action, SubscriptionRequestDto requestDto) {
        this.action = action;
        this.requestDto = requestDto;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public SubscriptionRequestDto getRequestDto() {
        return requestDto;
    }

    public void setRequestDto(SubscriptionRequestDto requestDto) {
        this.requestDto = requestDto;
    }
}
