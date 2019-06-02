package com.benny.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultModel {

    private int code;

    private String message;

    public ResultModel(int code, String message) {
        this.code = code;
        this.message = message;
    }

}