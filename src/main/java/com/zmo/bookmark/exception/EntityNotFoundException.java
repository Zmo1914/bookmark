package com.zmo.bookmark.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.text.MessageFormat;

@Slf4j
@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class EntityNotFoundException extends RuntimeException{

    @Serial
    private static final String ENTITY_NOT_FOUND="Entity ''{0}'' with id ''{1}'' not found";

    public EntityNotFoundException(Class entityClass, int id){
       log.error(MessageFormat.format(ENTITY_NOT_FOUND, entityClass.getName(), id));
    }

    public EntityNotFoundException(Class entityClass, String name){
        log.error(MessageFormat.format(ENTITY_NOT_FOUND, entityClass.getName(), name));
    }

}
