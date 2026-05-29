package com.study.app.domains.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.commons.EncryptionUtils;
import com.study.app.domains.users.UsersDAO;
import com.study.app.domains.users.UsersDTO;

@Service
public class AuthService {

	@Autowired
	private UsersDAO usersDao;

	public int login(AuthDTO dto) {
		UsersDTO user = usersDao.login(dto.getId());
		String getShaPw = EncryptionUtils.getSha512(dto.getPw());
		dto.setPw(getShaPw);

		if(user == null || !user.getPw().equals(getShaPw)) {
			return 0;
		}

		if(!user.getStatus().equals("ACTIVE")) {
			return -1;
		}

		return 1;
	}
}
