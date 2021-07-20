package lk.teachmeit.boilerplate.dto;

public class ConfirmEmailDto {
    private String email;

    private String verifyCode;

    public ConfirmEmailDto() {
    }

    public ConfirmEmailDto(String email, String verifyCode) {
        this.email = email;
        this.verifyCode = verifyCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
