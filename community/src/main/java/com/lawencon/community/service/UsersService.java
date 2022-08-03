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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.Role;
import com.lawencon.community.dao.CompanyDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.UserRoleDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.exception.InvalidLoginException;
import com.lawencon.community.model.Company;
import com.lawencon.community.model.File;
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
import com.lawencon.community.pojo.users.UpdatePasswordReq;
import com.lawencon.community.pojo.users.UpdateUserReq;
import com.lawencon.community.util.EmailComponent;
import com.lawencon.community.util.GenerateCode;
import com.lawencon.model.SearchQuery;
import com.lawencon.security.RefreshTokenEntity;
import com.lawencon.security.RefreshTokenService;
import com.lawencon.util.JwtUtil;

@Service
public class UsersService extends BaseService<Users> implements UserDetailsService {

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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private FileDao fileDao;
	
	public SearchQuery<PojoUsers> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Users> users = userDao.findAll(query, startPage, maxPage);
		List<PojoUsers> result = new ArrayList<PojoUsers>();

		users.getData().forEach(val -> {
			PojoUsers user = new PojoUsers();
			Company company = companyDao.getById(val.getCompany().getId());
			Industry industry = industryDao.getById(val.getIndustry().getId());
			Position position = positionDao.getById(val.getPosition().getId());
			
			if(val.getFile() != null) {
				user.setFile(val.getFile().getId());
			}

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
		
		if(users.getFile() != null) {
			user.setFile(users.getFile().getId());
		}

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
		insert.setUserPassword(passwordEncoder.encode(data.getUserPassword()));

		try {
			begin();
			
			Users result = userDao.saveNonLogin(insert, () -> userDao.findByRoleCode(Role.ADMIN.name()));
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
		Users users = new Users();
		Company company = companyDao.getById(data.getCompany());
		Industry industry = industryDao.getById(data.getIndustry());
		Position position = positionDao.getById(data.getPosition());
		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();
		File file = new File();
		
		// File check
		if(data.getFile() != null || data.getFileName() != null) {
			if(data.getFileName() != null) {				
				file.setFileName(data.getFileName());
				file.setFileExt(data.getFileExt());
			} else {
				file = fileDao.getById(data.getFile());
				update.setFile(file);
			}
		}
		
		// User ID check (from admin / from member)
		if(data.getId() != null) {
			users = userDao.getById(data.getId());
			update.setId(data.getId());
			update.setEmail(data.getEmail());
		} else {
			users = userDao.getById(getUserId());
			update.setId(getUserId());
			update.setEmail(users.getEmail());
		}
		
		update.setRole(users.getRole());
		update.setFullName(data.getFullName());
		update.setUsername(data.getUsername());
		update.setUserPassword(users.getUserPassword());
		update.setCompany(company);
		update.setIndustry(industry);
		update.setPosition(position);
		update.setVersion(data.getVersion());
		update.setIsActive(data.getIsActive());

		try {
			begin();
			
			if(data.getFileName() != null) {
				File fileResult = fileDao.saveNew(file);
				update.setFile(fileResult);
			}
			
			Users result = userDao.saveNew(update);
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
        
        Users res = userDao.save(user);
        String token = res.getToken().getToken();
        commit();
        return token;
    }
	
	public PojoUpdateRes changePassword(UpdatePasswordReq data) throws Exception {
		PojoUpdateRes response = new PojoUpdateRes();
		PojoUpdateResData resData = new PojoUpdateResData();
		
		Users user = userDao.getById(getUserId());
		
		try {
			if(passwordEncoder.matches(data.getOldPassword(), user.getUserPassword())) {
				user.setUserPassword(passwordEncoder.encode(data.getNewPassword()));
				
				begin();
				Users update = userDao.save(user);
				commit();
				
				resData.setMessage("Successfully update the password!");
				resData.setVersion(update.getVersion());
			} else {
				resData.setMessage("Wrong old password!");
				resData.setVersion(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		
		response.setData(resData);
		return response;
	}
	
	public ShowUserById showById() {
		Users users = userDao.getById(getUserId());
		PojoUsers user = new PojoUsers();

		Company company = companyDao.getById(users.getCompany().getId());
		Industry industry = industryDao.getById(users.getIndustry().getId());
		Position position = positionDao.getById(users.getPosition().getId());
		
		if(users.getFile() != null) {
			user.setFile(users.getFile().getId());
		}

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
}
