# Palindrome Checker
It is a web application and currently exposing GET endpoints - http:localhost:8080/palindrome-checker/v1/verify/{userName}/{value} to verify whether given value is Palindrome.
It performs the validations on the input and marks it as failed if it contains spaces or numbers in it. Please note the validations are case-sensitive and application logs should help in diagnosing any support queries.

# Local Mode

* Run "src/main/java/com/example/cme/palindromechecker/PalindromeCheckerApplication.java"
* Use one of the following ways to access the GET API and make sure to update the <portNumber> with the Tomcat started on port value.
  * Swagger UI - http://localhost:<portNumber>/swagger-ui.html#!/palindrome45checker45controller/palindromeCheckerUsingGET
  * API Url - http://localhost:<portNumbe>/palindrome-checker/v1/verify/{username}/{value}


