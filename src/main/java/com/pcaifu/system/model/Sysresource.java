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
@Table(name = "sys_resource")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Sysresource implements java.io.Serializable {

	private String id;
	private Sysresourcetype sysresourcetype;
	private Sysresource sysresource;
	private String name;
	private String remark;
	private Integer seq;
	private String url;
	private String icon;
	private Set<Sysrole> sysroles = new HashSet<Sysrole>(0);
	private Set<Sysresource> sysresources = new HashSet<Sysresource>(0);

	public Sysresource() {
	}

	public Sysresource(String id, Sysresourcetype sysresourcetype, String name) {
		this.id = id;
		this.sysresourcetype = sysresourcetype;
		this.name = name;
	}

	public Sysresource(String id, Sysresourcetype sysresourcetype, Sysresource sysresource, String name, String remark, Integer seq, String url, String icon, Set<Sysrole> sysroles, Set<Sysresource> sysresources) {
		this.id = id;
		this.sysresourcetype = sysresourcetype;
		this.sysresource = sysresource;
		this.name = name;
		this.remark = remark;
		this.seq = seq;
		this.url = url;
		this.icon = icon;
		this.sysroles = sysroles;
		this.sysresources = sysresources;
	}

	@Id
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRESOURCETYPE_ID", nullable = false)
	public Sysresourcetype getSysresourcetype() {
		return this.sysresourcetype;
	}

	public void setSysresourcetype(Sysresourcetype sysresourcetype) {
		this.sysresourcetype = sysresourcetype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID")
	public Sysresource getSysresource() {
		return this.sysresource;
	}

	public void setSysresource(Sysresource sysresource) {
		this.sysresource = sysresource;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "SEQ")
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Column(name = "URL", length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "ICON", length = 100)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_role_resource", joinColumns = { @JoinColumn(name = "TRESOURCE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TROLE_ID", nullable = false, updatable = false) })
	public Set<Sysrole> getSysroles() {
		return this.sysroles;
	}

	public void setSysroles(Set<Sysrole> sysroles) {
		this.sysroles = sysroles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sysresource")
	public Set<Sysresource> getSysresources() {
		return this.sysresources;
	}

	public void setSysresources(Set<Sysresource> sysresources) {
		this.sysresources = sysresources;
	}

}
