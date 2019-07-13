package com.aps.services.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDto {
    @Size(min = 4, max = 250)
    @NotNull
    private String username;

    @NotNull
    @Size(min = 4)
    private String password;
}
