package com.polstat.perpustakaan.auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuthRequest {
    @NotNull @Email
    private String email;
    @NotNull
    private String password;
}
