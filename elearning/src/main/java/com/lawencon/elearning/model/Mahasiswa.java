package com.lawencon.elearning.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import com.lawencon.base.BaseEntity;

@Entity
@Indexed
@Table(uniqueConstraints = {
		@UniqueConstraint(
				name = "mhs_ck",
				columnNames = {"nama", "univ_id"}
		)
})
public class Mahasiswa extends BaseEntity {

	private static final long serialVersionUID = -5196455701225322056L;

	@FullTextField
	private String nama;

	@ManyToOne
	@JoinColumn(name = "univ_id")
	@IndexedEmbedded
	private Universitas universitas;

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public Universitas getUniversitas() {
		return universitas;
	}

	public void setUniversitas(Universitas universitas) {
		this.universitas = universitas;
	}

}
