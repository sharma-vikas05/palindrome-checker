package com.example.cme.palindromechecker.persistent.impl;

import com.example.cme.palindromechecker.persistent.PalindromePersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class PalindromePersistenceServiceImpl implements PalindromePersistenceService {

    private static final Logger LOG = LoggerFactory.getLogger("PalindromeCheckerPersistenceImpl.class");

    protected String FILE_PATH = "/Users/vikassharma/projects/palindrome-checker/cache.txt";

    public boolean save(Map<String, Boolean> cache){
        try {
            Files.deleteIfExists(Paths.get(FILE_PATH));
            File file = new File(FILE_PATH);
            file.createNewFile();
            StringBuilder content = new StringBuilder();
            for(Map.Entry<String, Boolean> entry: cache.entrySet()){
                String value = entry.getKey() + "," + entry.getValue().toString() + ";";
                content.append(value);
            }
            try(FileWriter fw = new FileWriter(file)){
                fw.write(content.toString());
            }catch (IOException e){
                LOG.error("Error during writing the cache content to the file due to {}", e.getMessage());
                return false;
            }
        } catch (IOException ie) {
            LOG.error("Error during saving the cache content to the file due to {}", ie.getMessage());
            return false;
        }
        LOG.info("Updated the content of the cache in the File at the path {}", FILE_PATH);
        return true;
    }

    @Async
    public CompletableFuture<Map<String, Boolean>> load(){
        Map<String, Boolean> cache = new HashMap<>();
        try{
            String content = Files.readString(Paths.get(FILE_PATH));
            if(null !=  content){
                String[] rows = content.split(";");
                for(String row: rows){
                    String[] data = row.split(",");
                    cache.put(data[0], Boolean.valueOf(data[1]));
                }
            }
            LOG.info("Loaded the content of the file in the cache from the path {}", FILE_PATH);
        }catch (IOException ie){
            LOG.error("Error during loading the cache content from the file due to {}", ie.getMessage());
        }

        return CompletableFuture.completedFuture(cache);
    }

}
