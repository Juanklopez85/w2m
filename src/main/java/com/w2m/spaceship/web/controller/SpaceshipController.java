package com.w2m.spaceship.web.controller;

import com.w2m.spaceship.service.SpaceshipService;
import com.w2m.spaceship.service.domain.Spaceship;
import com.w2m.spaceship.web.model.ErrorDto;
import com.w2m.spaceship.web.model.SpaceshipDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/spaceship", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SpaceshipController {

  private final SpaceshipService spaceshipService;
  private final ConversionService conversionService;

  @Operation(summary = "Get all spaceships paginated", description = "Given a page, the endpoint retrieves 10 spaceships. If page is empty, will put 0 by default")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful"),
      @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
      @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @GetMapping
  public ResponseEntity<List<SpaceshipDto>> getSpaceshipsByPage(
      @Schema(description = "Page of the request", example = "1") @RequestParam Optional<Integer> page
  ) {
    List<SpaceshipDto> spaceshipDtoList = spaceshipService.getSpaceshipsByPage(page.orElse(0))
        .stream()
        .map(spaceship -> conversionService.convert(spaceship, SpaceshipDto.class))
        .toList();
    return ResponseEntity.ok(spaceshipDtoList);
  }

  @Operation(summary = "Get spaceship by id", description = "Return the spaceship by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful"),
      @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
      @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @GetMapping(path = "/{spaceshipId}")
  public ResponseEntity<SpaceshipDto> getSpaceshipById(
      @Schema(description = "Spaceship id", example = "1") @PathVariable Long spaceshipId
  ) {
    return ResponseEntity.ok(conversionService.convert(spaceshipService.getSpaceshipById(spaceshipId), SpaceshipDto.class));
  }

  @Operation(summary = "Get spaceship by name", description = "Return a spaceship list with name like spaceshipName")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful"),
      @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
      @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @GetMapping(path = "/find/{spaceshipName}")
  public ResponseEntity<List<SpaceshipDto>> findSpaceshipsByName(
      @Schema(description = "Name to search", example = "wing") @PathVariable String spaceshipName
  ) {
    List<SpaceshipDto> spaceshipDtoList = spaceshipService.getSpaceshipsByName(spaceshipName)
        .stream()
        .map(spaceship -> conversionService.convert(spaceship, SpaceshipDto.class))
        .toList();
    return ResponseEntity.ok(spaceshipDtoList);
  }

  @Operation(summary = "Create spaceship", description = "Create a new spaceship")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful"),
      @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
      @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @PostMapping
  public ResponseEntity<SpaceshipDto> createSpaceship(
      @Schema(description = "Spaceship data") @Valid @NotNull @RequestBody SpaceshipDto spaceshipDto
  ) {
    Spaceship spaceship = spaceshipService.createSpaceship(conversionService.convert(spaceshipDto, Spaceship.class));
    return ResponseEntity.ok(conversionService.convert(spaceship, SpaceshipDto.class));
  }

  @Operation(summary = "Modify spaceship", description = "Modify a spaceship")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful"),
      @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
      @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @PutMapping
  public ResponseEntity<SpaceshipDto> modifySpaceship(
      @Schema(description = "Spaceship data") @Valid @NotNull @RequestBody SpaceshipDto spaceshipDto
  ) {
    Spaceship spaceship = spaceshipService.modifySpaceship(conversionService.convert(spaceshipDto, Spaceship.class));
    return ResponseEntity.ok(conversionService.convert(spaceship, SpaceshipDto.class));
  }

  @Operation(summary = "Delete spaceship", description = "Delete a spaceship by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful"),
      @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
      @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @DeleteMapping(path = "/{spaceshipId}")
  public ResponseEntity<Void> deleteSpaceship(
      @Schema(description = "Spaceship id", example = "1") @PathVariable Long spaceshipId
  ) {
    spaceshipService.deleteSpaceship(spaceshipId);
    return ResponseEntity.ok().build();
  }
}
