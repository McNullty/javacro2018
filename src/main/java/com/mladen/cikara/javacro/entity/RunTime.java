package com.mladen.cikara.javacro.entity;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "run_time")
@Data
public class RunTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "run_time_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", updatable = false)
  private User user;

  @Column(updatable = false)
  private LocalDateTime dateTime;

  private Duration runTime;

  private Float distance;
}
