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

    }

    public KeleResponseEntity(int code, String message, T data, Map<String, Object> ext) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
        this.ext = ext;
    }

    public static <T> Builder<T> builder() {
        return new KeleResponseEntity.Builder<>();
    }

    public static class Builder<T> {
        private int code;
        private String message;
        private T data;
        private Map<String, Object> ext;

        Builder() {

        }

        public Builder<T> ok() {
            this.code = KeleResponseStatus.SUCCESS.code;
            this.message = KeleResponseStatus.SUCCESS.message;
            return this;
        }

        public Builder<T> ok(T data) {
            this.code = KeleResponseStatus.SUCCESS.code;
            this.message = KeleResponseStatus.SUCCESS.message;
            this.data = data;
            return this;
        }

        public Builder<T> ext(Map<String, Object> ext) {
            this.ext = ext;
            return this;
        }

        public Builder<T> error() {
            this.code = KeleResponseStatus.ERROR.code;
            this.message = KeleResponseStatus.ERROR.message;
            return this;
        }

        public Builder<T> error(T data) {
            this.code = KeleResponseStatus.ERROR.code;
            this.message = KeleResponseStatus.ERROR.message;
            this.data = data;
            return this;
        }

        public Builder<T> status(KeleResponseStatus status) {
            this.code = status.code;
            this.message = status.message;
            return this;
        }

        public Builder<T> entity(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> code(int code) {
            this.code = code;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public KeleResponseEntity<T> build() {
            return new KeleResponseEntity<>(this.code, this.message, this.data, this.ext);
        }
    }

}
