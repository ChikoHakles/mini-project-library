package id.co.indivara.perpustakaan.entities;

import java.util.ArrayList;
import java.util.Date;

public class ResponseBody {
    private final Date timestamp = new Date();
    private int status;
    private String message;
    private ArrayList<String> errors;
    private ArrayList<Object> datas;

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

    public ArrayList<Object> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<Object> datas) {
        this.datas = datas;
    }

    public ResponseBody(int status, String message, ArrayList<String> errors, ArrayList<Object> datas) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.datas = datas;
    }
}
