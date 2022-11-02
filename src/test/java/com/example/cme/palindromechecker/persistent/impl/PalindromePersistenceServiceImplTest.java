package com.example.cme.palindromechecker.persistent.impl;

import com.example.cme.palindromechecker.persistent.PalindromePersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class PalindromePersistenceServiceImplTest {

    PalindromePersistenceServiceImpl palindromePersistenceService;

    @BeforeEach
    void setUp() {
        palindromePersistenceService = new PalindromePersistenceServiceImpl();
        palindromePersistenceService.FILE_PATH = "/Users/vikassharma/projects/palindrome-checker/test_cache.txt";
    }

    @Test
    void save() {
        Map<String, Boolean> input = new HashMap<>();
        input.put("kayak", true);
        Boolean res = palindromePersistenceService.save(input);
        assertTrue(res);
    }

    @Test
    void load() {
        try {
            CompletableFuture<Map<String, Boolean>> result = palindromePersistenceService.load();
            assertNotNull(result);
            assertNotNull(result.get());
        }catch (InterruptedException | ExecutionException e){
            assertTrue(false);
        }
    }
}