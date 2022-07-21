package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.SubscriptionCategoryDao;
import com.lawencon.community.model.SubscriptionCategory;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoDeleteResData;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.subscategory.InsertSubsCategoryReq;
import com.lawencon.community.pojo.subscategory.PojoSubscriptionCategory;
import com.lawencon.community.pojo.subscategory.ShowSubsCategoryById;
import com.lawencon.community.pojo.subscategory.UpdateSubsCategoryReq;
import com.lawencon.model.SearchQuery;

@Service
public class SubsCategoryService extends BaseCoreService<SubscriptionCategory> {
	@Autowired
	private SubscriptionCategoryDao subsCategoryDao;
	
	public SearchQuery<PojoSubscriptionCategory> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<SubscriptionCategory> categories = subsCategoryDao.findAll(query, startPage, maxPage);
		List<PojoSubscriptionCategory> result = new ArrayList<PojoSubscriptionCategory>();
		
		categories.getData().forEach(val -> {
			PojoSubscriptionCategory data = new PojoSubscriptionCategory();
			data.setId(val.getId());
			data.setDescription(val.getDescription());
			data.setPrice(val.getPrice());
			data.setIsActive(val.getIsActive());
			data.setVersion(val.getVersion());
			
			result.add(data);
		});
		
		SearchQuery<PojoSubscriptionCategory> response = new SearchQuery<PojoSubscriptionCategory>();
		response.setData(result);
		response.setCount(categories.getCount());
		
		return response;
	}
	
	public ShowSubsCategoryById showById(String id) throws Exception {
		ShowSubsCategoryById response = new ShowSubsCategoryById();
		SubscriptionCategory category = subsCategoryDao.getById(id);
		PojoSubscriptionCategory result = new PojoSubscriptionCategory();
		
		result.setId(category.getId());
		result.setDescription(category.getDescription());
		result.setPrice(category.getPrice());
		result.setIsActive(category.getIsActive());
		result.setVersion(category.getVersion());
		
		response.setData(result);
		
		return response;
	}
	
	public PojoInsertRes insert(InsertSubsCategoryReq data) throws Exception {
		PojoInsertRes response = new PojoInsertRes();
		PojoInsertResData resData = new PojoInsertResData();
		SubscriptionCategory insert = new SubscriptionCategory();
		
		insert.setDescription(data.getDescription());
		insert.setPrice(data.getPrice());
		insert.setIsActive(data.getIsActive());
		
		try {
			begin();
			SubscriptionCategory result = save(insert);
			commit();
			
			resData.setId(result.getId());
			resData.setMessage("Successfully add new data!");
			response.setData(resData);
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		
		return response;
	}
	
	public PojoUpdateRes update(UpdateSubsCategoryReq data) throws Exception {
		PojoUpdateRes response = new PojoUpdateRes();
		PojoUpdateResData resData = new PojoUpdateResData();
		SubscriptionCategory update = new SubscriptionCategory();
		
		update.setId(data.getId());
		update.setDescription(data.getDescription());
		update.setPrice(data.getPrice());
		update.setIsActive(data.getIsActive());
		update.setVersion(data.getVersion());
		
		try {
			begin();
			SubscriptionCategory result = save(update);
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
		PojoDeleteResData resData = new PojoDeleteResData();
		PojoDeleteRes response = new PojoDeleteRes();
		
		try {
			begin();
			boolean result = subsCategoryDao.deleteById(id);
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
