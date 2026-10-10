package ru.itis.shop.user.api.dto;

public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String profileDescription;

    public UserDto() {
    }

    public UserDto(Integer id, String name, String email, String profileDescription) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileDescription = profileDescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profileDescription='" + profileDescription + '\'' +
                '}';
    }
}