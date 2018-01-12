package com.pcaifu.system.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "sys_role")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Sysrole implements java.io.Serializable {

	private String id;
	private Sysrole sysrole;
	private String name;
	private String remark;
	private Integer seq;
	private Set<Sysrole> sysroles = new HashSet<Sysrole>(0);
	private Set<Sysresource> sysresources = new HashSet<Sysresource>(0);
	private Set<SysUser> sysusers = new HashSet<SysUser>(0);

	public Sysrole() {
	}

	public Sysrole(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Sysrole(String id, Sysrole sysrole, String name, String remark, Integer seq, Set<Sysrole> sysroles, Set<Sysresource> sysresources, Set<SysUser> sysusers) {
		this.id = id;
		this.sysrole = sysrole;
		this.name = name;
		this.remark = remark;
		this.seq = seq;
		this.sysroles = sysroles;
		this.sysresources = sysresources;
		this.sysusers = sysusers;
	}

	@Id
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID")
	public Sysrole getSysrole() {
		return sysrole;
	}

	public void setSysrole(Sysrole sysrole) {
		this.sysrole = sysrole;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "SEQ")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sysrole")
	public Set<Sysrole> getSysroles() {
		return sysroles;
	}

	public void setSysroles(Set<Sysrole> sysroles) {
		this.sysroles = sysroles;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_role_resource", joinColumns = { @JoinColumn(name = "TROLE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TRESOURCE_ID", nullable = false, updatable = false) })
	public Set<Sysresource> getSysresources() {
		return sysresources;
	}

	public void setSysresources(Set<Sysresource> sysresources) {
		this.sysresources = sysresources;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "TROLE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TUSER_ID", nullable = false, updatable = false) })
	public Set<SysUser> getSysusers() {
		return sysusers;
	}

	public void setSysusers(Set<SysUser> sysusers) {
		this.sysusers = sysusers;
	}

	

}
