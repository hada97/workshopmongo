package br.com.workshop.workshopmongo.resources.exceptions;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StandardError implements Serializable {

    private Long timestamp;
    private String status;
    private String error;
    private String message;
    private String path;

    public StandardError(){
    }

    public StandardError(Long timestamp, String status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

}
