package ru.spring.library.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Book {

  private Long id;

  @NotEmpty(message = "Title must not be empty")
  private String title;

  @NotEmpty(message = "Author must not be empty")
  private String author;

  @NotNull
  private Integer publishedAt;

  public Book(Long id, String title, String author, Integer published_at) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.publishedAt = published_at;
  }

  public Book() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Integer getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(Integer publishedAt) {
    this.publishedAt = publishedAt;
  }

}
