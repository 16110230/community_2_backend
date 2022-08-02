package com.lawencon.security;

import org.springframework.stereotype.Service;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;

@Service
public class RefreshTokenService extends AbstractJpaDao<RefreshTokenEntity> {
	public RefreshTokenEntity saveToken(RefreshTokenEntity data) throws Exception {
		try {
			data = saveNew(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}

		return data;
	}

	public RefreshTokenEntity getByIdRefreshToken(String id) throws Exception {
		return ConnHandler.getManager().find(RefreshTokenEntity.class, id);
	}

	public void validateRefreshToken(String refreshToken) {
		String sql = "SELECT r FROM RefreshTokenEntity "
				+ "	WHERE token = :refreshToken AND expiredDate > current_timestamp() ";

		RefreshTokenEntity result = null;
		try {
			result = ConnHandler.getManager().createQuery(sql, RefreshTokenEntity.class)
					.setParameter("refreshToken", refreshToken)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (result == null) {
				throw new InvalidTokenException("Invalid Refresh Token");
			}
		}
	}
}

class InvalidTokenException extends RuntimeException {

	private static final long serialVersionUID = 7566807062941977397L;

	public InvalidTokenException() {
		super();
	}

	public InvalidTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTokenException(String message) {
		super(message);
	}

	public InvalidTokenException(Throwable cause) {
		super(cause);
	}

}

