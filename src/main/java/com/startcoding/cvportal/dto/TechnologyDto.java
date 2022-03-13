package com.startcoding.cvportal.dto;
import java.util.*;

public class TechnologyDto {
    private Long id=0l;
    private String name;
    private Map<Long, String> juniorWorks=new HashMap<>();
    private Map<Long, String> internships=new HashMap<>();
    private Map<Long, String> courses=new HashMap<>();
    private Map<Long, String> cvs=new HashMap<>();
    private Map<Long, String> users=new HashMap<>();

    public TechnologyDto(Long id, String name, Map<Long,String> users, Map<Long,String> cvs, Map<Long,String> courses, Map<Long,String> internships, Map<Long,String> juniorWorks) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.cvs = cvs;
        this.courses = courses;
        this.internships = internships;
        this.juniorWorks = juniorWorks;
    }

    public  TechnologyDto(){}
    public TechnologyDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, String> getJuniorWorks() {
        return juniorWorks;
    }

    public void setJuniorWorks(Map<Long, String> juniorWorks) {
        this.juniorWorks = juniorWorks;
    }

    public Map<Long, String> getInternships() {
        return internships;
    }

    public void setInternships(Map<Long, String> internships) {
        this.internships = internships;
    }

    public Map<Long, String> getCourses() {
        return courses;
    }

    public void setCourses(Map<Long, String> courses) {
        this.courses = courses;
    }

    public Map<Long, String> getCvs() {
        return cvs;
    }

    public void setCvs(Map<Long, String> cvs) {
        this.cvs = cvs;
    }

    public Map<Long, String> getUsers() {
        return users;
    }

    public void setUsers(Map<Long, String> users) {
        this.users = users;
    }
}
