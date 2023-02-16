package com.zmo.bookmark.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<?> responseBuilder(
            HttpStatus httpStatus, Object object){

        Map<String, Object> response = new HashMap<>();
        response.put("response", object);

        return new ResponseEntity<>(response, httpStatus);
    }

    public static ResponseEntity<?> responseBuilder(
            HttpStatus httpStatus, Object beforeUpdate, Object afterUpdate){

        Map<String, Object> response = new HashMap<>();
        response.put("original bookmark", beforeUpdate);
        response.put("updated bookmark", afterUpdate);

        return new ResponseEntity<>(response, httpStatus);
    }
}
