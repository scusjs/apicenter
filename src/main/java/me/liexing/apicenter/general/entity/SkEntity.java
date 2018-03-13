package me.liexing.apicenter.general.entity;

import java.io.Serializable;
import java.util.Date;

public class SkEntity implements Serializable {
    private String skcode;
    private String url;
    private String name;
    private String phone;
    private String email;
    private Boolean valid;
    private Date created_time;

    public String getSkcode() {
        return skcode;
    }

    public void setSkcode(String skcode) {
        this.skcode = skcode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }
}
