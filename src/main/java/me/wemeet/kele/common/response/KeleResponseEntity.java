package me.wemeet.kele.common.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class KeleResponseEntity<T> implements Serializable {
    private int code;
    private String message;
    private T data;
    private Map<String, Object> ext;

    public KeleResponseEntity() {
        super();
    }

    public KeleResponseEntity(KeleResponseStatus status) {
        super();
        this.code = status.code;
        this.message = status.message;
    }

    public KeleResponseEntity(KeleResponseStatus status, T data) {
        super();
        this.code = status.code;
        this.message = status.message;
        this.data = data;
    }

    public KeleResponseEntity(KeleResponseStatus status, T data, Map<String, Object> ext) {
        super();
        this.code = status.code;
        this.message = status.message;
        this.data = data;
        this.ext = ext;
    }

    public static <T> KeleResponseEntity<T> ok() {
        return new KeleResponseEntity<>(KeleResponseStatus.SUCCESS);
    }

    public static <T> KeleResponseEntity<T> ok(T data) {
        return new KeleResponseEntity<>(KeleResponseStatus.SUCCESS, data);
    }

    public static <T> KeleResponseEntity<T> ok(T data, Map<String, Object> ext) {
        return new KeleResponseEntity<>(KeleResponseStatus.SUCCESS, data, ext);
    }

    public static <T> KeleResponseEntity<T> error() {
        return new KeleResponseEntity<>(KeleResponseStatus.ERROR);
    }

    public static <T> KeleResponseEntity<T> error(T data) {
        return new KeleResponseEntity<>(KeleResponseStatus.SUCCESS, data);
    }

}
