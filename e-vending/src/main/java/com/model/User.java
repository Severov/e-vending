package com.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {

	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long				id;

	@Column(name = "root_user")
	private Integer				root_user;

	@Column(name = "username")
	private String				username;

	@Column(name = "fullname")
	private String				fullname;

	@Column(name = "password")
	private String				password;

	@Column(name = "enabled")
	private boolean				enabled;

	@Column(name = "email")
	private String				email;

	@Column(name = "accountNonExpired")
	private boolean				accountNonExpired;

	@Column(name = "accountNonLocked")
	private boolean				accountNonLocked;

	@Column(name = "credentialsNonExpired")
	private boolean				credentialsNonExpired;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id")
	@JsonIgnore
	private Company				company;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "users")
	@JsonIgnore
	private Set<Role>			userRole			= new HashSet<>();

	public User() {
	}

	public User(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User(String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired) {
		this.username = username;
		this.password = password;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public User(String username, String password, boolean enabled, Set<Role> userRole) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getUserRole() {
		return this.userRole;
	}

	public void setUserRole(Set<Role> userRole) {
		this.userRole = userRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setRoot_user(Integer root_user) {
		this.root_user = root_user;
	}

	public Integer getRoot_user() {
		return root_user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		for (Role role : this.userRole) {
			setAuths.add(new SimpleGrantedAuthority(role.getRole()));
		}

		return setAuths;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

}
