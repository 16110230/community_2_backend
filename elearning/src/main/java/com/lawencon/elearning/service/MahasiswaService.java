package com.lawencon.elearning.service;

import com.lawencon.elearning.model.Mahasiswa;
import com.lawencon.model.SearchQuery;

public interface MahasiswaService {

	Mahasiswa insert(Mahasiswa data) throws Exception;

	Mahasiswa update(Mahasiswa data) throws Exception;

	Mahasiswa findById(String id) throws Exception;

	SearchQuery<Mahasiswa> findAll(String query, Integer startPage, Integer maxPage) throws Exception;

	boolean deleteById(String id) throws Exception;

}
