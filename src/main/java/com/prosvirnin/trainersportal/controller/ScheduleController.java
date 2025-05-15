package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.model.dto.group.ScheduleDto;
import com.prosvirnin.trainersportal.service.group.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
@Tag(name = "Расписание", description = "Получение расписаний по клубу и по группе")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(
            summary = "Получить расписание по ID клуба",
            description = "Возвращает список всех расписаний, связанных с указанным клубом."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Расписание успешно получено"),
            @ApiResponse(responseCode = "404", description = "Клуб не найден или расписания отсутствуют")
    })
    @Parameter(name = "club_id", description = "Идентификатор клуба", required = true)
    @GetMapping("/club/{club_id}")
    public ResponseEntity<Collection<ScheduleDto>> getAllByClubId(
            @PathVariable("club_id") Long clubId) {
        return ResponseEntity.ok(scheduleService.finAllByClubId(clubId));
    }

    @Operation(
            summary = "Получить расписание по ID группы",
            description = "Возвращает список всех расписаний, связанных с указанной группой."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Расписание успешно получено"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена или расписания отсутствуют")
    })
    @Parameter(name = "group_id", description = "Идентификатор группы", required = true)
    @GetMapping("/group/{group_id}")
    public ResponseEntity<Collection<ScheduleDto>> getAllByGroupId(
            @PathVariable("group_id") Long groupId) {
        return ResponseEntity.ok(scheduleService.findAllByGroupId(groupId));
    }
}
