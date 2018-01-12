package com.pcaifu.system.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "sys_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SysUser implements java.io.Serializable {

	private String id;
	private Date createdatetime;
	private Date modifydatetime;
	private String name;
	private String pwd;
	private Set<Sysrole> sysroles = new HashSet<Sysrole>(0);

	public SysUser() {
	}

	public SysUser(String id, String name, String pwd) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
	}

	public SysUser(String id, Date createdatetime, Date modifydatetime, String name, String pwd, Set<Sysrole> sysroles) {
		this.id = id;
		this.createdatetime = createdatetime;
		this.modifydatetime = modifydatetime;
		this.name = name;
		this.pwd = pwd;
		this.sysroles = sysroles;
	}

	@Id
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME", length = 19)
	public Date getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYDATETIME", length = 19)
	public Date getModifydatetime() {
		return this.modifydatetime;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

	@Column(name = "NAME", unique = true, nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PWD", nullable = false, length = 100)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "TUSER_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TROLE_ID", nullable = false, updatable = false) })
	public Set<Sysrole> getSysroles() {
		return this.sysroles;
	}

	public void setSysroles(Set<Sysrole> sysroles) {
		this.sysroles = sysroles;
	}

}
