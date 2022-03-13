package com.startcoding.cvportal.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USER_TECHNOLOGY",
            joinColumns = @JoinColumn(name = "TECHNOLOGY_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    private Set<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "COURS_TECHNOLOGY",
            joinColumns = @JoinColumn(name = "TECHNOLOGY_ID"),
            inverseJoinColumns = @JoinColumn(name = "COURS_ID")
    )
    private Set<Course> courses;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "CV_TECHNOLOGY",
            joinColumns = @JoinColumn(name = "TECHNOLOGY_ID"),
            inverseJoinColumns = @JoinColumn(name = "CV_ID")
    )
    private Set<CV> cvs;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "INTERNSHIP_TECHNOLOGY",
            joinColumns = @JoinColumn(name = "TECHNOLOGY_ID"),
            inverseJoinColumns = @JoinColumn(name = "INTERNSHIP_ID")
    )
    private Set<Internship> internships;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "JUNIORWORK_TECHNOLOGY",
            joinColumns = @JoinColumn(name = "TECHNOLOGY_ID"),
            inverseJoinColumns = @JoinColumn(name = "JUNIORWORK_ID")
    )
    private Set<JuniorWork> juniorWorks;

    public Technology() {
    }

    public Technology(String name) {
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<CV> getCvs() {
        return cvs;
    }

    public void setCvs(Set<CV> cvs) {
        this.cvs = cvs;
    }

    public Set<Internship> getInternships() {
        return internships;
    }

    public void setInternships(Set<Internship> internships) {
        this.internships = internships;
    }

    public Set<JuniorWork> getJuniorWorks() {
        return juniorWorks;
    }

    public void setJuniorWorks(Set<JuniorWork> juniorWorks) {
        this.juniorWorks = juniorWorks;
    }
}
