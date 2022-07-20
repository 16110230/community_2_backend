package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.CompanyDao;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.model.Company;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoDeleteResData;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.users.InsertUserReq;
import com.lawencon.community.pojo.users.PojoUsers;
import com.lawencon.community.pojo.users.ShowUserById;
import com.lawencon.community.pojo.users.ShowUsers;
import com.lawencon.community.pojo.users.UpdateUserReq;

public class UsersService extends BaseCoreService {
	
	@Autowired
	private UsersDao userDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private PositionDao positionDao;
	
	@Autowired
	private IndustryDao industryDao;
	
	public ShowUsers showAll() {
		List<Users> users = userDao.getAll();
		List<PojoUsers> result = new ArrayList<PojoUsers>();
		
		users.forEach(val -> {
			PojoUsers user = new PojoUsers();
			Company company = companyDao.getById(val.getCompany().getId());
			Industry industry = industryDao.getById(val.getIndustry().getId());
			Position position = positionDao.getById(val.getPosition().getId());
			
			user.setId(val.getId());
			user.setFullName(val.getFullName());
			user.setEmail(val.getEmail());
			user.setUsername(val.getUsername());
			user.setIsActive(val.getIsActive());
			user.setVersion(val.getVersion());
			user.setCompany(company.getId());
			user.setCompanyName(company.getCompanyName());
			user.setIndustryName(industry.getId());
			user.setIndustryName(industry.getIndustryName());
			user.setPosition(position.getId());
			user.setPositionName(position.getPositionName());
			
			result.add(user);
		});
		
		ShowUsers response = new ShowUsers();
		response.setData(result);
		
		return response;
	}
	
	public ShowUserById showById(String id) {
		Users users = userDao.getById(id);
		PojoUsers user = new PojoUsers();
		
		Company company = companyDao.getById(users.getCompany().getId());
		Industry industry = industryDao.getById(users.getIndustry().getId());
		Position position = positionDao.getById(users.getPosition().getId());
		
		user.setId(users.getId());
		user.setFullName(users.getFullName());
		user.setEmail(users.getEmail());
		user.setUsername(users.getUsername());
		user.setIsActive(users.getIsActive());
		user.setVersion(users.getVersion());
		user.setCompany(company.getId());
		user.setCompanyName(company.getCompanyName());
		user.setIndustryName(industry.getId());
		user.setIndustryName(industry.getIndustryName());
		user.setPosition(position.getId());
		user.setPositionName(position.getPositionName());
		
		ShowUserById response = new ShowUserById();
		response.setData(user);
		
		return response;
	}
	
	public PojoInsertRes insert(InsertUserReq data) throws Exception {
		Users insert = new Users();
		Company company = companyDao.getById(data.getCompany());
		Industry industry = industryDao.getById(data.getIndustry());
		Position position = positionDao.getById(data.getPosition());
		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();
		
		insert.setFullName(data.getFullName());
		insert.setUsername(data.getUsername());
		insert.setEmail(data.getFullName());
		insert.setVerificationCode(data.getVerificationCode());
		insert.setCompany(company);
		insert.setIndustry(industry);
		insert.setPosition(position);
		
		try {
			begin();
			
			Users result = userDao.save(insert);
			resData.setId(result.getId());
			resData.setMessage("Successfully insert new data!");
			response.setData(resData);
			
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		
		return response;
	}
	
	public PojoUpdateRes update(UpdateUserReq data) throws Exception {
		Users update = new Users();
		Company company = companyDao.getById(data.getCompany());
		Industry industry = industryDao.getById(data.getIndustry());
		Position position = positionDao.getById(data.getPosition());
		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();
		
		update.setId(data.getId());
		update.setFullName(data.getFullName());
		update.setUsername(data.getUsername());
		update.setCompany(company);
		update.setIndustry(industry);
		update.setPosition(position);
		update.setVersion(data.getVersion());
		update.setIsActive(data.getIsActive());
		
		try {
			begin();
			
			Users result = userDao.save(update);
			resData.setVersion(result.getVersion());
			resData.setMessage("Successfully update the data!");
			response.setData(resData);
			
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		
		return response;
	}
	
	public PojoDeleteRes delete(String id) throws Exception {
		PojoDeleteResData resData = new PojoDeleteResData();
		PojoDeleteRes response = new PojoDeleteRes();
		
		try {
			begin();
			boolean result = userDao.deleteById(id);
			commit();
			
			if(result) {				
				resData.setMessage("Successfully delete the data!");
				response.setData(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		
		return response;
	}
}
