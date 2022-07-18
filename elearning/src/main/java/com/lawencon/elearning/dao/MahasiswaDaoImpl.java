package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.elearning.model.Mahasiswa;
import com.lawencon.model.SearchQuery;

@Repository
public class MahasiswaDaoImpl extends AbstractJpaDao<Mahasiswa> implements MahasiswaDao {

	@Override
	public Mahasiswa save(Mahasiswa data) throws Exception {
		return super.save(data);
	}

	@Override
	public Mahasiswa findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public SearchQuery<Mahasiswa> findAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Mahasiswa> sq = new SearchQuery<>();
		List<Mahasiswa> data = null;

		if (startPage == null || maxPage == null) {
			data = getAll();
			sq.setData(data);
		} else {
			if (query == null) {
				data = getAll(startPage, maxPage);
				int count = countAll().intValue();

				sq.setData(data);
				sq.setCount(count);
			} else {
				return super.getAll(query, startPage, maxPage, "nama", "universitas.nama");
			}
		}

		return sq;
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		return super.deleteById(id);
	}

}
