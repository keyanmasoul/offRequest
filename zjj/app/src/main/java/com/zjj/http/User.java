package com.zjj.http;

import java.io.Serializable;

/**
 * 用户信息
 * Created by zjj on 2016/3/4.
 */
public class User extends BaseBean {

    private int id;

    private String address;

    private String location;

    private String signature;

    private Integer version;

    private Boolean friend;

    private Boolean autoGenFlag;

    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRegInfoTrace() {
        return regInfoTrace;
    }

    public void setRegInfoTrace(int regInfoTrace) {
        this.regInfoTrace = regInfoTrace;
    }

    private int regInfoTrace;

    private Boolean modifiedBiuId;

    private Boolean perfectProfile;

    private String userId;

    private String biuId;

    private Integer currentPower;

    private String accessToken;

    private String createdTime;

    private int grade;

    private String secret;

    private String timToken;

    private String imToken;

    private String nickname;

    private String area;

    private String phone;

    private String email;

    private String avatar;

    private String sceneImage;

    private Integer sex;

    private Integer longitude;

    private Integer latitude;

    String memoName;

    private String biuPwd;

    public boolean isFriend() {
        return isFriend;
    }

    public void setIsFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }

    private boolean isFriend = false;

    public String getBiuPwd() {
        return biuPwd;
    }

    public void setBiuPwd(String biuPwd) {
        this.biuPwd = biuPwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getFriend() {
        return friend;
    }

    public void setFriend(Boolean friend) {
        this.friend = friend;
    }

    public void setAutoGenFlag(Boolean autoGenFlag) {
        this.autoGenFlag = autoGenFlag;
    }

    public Boolean getAutoGenFlag() {
        return autoGenFlag;
    }

    public Boolean getModifiedBiuId() {
        return modifiedBiuId;
    }

    public void setModifiedBiuId(Boolean modifiedBiuId) {
        this.modifiedBiuId = modifiedBiuId;
    }

    public Boolean getPerfectProfile() {
        return perfectProfile;
    }

    public void setPerfectProfile(Boolean perfectProfile) {
        this.perfectProfile = perfectProfile;
    }

    public String getBiuId() {
        return biuId;
    }

    public void setBiuId(String biuId) {
        this.biuId = biuId;
    }

    public Integer getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(Integer currentPower) {
        this.currentPower = currentPower;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTimToken() {
        return timToken;
    }

    public void setTimToken(String timToken) {
        this.timToken = timToken;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSceneImage() {
        return sceneImage;
    }

    public void setSceneImage(String sceneImage) {
        this.sceneImage = sceneImage;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public String getMemoName() {
        return memoName;
    }

    public void setMemoName(String memoName) {
        this.memoName = memoName;
    }
}
