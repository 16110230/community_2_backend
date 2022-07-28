package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.Role;
import com.lawencon.community.dao.CompanyDao;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.UserRoleDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.exception.InvalidLoginException;
import com.lawencon.community.model.Company;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.UserRole;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoEmailReq;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.PojoVerificationCode;
import com.lawencon.community.pojo.users.InsertUserReq;
import com.lawencon.community.pojo.users.PojoUsers;
import com.lawencon.community.pojo.users.ShowUserById;
import com.lawencon.community.pojo.users.UpdateUserReq;
import com.lawencon.community.util.EmailComponent;
import com.lawencon.community.util.GenerateCode;
import com.lawencon.model.SearchQuery;
import com.lawencon.security.RefreshTokenEntity;
import com.lawencon.security.RefreshTokenService;
import com.lawencon.util.JwtUtil;

@Service
public class UsersService extends BaseCoreService<Users> implements UserDetailsService {

	@Autowired
	private UsersDao userDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private PositionDao positionDao;

	@Autowired
	private IndustryDao industryDao;

	@Autowired
	private UserRoleDao roleDao;
	
	@Autowired
	private EmailComponent emailComponent;

	@Autowired
	private GenerateCode generateCode;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private RefreshTokenService tokenService;
	
	public SearchQuery<PojoUsers> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Users> users = userDao.findAll(query, startPage, maxPage);
		List<PojoUsers> result = new ArrayList<PojoUsers>();

		users.getData().forEach(val -> {
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
			user.setIndustry(industry.getId());
			user.setIndustryName(industry.getIndustryName());
			user.setPosition(position.getId());
			user.setPositionName(position.getPositionName());

			result.add(user);
		});

		SearchQuery<PojoUsers> response = new SearchQuery<PojoUsers>();
		response.setData(result);
		response.setCount(users.getCount());

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
		user.setIndustry(industry.getId());
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
		UserRole role = roleDao.getIdByRoleCode(Role.MEMBER.name());
		Position position = positionDao.getById(data.getPosition());
		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();
		

		insert.setFullName(data.getFullName());
		insert.setUsername(data.getUsername());
		insert.setEmail(data.getEmail());
		insert.setVerificationCode(data.getVerificationCode());
		insert.setCompany(company);
		insert.setIndustry(industry);
		insert.setPosition(position);
		insert.setRole(role);
		insert.setUserPassword(data.getUserPassword());

		try {
			begin();
			
			Users result = saveNonLogin(insert, () -> userDao.findByRoleCode(Role.ADMIN.name()));
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

			Users result = save(update);
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
		PojoDeleteRes response = new PojoDeleteRes();

		try {
			begin();
			boolean result = userDao.deleteById(id);
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

	public Users login(String username) throws Exception {
		Users user = userDao.findByUsernameAndPassword(username);

		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = new Users();

		try {
			user = userDao.findByUsernameAndPassword(username);
			if (user == null) {
				throw new InvalidLoginException(username);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new User(username, user.getUserPassword(), new ArrayList<>());
	}

	public PojoVerificationCode sendVerificationCode(PojoEmailReq email) throws Exception {
		String code = generateCode.generate();
		PojoVerificationCode response = new PojoVerificationCode();
		Map<String, Object> template = new HashMap<String, Object>();
		template.put("code", code);

		new Thread(() -> {
			try {
				emailComponent.sendMessageUsingFreemarkerTemplate(email.getEmail(), "Your sign-up verification code!",
						template);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		response.setMessage(code);
		return response;
	}
	
	public String updateToken(String id) throws Exception {
        Users user = userDao.getById(id);

        RefreshTokenEntity refreshToken = jwtUtil.generateRefreshToken();
        if(user.getToken() != null) {                        
            RefreshTokenEntity token = ConnHandler.getManager().find(RefreshTokenEntity.class, user.getToken().getId());
            token.setToken(refreshToken.getToken());
            token.setExpiredDate(refreshToken.getExpiredDate());
            begin();
            tokenService.saveToken(token);
        } else {
            begin();
            RefreshTokenEntity tokenNew = tokenService.saveToken(refreshToken);
            user.setToken(tokenNew);
        }
        
        Users res = save(user);
        String token = res.getToken().getToken();
        commit();
        return token;
    }
}
