package id.co.indivara.perpustakaan.entities;

import java.util.ArrayList;
import java.util.Date;

public class ResponseBody<T> {
    private final Date timestamp = new Date();
    private int status;
    private String message;
    private ArrayList<String> errors;
    private ArrayList<T> datas;

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

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public ArrayList<T> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<T> datas) {
        this.datas = datas;
    }

    public ResponseBody(int status, String message, ArrayList<String> errors, ArrayList<T> datas) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.datas = datas;
    }
}
