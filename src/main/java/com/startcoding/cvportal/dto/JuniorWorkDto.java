package com.startcoding.cvportal.dto;

import java.util.*;

public class JuniorWorkDto {
    private Long id;
    private String name;
    private String description;
    private String link;
    private List<String> technologies = new ArrayList<>();

    public JuniorWorkDto() {
    }

    public JuniorWorkDto(Long id, String name, String description, String link, List<String> technologies) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.link = link;
        this.technologies = technologies;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }
}
