package ru.spring.library.models;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "books")
public class Book {

	private static final int DATES_FOR_RENT = 10;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Title must not be empty")
	@Column(name = "title", nullable = false)
	private String title;

	@NotEmpty(message = "Author must not be empty")
	@Column(name = "author", nullable = false)
	private String author;

	@NotNull
	@Column(name = "published_at", nullable = false)
	private Integer publishedAt;

	@Column(name = "rented_at")
	private LocalDate rentedAt;

	@Transient
	private Boolean expired;

	public Book(Long id, String title, String author, Integer published_at) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publishedAt = published_at;
	}

	public LocalDate getRentedAt() {
		return rentedAt;
	}

	public void setRentedAt(LocalDate rentedAt) {
		this.rentedAt = rentedAt;
	}

	public Boolean getExpired() {
		if (Objects.isNull(rentedAt)) {
			return false;
		}

		return LocalDate.now().minusDays(DATES_FOR_RENT).isAfter(rentedAt);
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	public Book() {
	}

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
