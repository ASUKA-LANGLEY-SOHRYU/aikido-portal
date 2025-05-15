package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.model.domain.jwt.RefreshToken;
import com.prosvirnin.trainersportal.model.domain.jwt.TokenPair;
import com.prosvirnin.trainersportal.model.dto.user.BossRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.LoginRequest;
import com.prosvirnin.trainersportal.model.dto.user.ClientRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.TrainerRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.admin.AdminRegistrationRequest;
import com.prosvirnin.trainersportal.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация и регистрация", description = "Регистрация пользователей и управление JWT токенами")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Регистрация ученика",
            description = "Регистрирует нового ученика (client). Доступно для ролей: ADMIN, BOSS, TRAINER."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешная регистрация. Возвращается пара токенов."),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для регистрации")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'BOSS', 'TRAINER')")
    @PostMapping("/client/register")
    public ResponseEntity<TokenPair> registerClient(
            @RequestBody ClientRegistrationRequest clientRegistrationRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(authService.registerClient(userDetails, clientRegistrationRequest));
    }

    @Operation(
            summary = "Регистрация тренера",
            description = "Регистрирует нового тренера. Доступно только ролям ADMIN и BOSS."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешная регистрация. Возвращается пара токенов."),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для регистрации")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'BOSS')")
    @PostMapping("/trainer/register")
    public ResponseEntity<TokenPair> registerTrainer(@RequestBody TrainerRegistrationRequest trainerRegistrationRequest) {
        return ResponseEntity.ok(authService.registerTrainer(trainerRegistrationRequest));
    }

    @Operation(
            summary = "Регистрация руководителя (Boss)",
            description = "Регистрирует нового пользователя с ролью BOSS. Доступно только для ADMIN."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешная регистрация. Возвращается пара токенов."),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для регистрации")
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/boss/register")
    public ResponseEntity<TokenPair> registerBoss(@RequestBody BossRegistrationRequest bossRegistrationRequest) {
        return ResponseEntity.ok(authService.registerBoss(bossRegistrationRequest));
    }

    @Operation(
            summary = "Регистрация администратора",
            description = "Регистрирует нового пользователя с ролью ADMIN. Доступно только для ADMIN (аутентифицированного)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешная регистрация. Возвращается пара токенов."),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для регистрации")
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/admin/register")
    public ResponseEntity<TokenPair> registerAdmin(
            @RequestBody AdminRegistrationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(authService.registerAdmin(userDetails, request));
    }

    @Operation(
            summary = "Вход пользователя в систему",
            description = "Аутентифицирует пользователя по логину и паролю, возвращает JWT токены."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный вход. Возвращается пара токенов."),
            @ApiResponse(responseCode = "401", description = "Неверный логин или пароль")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenPair> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Operation(
            summary = "Обновление пары токенов",
            description = "Обновляет access и refresh токены по действующему refresh токену."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Токены успешно обновлены"),
            @ApiResponse(responseCode = "401", description = "Неверный или истёкший refresh токен")
    })
    @PostMapping("/refresh")
    public ResponseEntity<TokenPair> obtainTokenPair(@RequestBody RefreshToken refreshToken) {
        return ResponseEntity.ok(authService.obtainTokenPair(refreshToken));
    }
}
