package org.example.controller.maturityLevel_1.requests;

import org.example.dto.UserRequestDto;
import org.example.entity.Action;

public class UserRequest {
    private Action action;
    private UserRequestDto requestDto;

    public UserRequest() {
    }

    public UserRequest(Action action, UserRequestDto requestDto) {
        this.action = action;
        this.requestDto = requestDto;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public UserRequestDto getRequestDto() {
        return requestDto;
    }

    public void setRequestDto(UserRequestDto requestDto) {
        this.requestDto = requestDto;
    }
}
