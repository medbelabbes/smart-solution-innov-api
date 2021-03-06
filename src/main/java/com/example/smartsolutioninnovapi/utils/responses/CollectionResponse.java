package com.example.smartsolutioninnovapi.utils.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionResponse {
    private boolean status;
    private String message;
    private List<?> data;
    private int count;
}
