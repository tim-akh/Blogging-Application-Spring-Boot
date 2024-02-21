package com.timakh.blog_app.exception;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Date timestamp;
    private int statusCode;
    private String message;


}
