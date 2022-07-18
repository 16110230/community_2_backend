package com.lawencon.elearning.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import com.lawencon.base.BaseEntity;

@Entity
public class Universitas extends BaseEntity {

	private static final long serialVersionUID = -9034083546988015483L;

	@FullTextField
	private String nama;

	@OneToMany(mappedBy = "universitas")
	private Set<Mahasiswa> mahasiswa = new HashSet<>();

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public Set<Mahasiswa> getMahasiswa() {
		return mahasiswa;
	}

	public void setMahasiswa(Set<Mahasiswa> mahasiswa) {
		this.mahasiswa = mahasiswa;
	}

}
