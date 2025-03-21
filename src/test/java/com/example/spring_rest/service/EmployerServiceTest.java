package com.example.spring_rest.service;

import com.example.spring_rest.dto.EmployerFacade;
import com.example.spring_rest.dto.EmployerRequest;
import com.example.spring_rest.dto.EmployerResponse;
import com.example.spring_rest.exception.NotFoundException;
import com.example.spring_rest.model.Employer;
import com.example.spring_rest.repository.EmployerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {
    @Mock
    private EmployerRepository employerRepository;

    @Mock
    private EmployerFacade employerFacade;

    @InjectMocks
    private EmployerService employerService;

    private Employer employer;
    private EmployerRequest employerRequest;
    private EmployerResponse employerResponse;

    private final String name = "Tech Corp";
    private final String address = "123 Tech Lane";

    @BeforeEach
    void setUp() {
        employer = new Employer(name, address, new HashSet<>());
        employerRequest = new EmployerRequest( name, address);
        employerResponse = new EmployerResponse(1L, name, address);
    }

    @Test
    void testCreateEmployer() {
        when(employerFacade.toEntity(employerRequest)).thenReturn(employer);
        when(employerRepository.save(employer)).thenReturn(employer);
        when(employerFacade.toResponse(employer)).thenReturn(employerResponse);

        EmployerResponse savedEmployer = employerService.createEmployer(employerRequest);

        assertNotNull(savedEmployer);
        assertEquals(name, savedEmployer.getName());
        assertEquals(address, savedEmployer.getAddress());
    }

    @Test
    void testGetAllEmployers() {
        when(employerRepository.findAll()).thenReturn(List.of(employer));
        when(employerFacade.toResponse(employer)).thenReturn(employerResponse);

        List<EmployerResponse> employers = employerService.getAllEmployers();

        assertNotNull(employers);
        assertEquals(1, employers.size());
        assertEquals(name, employers.getFirst().getName());
    }

    @Test
    void testGetEmployerByIdSuccess() {
        when(employerRepository.findById(1L)).thenReturn(Optional.of(employer));
        when(employerFacade.toResponse(employer)).thenReturn(employerResponse);

        EmployerResponse foundEmployer = employerService.getEmployerById(1L);

        assertNotNull(foundEmployer);
        assertEquals(1L, foundEmployer.getId());
        assertEquals(name, foundEmployer.getName());
    }

    @Test
    void testGetEmployerByIdNotFound() {
        when(employerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> employerService.getEmployerById(1L));
    }


    @Test
    void testDelete() {
        long employerId = 1L;

        willDoNothing().given(employerRepository).deleteById(employerId );

        employerService.deleteEmployer(employerId);

        verify(employerRepository, times(1)).deleteById(1L);
    }
}
