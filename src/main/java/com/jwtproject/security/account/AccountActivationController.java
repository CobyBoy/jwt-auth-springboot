package com.jwtproject.security.account;

import com.jwtproject.security.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountActivationController {
    private final AccountActivationService accountActivationService;

    @GetMapping("/api/v1/confirm-account")
    public ResponseEntity<ApiResponseDto> confirmAccount(@RequestParam("token") String confirmationToken) {
        return ResponseEntity.ok(accountActivationService.activateAccount(confirmationToken));
    }
}
