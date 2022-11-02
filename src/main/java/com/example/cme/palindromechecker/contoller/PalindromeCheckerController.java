package com.example.cme.palindromechecker.contoller;

import com.example.cme.palindromechecker.persistent.PalindromePersistenceService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cme.palindromechecker.validator.PalindromeValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("palindrome-checker/v1")
public class PalindromeCheckerController {

    private static final Logger LOG = LoggerFactory.getLogger("PalindromeCheckerController.class");

    private PalindromeValidator palindromeValidator;

    private PalindromePersistenceService palindromePersistenceService;

    protected Map<String, Boolean> cache ;

    public PalindromeCheckerController(PalindromeValidator palindromeValidator, PalindromePersistenceService palindromePersistenceService){
        this.palindromeValidator = palindromeValidator;
        this.palindromePersistenceService = palindromePersistenceService;
        loadCache();
    }

    void loadCache(){
        try{
            CompletableFuture<Map<String, Boolean>> result = palindromePersistenceService.load();
            this.cache = null !=result ? result.get() : new HashMap<>();
        } catch (InterruptedException | ExecutionException ie){
            this.cache = new HashMap<>();
            LOG.error("Thread was interrupted or got execution exception during its run {}", ie.getMessage());
        }
        LOG.info("Cache load is completed ");
    }

    @ApiResponses(
            value={
                   @ApiResponse(code=200, message = "Successfully verified the input value"),
                    @ApiResponse(code=400, message = "Bad request, provided input value is not valid")
            }
    )
    @GetMapping("/verify/{userName}/{value}")
    public ResponseEntity<String> palindromeChecker(@PathVariable("userName") String userName, @PathVariable("value") String value){

        Boolean result = null;

        if(!palindromeValidator.validate(value)){
            LOG.error("Invalid input received {}", value);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("<b>Bad Request</b>, please note both spaces and numbers are not allowed in the value.");
        }

        if (null != cache && cache.containsKey(value)){
            LOG.info("Found the value in cache itself {}", value);
            return ResponseEntity.status(HttpStatus.OK).body("Result of the Palindrome Check is <b>" + cache.get(value) + "</b>");
        }else{
            StringBuilder reversedValue = new StringBuilder(value);
            result = reversedValue.reverse().toString().equals(value);
            cache.put(value, result);
            LOG.info("Result of the Palindrome check for the value {} is {}", value, result);
            palindromePersistenceService.save(cache);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Result of the Palindrome Check is <b>" + result + "</b>");
    }

}
