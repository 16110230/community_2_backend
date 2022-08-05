package com.lawencon.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.community.pojo.PojoVerificationReq;
import com.lawencon.community.pojo.PojoVerificationRes;
import com.lawencon.util.VerificationCodeUtil;

@Service
public class VerificationService {
	@Autowired
	private VerificationCodeUtil verificationUtil;
	
	public PojoVerificationRes register(PojoVerificationReq data) throws Exception {
		PojoVerificationRes response = new PojoVerificationRes();
        response.setMessage("Matched!");

        verificationUtil.validateVerificationCode(data.getEmail(), data.getCode());

        return response;
    }
}
