package com.example.smartsolutioninnovapi.utils.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private boolean status;
    private String message;
    private Object data;
}
