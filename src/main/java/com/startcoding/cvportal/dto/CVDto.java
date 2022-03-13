package com.startcoding.cvportal.dto;

import com.startcoding.cvportal.models.*;

import java.util.*;

public class CVDto {
    private Long id;
    private String education;
    private String courses;
    private String experience;
    private String aboutPerson;
    private Map<Long, String> works=new HashMap<>();
    private List<String> technologies = new ArrayList<>();

    public CVDto(Long id, String education, String courses, String experience, String aboutPerson, Map<Long, String> works, List<String> technologies) {
        this.id = id;
        this.education = education;
        this.courses = courses;
        this.experience = experience;
        this.aboutPerson = aboutPerson;
        this.works = works;
        this.technologies = technologies;
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

    public Map<Long, String> getWorks() {
        return works;
    }

    public void setWorks(Map<Long, String> works) {
        this.works = works;
    }

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }
}
