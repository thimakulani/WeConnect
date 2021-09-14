package com.st.weconnect.model;

public class AppUser {
    private String name;
    private String lastName;
    private String email;
    private String Id;
    private String img_url;



    public AppUser(String name, String lastName, String email, String id, String img_url) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.Id = id;
        this.img_url = img_url;
    }
    public AppUser() {
    }
    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
