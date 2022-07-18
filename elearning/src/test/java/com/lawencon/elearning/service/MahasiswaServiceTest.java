package com.lawencon.elearning.service;

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

import com.lawencon.elearning.model.Mahasiswa;
import com.lawencon.model.SearchQuery;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
public class MahasiswaServiceTest {

	@Autowired
	private MahasiswaService mahasiswaService;

	private String idInserted = null;

	@Test
	@Order(1)
	public void insert() throws Exception {
		Mahasiswa mhs = new Mahasiswa();
		mhs.setNama("Doe");
		mahasiswaService.insert(mhs);

		idInserted = mhs.getId();

		assertNotNull(mhs.getId());
	}

	@Test
	@Order(2)
	public void update() throws Exception {
		Mahasiswa mhs = mahasiswaService.findById(idInserted);
		mhs.setNama("Doe Edited");
		mahasiswaService.update(mhs);

		Mahasiswa mhsCheck = mahasiswaService.findById(idInserted);

		assertEquals(1, mhsCheck.getVersion());
		assertEquals("Doe Edited", mhsCheck.getNama());
	}
	
	@Test
	@Order(3)
	public void findAll() throws Exception {
		SearchQuery<Mahasiswa> mhs = mahasiswaService.findAll("doe", 0, 10);
		assertEquals(1, mhs.getData().size());
	}

	@Test
	@Order(4)
	public void delete() throws Exception {
		boolean isDeleted = mahasiswaService.deleteById(idInserted);
		assertTrue(isDeleted);
	}
}
