package com.hbyuan.demo.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "last_success_login")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastSuccessLogin;

	@Column(name = "last_success_logout")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastSuccessLogout;

	@Column(name = "granted_authorities")
	String grantedAuthorities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastSuccessLogin() {
		return lastSuccessLogin;
	}

	public void setLastSuccessLogin(Date lastSuccessLogin) {
		this.lastSuccessLogin = lastSuccessLogin;
	}

	public Date getLastSuccessLogout() {
		return lastSuccessLogout;
	}

	public void setLastSuccessLogout(Date lastSuccessLogout) {
		this.lastSuccessLogout = lastSuccessLogout;
	}

	public String getGrantedAuthorities() {
		return grantedAuthorities;
	}

	public void setGrantedAuthorities(String grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}

}
