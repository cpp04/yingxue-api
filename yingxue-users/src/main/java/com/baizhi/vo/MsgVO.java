package com.baizhi.vo;

public class MsgVO {

    private String phone;  //用来接收手机号

    private String captcha; //接收验证码

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
