package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.UserRoleDao;
import com.lawencon.community.model.UserRole;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.userrole.InsertUserRoleReq;
import com.lawencon.community.pojo.userrole.PojoUserRole;
import com.lawencon.community.pojo.userrole.ShowUserRoleById;
import com.lawencon.community.pojo.userrole.UpdateUserRoleReq;
import com.lawencon.model.SearchQuery;

@Service
public class UserRoleService extends BaseService<UserRole> {
	@Autowired
	private UserRoleDao roleDao;

	public SearchQuery<PojoUserRole> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<UserRole> roles = roleDao.findAll(query, startPage, maxPage);
		List<PojoUserRole> result = new ArrayList<PojoUserRole>();

		roles.getData().forEach(val -> {
			PojoUserRole role = new PojoUserRole();
			role.setId(val.getId());
			role.setRoleName(val.getRoleName());
			role.setRoleCode(val.getRoleCode());
			role.setVersion(val.getVersion());
			role.setIsActive(val.getIsActive());

			result.add(role);
		});

		SearchQuery<PojoUserRole> response = new SearchQuery<PojoUserRole>();
		response.setCount(roles.getCount());
		response.setData(result);

		return response;
	}

	public ShowUserRoleById showById(String id) throws Exception {
		UserRole role = roleDao.getById(id);
		ShowUserRoleById response = new ShowUserRoleById();
		PojoUserRole result = new PojoUserRole();

		result.setId(role.getId());
		result.setRoleName(role.getRoleName());
		result.setRoleCode(role.getRoleCode());
		result.setVersion(role.getVersion());
		result.setIsActive(role.getIsActive());

		response.setData(result);

		return response;
	}

	public PojoInsertRes insert(InsertUserRoleReq data) throws Exception {
		PojoInsertRes response = new PojoInsertRes();
		PojoInsertResData resData = new PojoInsertResData();
		UserRole insert = new UserRole();

		insert.setRoleName(data.getRoleName());
		insert.setRoleCode(data.getRoleCode());
		insert.setIsActive(data.getIsActive());

		try {
			begin();
			UserRole result = roleDao.saveNew(insert);
			commit();

			resData.setId(result.getId());
			resData.setMessage("Successfully insert new data!");
			response.setData(resData);
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return response;
	}

	public PojoUpdateRes update(UpdateUserRoleReq data) throws Exception {
		PojoUpdateRes response = new PojoUpdateRes();
		PojoUpdateResData resData = new PojoUpdateResData();
		UserRole update = roleDao.getById(data.getId());

		update.setId(data.getId());
		update.setRoleName(data.getRoleName());
		update.setRoleCode(data.getRoleCode());
		update.setVersion(data.getVersion());
		update.setIsActive(data.getIsActive());

		try {
			begin();
			UserRole result = roleDao.saveNew(update);
			commit();

			resData.setVersion(result.getVersion());
			resData.setMessage("Successfully update the data!");
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
			boolean result = roleDao.deleteById(id);
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
