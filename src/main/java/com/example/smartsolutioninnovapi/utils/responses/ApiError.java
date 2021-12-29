package com.example.smartsolutioninnovapi.utils.responses;

public class ApiError {
    private boolean status;
    private String response;
    private Object data;

    public ApiError(boolean status, String response, Object data) {
        this.status = status;
        this.response = response;
        this.data = data;
    }



    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
