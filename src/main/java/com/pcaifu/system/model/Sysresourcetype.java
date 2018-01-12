package com.pcaifu.system.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "sys_resourcetype")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Sysresourcetype implements java.io.Serializable {

	private String id;
	private String name;
	private Set<Sysresource> sysresources = new HashSet<Sysresource>(0);

	public Sysresourcetype() {
	}

	public Sysresourcetype(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Sysresourcetype(String id, String name, Set<Sysresource> tresources) {
		this.id = id;
		this.name = name;
		this.sysresources = tresources;
	}

	@Id
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sysresourcetype")
	public Set<Sysresource> getSysresources() {
		return this.sysresources;
	}

	public void setSysresources(Set<Sysresource> sysresources) {
		this.sysresources = sysresources;
	}

}
