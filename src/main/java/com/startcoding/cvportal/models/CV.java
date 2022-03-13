package com.startcoding.cvportal.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")

public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String education;
    private String courses;
    private String experience;
    private String aboutPerson;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cv")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cv") //EAGER
    private Set<Work> works;

    @ManyToMany(mappedBy = "cvs")
    private Set<Technology> technologies;

    public CV(){}
    public CV(String education, String courses, String experience, String aboutPerson){
        this.education = education;
        this.courses = courses;
        this.experience = experience;
        this.aboutPerson = aboutPerson;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAboutPerson() {
        return aboutPerson;
    }

    public void setAboutPerson(String aboutPerson) {
        this.aboutPerson = aboutPerson;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Work> getWorks() {
        return works;
    }

    public void setWorks(Set<Work> works) {
        this.works = works;
    }

    public Set<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<Technology> technologies) {
        this.technologies = technologies;
    }
}
