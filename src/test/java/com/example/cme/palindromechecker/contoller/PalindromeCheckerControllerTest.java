package com.example.cme.palindromechecker.contoller;

import com.example.cme.palindromechecker.persistent.PalindromePersistenceService;
import com.example.cme.palindromechecker.validator.PalindromeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PalindromeCheckerControllerTest {

    private MockMvc mockMvc;

    PalindromeCheckerController palindromeCheckerController;

    @Mock
    private PalindromeValidator palindromeValidator;

    @Mock
    private PalindromePersistenceService palindromePersistenceService;

    @BeforeEach
    void setUp() {
        palindromeCheckerController = new PalindromeCheckerController(palindromeValidator, palindromePersistenceService);
        mockMvc = MockMvcBuilders.standaloneSetup(palindromeCheckerController).build();
    }

    @Test
    void palindromeChecker_success_response() throws Exception {
        when(palindromeValidator.validate(anyString())).thenReturn(true);
        when(palindromePersistenceService.save(anyMap())).thenReturn(true);

        mockMvc.perform(get("/palindrome-checker/v1/verify/testUser/testValue")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Result of the Palindrome Check is <b>false</b>"));
    }

    @Test
    void palindromeChecker_bad_request() throws Exception {
        when(palindromeValidator.validate(anyString())).thenReturn(false);

        mockMvc.perform(get("/palindrome-checker/v1/verify/testUser/test Value")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("<b>Bad Request</b>, please note both spaces and numbers are not allowed in the value."));
    }
}