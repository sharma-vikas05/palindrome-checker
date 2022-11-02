package com.example.cme.palindromechecker.persistent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface PalindromePersistenceService {

    public boolean save(Map<String, Boolean> cache);

    public CompletableFuture<Map<String, Boolean>> load();

}
