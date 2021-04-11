package lk.teachmeit.auth.dto;

/*
 * Author: Tharuka Lakshan Dissanayake
 * Date: 2020/12/04
 */

public class UserDto {

    private String firstName;
    private String lastName;
    private String avatar;
    private String email;
    private String password;
    private long userRoleId;
    private String companyPhoneNumber;
    private long dailyTarget;
    private long teamId;
    private String gender;

    public UserDto() {
    }

    public UserDto(String firstName, String lastName, String avatar, String email, String password, long userRoleId, String companyPhoneNumber, long dailyTarget, long teamId, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.email = email;
        this.password = password;
        this.userRoleId = userRoleId;
        this.companyPhoneNumber = companyPhoneNumber;
        this.dailyTarget = dailyTarget;
        this.teamId = teamId;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getTeamId() {
        return teamId;
    }
    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public long getDailyTarget() {
        return dailyTarget;
    }

    public void setDailyTarget(long dailyTarget) {
        this.dailyTarget = dailyTarget;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
