// RoleRequestDto.java
package com.refrigerator.role.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequestDto {
    private String userEmail;
    private String role; // "RWD" | "RW" | "R" | "D"
}
