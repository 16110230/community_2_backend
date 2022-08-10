package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.CompanyDao;
import com.lawencon.community.model.Company;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.company.InsertCompanyReq;
import com.lawencon.community.pojo.company.PojoCompany;
import com.lawencon.community.pojo.company.ShowCompanyById;
import com.lawencon.community.pojo.company.UpdateCompanyReq;
import com.lawencon.model.SearchQuery;

@Service
public class CompanyService extends BaseCoreService<Company> {
	@Autowired
	private CompanyDao companyDao;

	public SearchQuery<PojoCompany> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Company> companies = companyDao.findAll(query, startPage, maxPage);
		List<PojoCompany> result = new ArrayList<PojoCompany>();

		companies.getData().forEach(val -> {
			PojoCompany data = new PojoCompany();
			data.setId(val.getId());
			data.setCompanyName(val.getCompanyName());
			data.setCompanyCode(val.getCompanyCode());
			data.setEmail(val.getEmail());
			data.setAddress(val.getAddress());
			data.setVersion(val.getVersion());
			data.setIsActive(val.getIsActive());

			result.add(data);
		});

		SearchQuery<PojoCompany> response = new SearchQuery<PojoCompany>();
		response.setData(result);
		response.setCount(companies.getCount());

		return response;
	}

	public ShowCompanyById showById(String id) throws Exception {
		ShowCompanyById response = new ShowCompanyById();
		PojoCompany result = new PojoCompany();
		Company company = companyDao.getById(id);

		result.setId(company.getId());
		result.setCompanyName(company.getCompanyName());
		result.setCompanyCode(company.getCompanyCode());
		result.setEmail(company.getEmail());
		result.setAddress(company.getAddress());
		result.setVersion(company.getVersion());
		result.setIsActive(company.getIsActive());

		response.setData(result);

		return response;
	}

	public PojoInsertRes insert(InsertCompanyReq data) throws Exception {
		PojoInsertRes response = new PojoInsertRes();
		PojoInsertResData resData = new PojoInsertResData();
		Company insert = new Company();

		insert.setCompanyName(data.getCompanyName());
		insert.setCompanyCode(data.getCompanyCode());
		insert.setAddress(data.getAddress());
		insert.setEmail(data.getEmail());
		insert.setIsActive(data.getIsActive());

		try {
			begin();
			Company result = companyDao.saveNew(insert);
			commit();

			resData.setId(result.getId());
			response.setMessage("Successfully add new data!");
			response.setData(resData);
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return response;
	}

	public PojoUpdateRes update(UpdateCompanyReq data) throws Exception {
		PojoUpdateRes response = new PojoUpdateRes();
		PojoUpdateResData resData = new PojoUpdateResData();
		Company update = companyDao.getById(data.getId());

		update.setCompanyName(data.getCompanyName());
		update.setCompanyCode(data.getCompanyCode());
		update.setEmail(data.getEmail());
		update.setAddress(data.getAddress());
		update.setVersion(data.getVersion());
		update.setIsActive(data.getIsActive());

		try {
			begin();
			Company result = companyDao.saveNew(update);
			commit();

			resData.setVersion(result.getVersion());
			response.setMessage("Successfully update the data!");
			response.setData(resData);
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return response;
	}

	public PojoDeleteRes delete(String id) throws Exception {
		PojoDeleteRes response = new PojoDeleteRes();

		try {
			begin();
			boolean result = companyDao.deleteById(id);
			commit();

			if (result) {
				response.setMessage("Successfully delete the data!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return response;
	}
}
