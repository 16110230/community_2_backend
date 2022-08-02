package com.lawencon.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.community.dao.FileDao;
import com.lawencon.community.model.File;

@Service
public class FileService extends BaseService<File> {

	@Autowired
	private FileDao fileDao;

	public File showById(String id) throws Exception {
		File file = fileDao.getById(id);
		return file;
	}
}
