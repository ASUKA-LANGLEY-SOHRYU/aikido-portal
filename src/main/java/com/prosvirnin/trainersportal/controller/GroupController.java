package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.model.dto.group.GroupCreateRequest;
import com.prosvirnin.trainersportal.model.dto.group.GroupDto;
import com.prosvirnin.trainersportal.model.dto.group.GroupEditRequest;
import com.prosvirnin.trainersportal.model.dto.group.GroupListItem;
import com.prosvirnin.trainersportal.service.group.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@Tag(name = "Группы", description = "Управление учебными группами")
public class GroupController {

    private final GroupService groupService;

    @Operation(summary = "Получить все группы (полная информация)")
    @GetMapping
    public ResponseEntity<Collection<GroupDto>> getAll() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @Operation(summary = "Получить краткий список всех групп")
    @GetMapping("/list")
    public ResponseEntity<Collection<GroupListItem>> getAllItems() {
        return ResponseEntity.ok(groupService.findAllItems());
    }

    @Operation(summary = "Создать новую группу")
    @PostMapping
    public ResponseEntity<GroupDto> createGroup(
            @Valid @RequestBody GroupCreateRequest request) {
        GroupDto saved = groupService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Редактировать существующую группу")
    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> updateGroup(
            @PathVariable Long id,
            @Valid @RequestBody GroupEditRequest request) {
        GroupDto updated = groupService.editById(id, request);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Удалить группу по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Добавить ученика в группу")
    @PostMapping("/{groupId}/clients/{clientId}")
    public ResponseEntity<Void> addClientToGroup(
            @PathVariable Long groupId,
            @PathVariable Long clientId) {
        groupService.addClientToGroup(clientId, groupId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить ученика из группы")
    @DeleteMapping("/{groupId}/clients/{clientId}")
    public ResponseEntity<Void> removeClientFromGroup(
            @PathVariable Long groupId,
            @PathVariable Long clientId) {
        groupService.removeClientFromGroup(clientId, groupId);
        return ResponseEntity.noContent().build();
    }
}
