package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.model.Industry;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.industry.InsertIndustryReq;
import com.lawencon.community.pojo.industry.PojoIndustry;
import com.lawencon.community.pojo.industry.ShowIndustryById;
import com.lawencon.community.pojo.industry.UpdateIndustryReq;
import com.lawencon.model.SearchQuery;

@Service
public class IndustryService extends BaseCoreService<Industry> {
	@Autowired
	private IndustryDao industryDao;

	public SearchQuery<PojoIndustry> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Industry> industries = industryDao.findAll(query, startPage, maxPage);
		List<PojoIndustry> result = new ArrayList<PojoIndustry>();

		industries.getData().forEach(val -> {
			PojoIndustry data = new PojoIndustry();
			data.setId(val.getId());
			data.setIndustryName(val.getIndustryName());
			data.setIndustryCode(val.getIndustryCode());
			data.setIsActive(val.getIsActive());
			data.setVersion(val.getVersion());

			result.add(data);
		});

		SearchQuery<PojoIndustry> response = new SearchQuery<PojoIndustry>();
		response.setData(result);
		response.setCount(industries.getCount());

		return response;
	}

	public ShowIndustryById showById(String id) throws Exception {
		ShowIndustryById response = new ShowIndustryById();
		Industry industry = industryDao.getById(id);
		PojoIndustry result = new PojoIndustry();

		result.setId(industry.getId());
		result.setIndustryName(industry.getIndustryName());
		result.setIndustryCode(industry.getIndustryCode());
		result.setIsActive(industry.getIsActive());
		result.setVersion(industry.getVersion());

		response.setData(result);

		return response;
	}

	public PojoInsertRes insert(InsertIndustryReq data) throws Exception {
		PojoInsertRes response = new PojoInsertRes();
		PojoInsertResData resData = new PojoInsertResData();
		Industry insert = new Industry();

		insert.setIndustryName(data.getIndustryName());
		insert.setIndustryCode(data.getIndustryCode());
		insert.setIsActive(data.getIsActive());

		try {
			begin();
			
			Industry result = save(insert);
			resData.setId(result.getId());
			resData.setMessage("Successfully add new data!");
			response.setData(resData);
			
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return response;
	}

	public PojoUpdateRes update(UpdateIndustryReq data) throws Exception {
		PojoUpdateRes response = new PojoUpdateRes();
		PojoUpdateResData resData = new PojoUpdateResData();
		Industry update = new Industry();

		update.setId(data.getId());
		update.setIndustryName(data.getIndustryName());
		update.setIndustryCode(data.getIndustryCode());
		update.setIsActive(data.getIsActive());
		update.setVersion(data.getVersion());

		try {
			begin();
			Industry result = save(update);
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
			boolean result = industryDao.deleteById(id);
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
