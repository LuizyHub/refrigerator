package com.refrigerator.permission.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareDto {

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    private Boolean readable;

    @NotNull
    private Boolean writable;

    @NotNull
    private Boolean deletable;
}
