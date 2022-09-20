package com.example.ezkeeper;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


//结果封装类

public class JSONResult<T> extends ResponseEntity<T> {

    public JSONResult(HttpStatus status) {
        super(status);
    }

    public JSONResult(String code, String msg, T data) {
        super((T) Message.custom(code, msg, data), Message.num2HttpStatus(code));
    }

    public JSONResult(String code, String msg) {
        super((T) Message.custom(code, msg), Message.num2HttpStatus(code));
    }

    public static <T> JSONResult<T> success(String msg){return  new JSONResult("200",msg,null);}

    public static <T> JSONResult<T> success(T data) {
        return new JSONResult("200", "操作成功", data);
    }

    public static <T> JSONResult<T> success(T data, String msg) {
        return new JSONResult("200", msg, data);
    }

    public static <T> JSONResult<T> failed(T data) {
        return new JSONResult("500", "操作失败", data);
    }

    public static <T> JSONResult<T> failMsg(String msg) {
        return new JSONResult("500", msg);
    }

    public static <T> JSONResult<T> validateFailed(T data){
        return new JSONResult("404","参数检验失败",data);
    }

    public static <T> JSONResult<T> validateFailedMsg(String msg){
        return new JSONResult("404",msg);
    }

    public static <T> JSONResult<T> unathorzied(){
        return new JSONResult("401", "暂未登录或token已过期");
    }

    public static <T> JSONResult<T> forbidden(){
        return new JSONResult("403", "没有相关权限");
    }

    public static <T> JSONResult<T> custom(String code, String msg, T data) {
        return new JSONResult(code, msg, data);
    }
}

@Data
class Message<T>{

    String status;
    String message;

    T data;

    public Message(){

    }

    public Message(String status, String message){
        this.status = status;
        this.message = message;
    }

    public Message(String status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> Message<T> custom(String status, String message, T data){
        return new Message(status,message,data);
    }

    public static <T> Message<T> custom(String status, String message){
        return new Message(status,message);
    }

    public static HttpStatus num2HttpStatus(String code){
        HttpStatus status = HttpStatus.NOT_FOUND;
        for (HttpStatus httpStatus : HttpStatus.values()){
            if (Integer.parseInt(code) == httpStatus.value()){
                return httpStatus;
            }
        }
        return status;
    }
}
