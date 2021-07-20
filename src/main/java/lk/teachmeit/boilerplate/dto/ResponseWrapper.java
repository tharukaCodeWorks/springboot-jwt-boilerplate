package lk.teachmeit.boilerplate.dto;

public class ResponseWrapper {
    private Object body;
    private String status;
    private String message;

    public ResponseWrapper() {
    }

    public ResponseWrapper(Object body, String status, String message) {
        this.body = body;
        this.status = status;
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
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
