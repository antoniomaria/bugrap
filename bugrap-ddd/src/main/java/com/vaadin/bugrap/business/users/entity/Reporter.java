package com.vaadin.bugrap.business.users.entity;


import com.vaadin.bugrap.business.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name=Reporter.reporterByNameOrEmail,query="SELECT r FROM Reporter r WHERE r.email = :email or r.name = :name")
public class Reporter extends AbstractEntity {
        private static final String PREFIX ="com.vaadin.bugrap.business.users.entity.Reporter.";
        public static final String reporterByNameOrEmail ="reporterByNameOrEmail";

	private String name;

	private String email;

	private String password;

	private boolean admin = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return name;
	}

	public boolean equals(Reporter reporter) {
		return getId() == reporter.getId();
	}
}
