package com.aviobrief.springserver.models.service_models;

import java.util.ArrayList;
import java.util.List;

public class OrganizationServiceModel {

    private String name;
    private List<UserServiceModel> members = new ArrayList<>();

    public OrganizationServiceModel() {
    }

    public String getName() {
        return name;
    }

    public OrganizationServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<UserServiceModel> getMembers() {
        return members;
    }

    public OrganizationServiceModel setMembers(List<UserServiceModel> members) {
        this.members = members;
        return this;
    }
}
