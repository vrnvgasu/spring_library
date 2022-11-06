package ru.spring.library.models;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Full name must not be empty")
	@Pattern(regexp = "[a-zA-ZА-Яа-яЁё]+ [a-zA-ZА-Яа-яЁё]+ [a-zA-ZА-Яа-яЁё]+",
			message = "FullName must be like Ivan Ivanovich Ivanov")
	@Column(name = "full_name", nullable = false)
	private String fullName;

	@Min(value = 1901, message = "Birthday must be greater than 1900")
	@Column(name = "birthday", nullable = false)
	private Integer birthday;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Set<Book> books;

	public User(Long id, String fullName, Integer birthday) {
		this.id = id;
		this.fullName = fullName;
		this.birthday = birthday;
	}

	public User() {
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
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
