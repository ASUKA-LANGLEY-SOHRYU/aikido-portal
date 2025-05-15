package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.model.dto.user.TrainerInfo;
import com.prosvirnin.trainersportal.service.user.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trainer")
@RequiredArgsConstructor
@Tag(name = "Тренеры", description = "Управление тренерами")
public class TrainerController {
    private final TrainerService trainerService;

    @Operation(summary = "Получить информацию о тренере по ID")
    @GetMapping("/{id}")
    private ResponseEntity<TrainerInfo> getInfoById(@PathVariable Long id)
    {
        return ResponseEntity.ok(trainerService.getById(id));
    }
}
