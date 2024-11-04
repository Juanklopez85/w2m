package com.w2m.spaceship.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.w2m.spaceship.service.SpaceshipService;
import com.w2m.spaceship.service.domain.NotFoundException;
import com.w2m.spaceship.service.domain.Spaceship;
import com.w2m.spaceship.web.ApiTester;
import com.w2m.spaceship.web.model.SpaceshipDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(SpaceshipController.class)
class SpaceshipControllerTest extends ApiTester {

  @MockBean
  private SpaceshipService spaceshipService;

  @Autowired
  private ConversionService conversionService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void givenPage_whenGetSpaceshipsByPage_thenVerifyResponseOK() throws Exception  {
    Spaceship spaceship = Spaceship.builder()
        .spaceshipId(1L)
        .name("test name")
        .spaceshipClass("test class")
        .manufacturer("test manufacturer")
        .model("test model")
        .cost(123L)
        .length(456.78f)
        .passengers(100L)
        .build();
    when(spaceshipService.getSpaceshipsByPage(1)).thenReturn(List.of(spaceship));

    mockMvc.perform(
            MockMvcRequestBuilders.get("/spaceship?page={page}", 1))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].spaceshipId").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("test name"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].spaceshipClass").value("test class"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].manufacturer").value("test manufacturer"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("test model"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost").value(123L))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].length").value(456.78f))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].passengers").value(100L));
  }

  @Test
  void givenNoPage_whenGetSpaceshipsByPage_thenVerifyResponseOK() throws Exception  {
    Spaceship spaceship = Spaceship.builder()
        .spaceshipId(1L)
        .name("test name")
        .spaceshipClass("test class")
        .manufacturer("test manufacturer")
        .model("test model")
        .cost(123L)
        .length(456.78f)
        .passengers(100L)
        .build();
    when(spaceshipService.getSpaceshipsByPage(0)).thenReturn(List.of(spaceship));

    mockMvc.perform(
            MockMvcRequestBuilders.get("/spaceship"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].spaceshipId").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("test name"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].spaceshipClass").value("test class"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].manufacturer").value("test manufacturer"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("test model"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost").value(123L))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].length").value(456.78f))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].passengers").value(100L));
  }

  @Test
  void givenSpaceshipId_whenGetSpaceshipById_thenVerifyResponseOK() throws Exception  {
    Spaceship spaceship = Spaceship.builder()
        .spaceshipId(1L)
        .name("test name")
        .spaceshipClass("test class")
        .manufacturer("test manufacturer")
        .model("test model")
        .cost(123L)
        .length(456.78f)
        .passengers(100L)
        .build();
    when(spaceshipService.getSpaceshipById(5)).thenReturn(spaceship);

    mockMvc.perform(
            MockMvcRequestBuilders.get("/spaceship/{spaceshipId}", 5))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.spaceshipId").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test name"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.spaceshipClass").value("test class"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer").value("test manufacturer"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("test model"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.cost").value(123L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.length").value(456.78f))
        .andExpect(MockMvcResultMatchers.jsonPath("$.passengers").value(100L));
  }

  @Test
  void givenNotFoundException_whenGetSpaceshipById_thenVerifyResponseIsNotFound() throws Exception  {
    when(spaceshipService.getSpaceshipById(5)).thenThrow(new NotFoundException("Spaceship not found"));

    mockMvc.perform(
            MockMvcRequestBuilders.get("/spaceship/{spaceshipId}", 5))
        .andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Spaceship not found"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("NOT_FOUND"));
  }

  @Test
  void givenSpaceshipName_whenFindSpaceshipsByName_thenVerifyResponseOK() throws Exception  {
    Spaceship spaceship = Spaceship.builder()
        .spaceshipId(1L)
        .name("test name")
        .spaceshipClass("test class")
        .manufacturer("test manufacturer")
        .model("test model")
        .cost(123L)
        .length(456.78f)
        .passengers(100L)
        .build();
    when(spaceshipService.getSpaceshipsByName("test name")).thenReturn(List.of(spaceship));

    mockMvc.perform(
            MockMvcRequestBuilders.get("/spaceship/find/{spaceshipName}", "test name"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].spaceshipId").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("test name"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].spaceshipClass").value("test class"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].manufacturer").value("test manufacturer"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("test model"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost").value(123L))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].length").value(456.78f))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].passengers").value(100L));
  }

  @Test
  void givenSpaceship_whenCreateSpaceship_thenVerifyResponseOK() throws Exception  {
    SpaceshipDto spaceshipDto = SpaceshipDto.builder()
        .build();
    Spaceship spaceship = Spaceship.builder()
        .spaceshipId(1L)
        .name("test name")
        .spaceshipClass("test class")
        .manufacturer("test manufacturer")
        .model("test model")
        .cost(123L)
        .length(456.78f)
        .passengers(100L)
        .build();
    when(spaceshipService.createSpaceship(any(Spaceship.class))).thenReturn(spaceship);

    mockMvc.perform(
            MockMvcRequestBuilders.post("/spaceship")
                .content(asJsonString(spaceshipDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.spaceshipId").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test name"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.spaceshipClass").value("test class"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer").value("test manufacturer"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("test model"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.cost").value(123L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.length").value(456.78f))
        .andExpect(MockMvcResultMatchers.jsonPath("$.passengers").value(100L));
  }

  @Test
  void givenEmptySpaceship_whenCreateSpaceship_thenVerifyResponseIsBadRequest() throws Exception  {
    mockMvc.perform(
            MockMvcRequestBuilders.post("/spaceship")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("DESERIALIZATION_ERROR"));
  }

  @Test
  void givenSpaceship_whenModifySpaceship_thenVerifyResponseOK() throws Exception  {
    SpaceshipDto spaceshipDto = SpaceshipDto.builder()
        .build();
    Spaceship spaceship = Spaceship.builder()
        .spaceshipId(1L)
        .name("test name")
        .spaceshipClass("test class")
        .manufacturer("test manufacturer")
        .model("test model")
        .cost(123L)
        .length(456.78f)
        .passengers(100L)
        .build();
    when(spaceshipService.modifySpaceship(any(Spaceship.class))).thenReturn(spaceship);

    mockMvc.perform(
            MockMvcRequestBuilders.put("/spaceship")
                .content(asJsonString(spaceshipDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.spaceshipId").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test name"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.spaceshipClass").value("test class"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer").value("test manufacturer"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("test model"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.cost").value(123L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.length").value(456.78f))
        .andExpect(MockMvcResultMatchers.jsonPath("$.passengers").value(100L));
  }

  @Test
  void givenSpaceshipId_whenDeleteSpaceship_thenVerifyResponseOK() throws Exception  {
    doNothing().when(spaceshipService).deleteSpaceship(1L);

    mockMvc.perform(
            MockMvcRequestBuilders.delete("/spaceship/{spaceshipId}", 1))
        .andExpect(status().isOk());
  }

}