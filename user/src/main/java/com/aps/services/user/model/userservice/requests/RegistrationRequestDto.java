package com.aps.services.user.model.userservice.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {
    @NotNull
    @Size(min = 4)
    private String username;

    @NotNull
    @Email
    @Size(min = 4)
    private String email;

    @NotNull
    @Size(min = 4)
    private String password;
}
