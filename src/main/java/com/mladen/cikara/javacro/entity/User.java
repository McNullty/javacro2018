package com.mladen.cikara.javacro.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "appuser")
public class User {

  private static final long serialVersionUID = -6543038038488379924L;
  private static final Logger logger = LoggerFactory.getLogger(User.class);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  private String firstName;

  private String lastName;

  @Column(updatable = false)
  private String username;

  private String password;

  public User() {
  }

  public String getFirstName() {
    return firstName;
  }

  public Long getId() {
    return id;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return "User [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
        + ", password=" + password + "]";
  }
}
