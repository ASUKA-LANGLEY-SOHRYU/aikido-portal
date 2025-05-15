package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.model.domain.user.Role;
import com.prosvirnin.trainersportal.model.dto.user.UserEditRequest;
import com.prosvirnin.trainersportal.model.dto.user.UserFilterRequest;
import com.prosvirnin.trainersportal.model.dto.user.UserListItem;
import com.prosvirnin.trainersportal.model.dto.user.UserProfileDto;
import com.prosvirnin.trainersportal.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Управление пользователями")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получить профиль текущего авторизованного пользователя")
    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getByUserDetails(userDetails));
    }

    @Operation(summary = "Обновить профиль текущего пользователя")
    @PutMapping("/me")
    public ResponseEntity<UserProfileDto> editMe(@RequestBody UserEditRequest request,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.editByUserDetails(userDetails, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить пользователя по ID (только для админа)")
    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDto> editById(@RequestBody UserEditRequest request,
                                                   @PathVariable Long id) {
        return ResponseEntity.ok(userService.editById(id, request));
    }

    @Operation(summary = "Получить список пользователей с фильтрацией")
    @PostMapping("/list")
    public ResponseEntity<Collection<UserListItem>> getUserListItems(
            @RequestBody UserFilterRequest request) {
        return ResponseEntity.ok(userService.findAllListItems(request));
    }

    @Operation(summary = "Получить профиль пользователя по ID")
    @GetMapping("/{id}/profile")
    public ResponseEntity<UserProfileDto> getUserProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getProfileById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BOSS')")
    @Operation(summary = "Установить роли пользователю по ID")
    @PutMapping("/{id}/roles")
    public ResponseEntity<Void> setUserRolesById(
            @PathVariable Long id,
            @RequestBody Set<Role> roles) {
        userService.setRolesById(id, roles);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Импорт учеников из Excel-файла")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> importClientsFromFile(@RequestPart("file") MultipartFile file) {
        userService.createClientsFromDatasheet(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
