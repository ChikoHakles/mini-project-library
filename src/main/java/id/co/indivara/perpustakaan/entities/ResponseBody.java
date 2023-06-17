package id.co.indivara.perpustakaan.entities;

import java.util.ArrayList;
import java.util.Date;

public class ResponseBody<T> {
    private final Date timestamp = new Date();
    private int status;
    private String message;
    private ArrayList<String> error;
    private T data;

    public Date getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getError() {
        return error;
    }

    public void setError(ArrayList<String> error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseBody(int status, String message, ArrayList<String> error, T data) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.data = data;
    }
}
