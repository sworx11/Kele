package me.wemeet.kele.common.response;

import java.io.Serializable;
import java.util.Map;

public class ResponseEntity<T> implements Serializable {
    private int code;
    private String message;
    private T data;
    private Map<String, Object> ext;

    public ResponseEntity() {
        super();
    }

    public ResponseEntity(ResponseStatus status) {
        super();
        this.code = status.code;
        this.message = status.message;
    }

    public ResponseEntity(ResponseStatus status, T data) {
        super();
        this.code = status.code;
        this.message = status.message;
        this.data = data;
    }

    public ResponseEntity(ResponseStatus status, T data, Map<String, Object> ext) {
        super();
        this.code = status.code;
        this.message = status.message;
        this.data = data;
        this.ext = ext;
    }

    public static <T> ResponseEntity<T> ok() {
        return new ResponseEntity<>(ResponseStatus.SUCCESS);
    }

    public static <T> ResponseEntity<T> ok(T data) {
        return new ResponseEntity<>(ResponseStatus.SUCCESS, data);
    }

    public static <T> ResponseEntity<T> ok(T data, Map<String, Object> ext) {
        return new ResponseEntity<>(ResponseStatus.SUCCESS, data, ext);
    }

    public static <T> ResponseEntity<T> error() {
        return new ResponseEntity<>(ResponseStatus.ERROR);
    }

    public static <T> ResponseEntity<T> error(T data) {
        return new ResponseEntity<>(ResponseStatus.SUCCESS, data);
    }

}
