/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.matejkvassay.musiclibrary.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Matej Bordáč
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GenreInvalidArgumentException extends RuntimeException {

    public GenreInvalidArgumentException(String id, String message) {
        super("Genre argument error, id: " + id + " message: " + message);
    }
}