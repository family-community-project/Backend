package com.example.family.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthMailResponse {
    boolean success;
    String authCode;
}
