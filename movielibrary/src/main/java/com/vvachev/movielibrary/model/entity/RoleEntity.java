package com.vvachev.movielibrary.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.vvachev.movielibrary.model.entity.enums.RoleEnum;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RoleEnum role;

	public RoleEntity() {
	}

	public RoleEntity(RoleEnum role) {
		this.role = role;
	}
}
