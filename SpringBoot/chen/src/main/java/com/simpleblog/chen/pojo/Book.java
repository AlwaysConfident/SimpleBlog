package com.simpleblog.chen.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "book")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @ManyToOne
  @JoinColumn(name = "cid")
  private Category category;

  @Column(name = "cover")
  private String cover;

  @Column(name = "title")
  private String title;

  @Column(name = "author")
  private String author;

  @Column(name = "date")
  private String date;

  @Column(name = "press")
  private String press;

  @Column(name = "abs")
  private String abs;
}
