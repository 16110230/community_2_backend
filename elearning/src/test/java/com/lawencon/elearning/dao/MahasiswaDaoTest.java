package com.lawencon.elearning.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.lawencon.base.ConnHandler;
import com.lawencon.elearning.model.Mahasiswa;
import com.lawencon.model.SearchQuery;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
public class MahasiswaDaoTest {

	@Autowired
	private MahasiswaDao mhsDao;

	private String idInserted = null;

	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();

		Mahasiswa mhs = new Mahasiswa();
		mhs.setNama("John");
		mhsDao.save(mhs);

		ConnHandler.commit();

		idInserted = mhs.getId();

		assertNotNull(mhs.getId());
	}

	@Test
	@Order(2)
	public void update() throws Exception {
		Mahasiswa mhs = mhsDao.findById(idInserted);

		ConnHandler.begin();
		mhs.setNama("John Edited");
		mhsDao.save(mhs);
		ConnHandler.commit();

		Mahasiswa mhsCheck = mhsDao.findById(idInserted);

		assertEquals(1, mhsCheck.getVersion());
		assertEquals("John Edited", mhsCheck.getNama());
	}

	@Test
	@Order(3)
	public void findAll() throws Exception {
		SearchQuery<Mahasiswa> mhs = mhsDao.findAll("John", 0, 10);
		assertEquals(1, mhs.getData().size());
	}

	@Test
	@Order(4)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = mhsDao.deleteById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
