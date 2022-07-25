package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.model.Position;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoDeleteResData;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.position.InsertPositionReq;
import com.lawencon.community.pojo.position.PojoPosition;
import com.lawencon.community.pojo.position.ShowPositionById;
import com.lawencon.community.pojo.position.UpdatePositionReq;
import com.lawencon.model.SearchQuery;

@Service
public class PositionService extends BaseCoreService<Position> {
	@Autowired
	private PositionDao positionDao;
	
	public SearchQuery<PojoPosition> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Position> positions = positionDao.findAll(query, startPage, maxPage);
		List<PojoPosition> result = new ArrayList<PojoPosition>();
		
		positions.getData().forEach(val -> {
			PojoPosition data = new PojoPosition();
			
			data.setId(val.getId());
			data.setPositionName(val.getPositionName());
			data.setPositionCode(val.getPositionCode());
			data.setIsActive(val.getIsActive());
			data.setVersion(val.getVersion());
			
			result.add(data);
		});
		
		SearchQuery<PojoPosition> response = new SearchQuery<PojoPosition>();
		response.setData(result);
		response.setCount(positions.getCount());
		
		return response;
	}
	
	public ShowPositionById showById(String id) throws Exception {
		ShowPositionById response = new ShowPositionById();
		PojoPosition result = new PojoPosition();
		Position position = positionDao.getById(id);
		
		result.setId(position.getId());
		result.setPositionName(position.getPositionName());
		result.setPositionCode(position.getPositionCode());
		result.setIsActive(position.getIsActive());
		result.setVersion(position.getVersion());
		
		response.setData(result);
		
		return response;
	}
	
	public PojoInsertRes insert(InsertPositionReq data) throws Exception {
		PojoInsertRes response = new PojoInsertRes();
		PojoInsertResData resData = new PojoInsertResData();
		Position insert = new Position();
		
		insert.setPositionName(data.getPositionName());
		insert.setPositionCode(data.getPositionCode());
		insert.setIsActive(data.getIsActive());
		
		try {			
			begin();
			Position result = save(insert);
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
	
	public PojoUpdateRes update(UpdatePositionReq data) throws Exception {
		PojoUpdateRes response = new PojoUpdateRes();
		PojoUpdateResData resData = new PojoUpdateResData();
		Position update = new Position();
		
		update.setId(data.getId());
		update.setPositionName(data.getPositionName());
		update.setPositionCode(data.getPositionCode());
		update.setIsActive(data.getIsActive());
		update.setVersion(data.getVersion());
		
		try {
			begin();
			Position result = save(update);
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
		PojoDeleteResData resData = new PojoDeleteResData();
		
		try {
			begin();
			boolean result = positionDao.deleteById(id);
			commit();
			
			if(result) {				
				resData.setMessage("Successfully delete the data!");
				response.setData(resData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		
		return response;
	}
}
