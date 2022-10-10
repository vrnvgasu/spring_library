package ru.spring.library.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class User {

  private Long id;

  @NotEmpty(message = "Full name must not be empty")
  @Pattern(regexp = "[a-zA-ZА-Яа-яЁё]+ [a-zA-ZА-Яа-яЁё]+ [a-zA-ZА-Яа-яЁё]+",
      message = "FullName must be like Ivan Ivanovich Ivanov")
  private String fullName;

  @Min(value = 1901, message = "Birthday must be greater than 1900")
  private Integer birthday;

  public User(Long id, String fullName, Integer birthday) {
    this.id = id;
    this.fullName = fullName;
    this.birthday = birthday;
  }

  public User() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Integer getBirthday() {
    return birthday;
  }

  public void setBirthday(Integer birthday) {
    this.birthday = birthday;
  }

}
