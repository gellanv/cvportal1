package com.startcoding.cvportal.dto;

import com.startcoding.cvportal.models.CV;
import com.startcoding.cvportal.models.Technology;
import java.util.Set;

public class UserDto {
   // private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;

    public Long getCvId() {
        return CvId;
    }

    public void setCvId(Long cvId) {
        CvId = cvId;
    }

    private Long CvId;
    // private String photo;
    // private CV cv;
    // private Set<Technology> technologies;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    /*public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }*/
}