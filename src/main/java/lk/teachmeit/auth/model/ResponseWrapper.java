package lk.teachmeit.auth.model;

import java.util.List;

public class ResponseWrapper<T> {
    private List<T> body;
    private String status;
    private String message;

    public ResponseWrapper() {
    }

    public ResponseWrapper(List<T> body, String status, String message) {
        this.body = body;
        this.status = status;
        this.message = message;
    }

    public List<T> getBody() {
        return body;
    }

    public void setBody(List<T> body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
