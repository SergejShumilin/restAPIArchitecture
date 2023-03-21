package org.example.controller.maturityLevel_0;

import org.example.dto.SubscriptionRequestDto;
import org.example.dto.UserRequestDto;
import org.example.entity.Action;
import org.example.entity.EntityType;

public class GeneralRequest {
    private Action action;

    private EntityType entityType;
    private UserRequestDto userRequestDto;

    private SubscriptionRequestDto subscriptionRequestDto;

    public GeneralRequest() {
    }

    public GeneralRequest(Action action, EntityType entityType, UserRequestDto userRequestDto, SubscriptionRequestDto subscriptionRequestDto) {
        this.action = action;
        this.entityType = entityType;
        this.userRequestDto = userRequestDto;
        this.subscriptionRequestDto = subscriptionRequestDto;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public UserRequestDto getUserRequestDto() {
        return userRequestDto;
    }

    public void setUserRequestDto(UserRequestDto userRequestDto) {
        this.userRequestDto = userRequestDto;
    }

    public SubscriptionRequestDto getSubscriptionRequestDto() {
        return subscriptionRequestDto;
    }

    public void setSubscriptionRequestDto(SubscriptionRequestDto subscriptionRequestDto) {
        this.subscriptionRequestDto = subscriptionRequestDto;
    }
}
