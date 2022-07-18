package com.lawencon.elearning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.elearning.dao.MahasiswaDao;
import com.lawencon.elearning.model.Mahasiswa;
import com.lawencon.model.SearchQuery;

@Service
public class MahasiswaServiceImpl extends BaseCoreService implements MahasiswaService {

	@Autowired
	private MahasiswaDao mahasiswaDao;

	@Override
	public Mahasiswa insert(Mahasiswa data) throws Exception {
		try {
			begin();
			mahasiswaDao.save(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return data;
	}

	@Override
	public Mahasiswa update(Mahasiswa data) throws Exception {
		try {
			Mahasiswa mhsDb = mahasiswaDao.findById(data.getId());
			data.setCreatedAt(mhsDb.getCreatedAt());
			data.setCreatedBy(mhsDb.getCreatedBy());

			begin();
			mahasiswaDao.save(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return data;
	}

	@Override
	public Mahasiswa findById(String id) throws Exception {
		return mahasiswaDao.findById(id);
	}

	@Override
	public SearchQuery<Mahasiswa> findAll(String query, Integer startPage, Integer maxPage) throws Exception {
		return mahasiswaDao.findAll(query, startPage, maxPage);
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		try {
			begin();
			boolean isDeleted = mahasiswaDao.deleteById(id);
			commit();

			return isDeleted;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
	}

}
