package com.example.cme.palindromechecker.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PalindromeValidatorTest {

    PalindromeValidator palindromeValidator;

    @BeforeEach
    public void setup(){
        palindromeValidator = new PalindromeValidator();
    }

    @Test
    void validate_valid_input() {
        Boolean res = palindromeValidator.validate("kayak");
        assertTrue(res);
    }

    @Test
    void validate_invalid_input_with_spaces() {
        Boolean res = palindromeValidator.validate("Some word");
        assertFalse(res);
    }

    @Test
    void validate_invalid_input_with_numbers() {
        Boolean res = palindromeValidator.validate("1 word");
        assertFalse(res);
    }
}