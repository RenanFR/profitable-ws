package com.profitable.ws.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_roles")
@AllArgsConstructor
@Builder
public class Role implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Getter
	@Setter
	private String role;
	
	@ManyToMany(mappedBy = "profiles")
	@JsonBackReference
	@Getter
	@Setter
	private List<Trader> users;

	public Role() {
	}

	public Role(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role;
	}

}
