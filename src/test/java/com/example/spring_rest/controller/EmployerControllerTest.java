package com.example.spring_rest.controller;

import com.example.spring_rest.dto.EmployerRequest;
import com.example.spring_rest.dto.EmployerResponse;
import com.example.spring_rest.service.EmployerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EmployerControllerTest {
    @Mock
    private EmployerService employerService;

    @InjectMocks
    private EmployerController employerController;

    private MockMvc mockMvc;
    private EmployerRequest employerRequest;
    private EmployerResponse employerResponse;

    private final String name = "Tech Corp";
    private final String address = "123 Tech Lane";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employerController).build();

        employerRequest = new EmployerRequest();// Set ID for the request to match the response
        employerRequest.setAddress(address);
        employerRequest.setName(name);

        employerResponse = new EmployerResponse(1L, name, address);
    }

    @Test
    void testGetAllEmployers() throws Exception {
        when(employerService.getAllEmployers()).thenReturn(List.of(employerResponse));

        // Perform the GET request and verify the response
        mockMvc.perform(get("/employers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].address").value(address));
    }

    @Test
    void testCreateSuccess() throws Exception {
        when(employerService.createEmployer(employerRequest)).thenReturn(employerResponse);

        mockMvc.perform(post("/employers")
                        .contentType("application/json")
                        .content("{\"name\":\"" + name + "\", \"address\":\"" + address + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.address").value(address));
    }

    @Test
    void testCreateBadRequest() throws Exception {
        mockMvc.perform(post("/employers")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
