package com.project.railwayreservation.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class Passenger {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    private Long age;

    @NotBlank
    private String gender;

    @NotBlank
    private String email;

    @NotBlank
    private String mobile;

    @NotBlank
    private String berth;

    public Passenger() {
    }

    public Passenger(String name, Long age, String gender, String email, String mobile, String berth) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.mobile = mobile;
        this.berth = berth;
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

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBerth() {
        return berth;
    }

    public void setBerth(String berth) {
        this.berth = berth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger)) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(getId(), passenger.getId()) && Objects.equals(getName(), passenger.getName()) && Objects.equals(getAge(), passenger.getAge()) && Objects.equals(getGender(), passenger.getGender()) && Objects.equals(getEmail(), passenger.getEmail()) && Objects.equals(getMobile(), passenger.getMobile()) && Objects.equals(getBerth(), passenger.getBerth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAge(), getGender(), getEmail(), getMobile(), getBerth());
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", berth='" + berth + '\'' +
                '}';
    }
}
