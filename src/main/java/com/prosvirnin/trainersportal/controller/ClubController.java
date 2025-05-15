package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.model.dto.group.ClubCreateRequest;
import com.prosvirnin.trainersportal.model.dto.group.ClubEditRequest;
import com.prosvirnin.trainersportal.model.dto.group.ClubInfo;
import com.prosvirnin.trainersportal.model.dto.group.ClubListItem;
import com.prosvirnin.trainersportal.service.group.ClubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/clubs")
@RequiredArgsConstructor
@Tag(name = "Клубы", description = "Управление клубами и их группами")
public class ClubController {

    private final ClubService clubService;

    @Operation(summary = "Получить информацию о клубе по ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClubInfo> getClub(@PathVariable Long id) {
        return ResponseEntity.ok(clubService.getById(id));
    }

    @Operation(summary = "Получить все клубы (полная информация)")
    @GetMapping
    public ResponseEntity<Collection<ClubInfo>> getAllClubs() {
        return ResponseEntity.ok(clubService.getAll());
    }

    @Operation(summary = "Получить краткий список клубов (для выпадающих списков и т.п.)")
    @GetMapping("/list")
    public ResponseEntity<Collection<ClubListItem>> getAllListItems() {
        return ResponseEntity.ok(clubService.getAllListItems());
    }

    @Operation(summary = "Создать новый клуб")
    @PostMapping
    public ResponseEntity<ClubInfo> createClub(
            @Valid @RequestBody ClubCreateRequest request) {
        return ResponseEntity.ok(clubService.save(request));
    }

    @Operation(summary = "Редактировать существующий клуб")
    @PutMapping("/{id}")
    public ResponseEntity<ClubInfo> updateClub(
            @PathVariable Long id,
            @Valid @RequestBody ClubEditRequest request) {
        return ResponseEntity.ok(clubService.editById(id, request));
    }

    @Operation(summary = "Удалить клуб и связанные с ним группы")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        clubService.deleteWithGroupsById(id);
        return ResponseEntity.noContent().build();
    }
}
