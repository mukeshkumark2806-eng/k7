package com.railway.reservation.dto.response;

import com.railway.reservation.entity.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Passenger details response")
public class PassengerResponseDto {

    @Schema(description = "Passenger ID", example = "1")
    private Long id;

    @Schema(description = "First Name", example = "Rahul")
    private String firstName;

    @Schema(description = "Last Name", example = "Sharma")
    private String lastName;

    @Schema(description = "Email Address", example = "rahul.sharma@example.com")
    private String email;

    @Schema(description = "Phone Number", example = "9876543210")
    private String phoneNumber;

    @Schema(description = "Gender", example = "MALE")
    private Gender gender;

    @Schema(description = "Age", example = "28")
    private Integer age;

    @Schema(description = "Residential Address", example = "Sector 14, Gurgaon, Haryana")
    private String address;

    public PassengerResponseDto() {
    }

    public PassengerResponseDto(Long id, String firstName, String lastName, String email,
                                String phoneNumber, Gender gender, Integer age, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.age = age;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
