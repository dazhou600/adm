package com.bgcode.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bgcode.adm.domain.Duty;
import com.bgcode.adm.domain.Menu;
import com.bgcode.adm.dao.MenuRepository;
import com.bgcode.adm.dao.UserRepository;
import com.bgcode.sys.SUser;

@Service
public class UserService {

	@Autowired
	private UserRepository userDao;

	@Autowired
	private MenuRepository menuDao;

	public SUser findByName(String name) {
		// duty = this.userDao.findByName(name).get(0);
		//Duty duty = this.userDao.findByNamePswd();
		Duty duty = this.userDao.findOne(name);
		if (duty == null) {
			return null;
		} else {
			SUser user = new SUser(duty);
			List<Menu> menus = menuDao.findByUser(name);
			user.setMenus(menus);
			return user;
		}
	}
}
