package com.benny.blog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultModel {

    private int code;

    private String message;

    public ResultModel(int code, String message) {
        this.code = code;
        this.message = message;
    }

}