package com.bgcode.adm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bgcode.adm.domain.Duty;
import com.bgcode.adm.domain.Menu;
import com.bgcode.adm.dao.MenuRepository;
import com.bgcode.adm.dao.UserRepository;
import com.bgcode.sys.SUser;

@Service
public class SuserService {

	@Autowired
	private UserRepository userDao;

	@Autowired
	private MenuRepository menuDao;
	
	public Duty regst(Duty user)throws RegisterException {
		if(this.userDao.findOne(user.getId())==null){
			user.setLastTime(new Date());
			user.setCreatedate(new Date());
			user.setStater(1);
			user.setAccountnonlocked(1);
			//user.setId(user.getName());
			//user.setName(null);
			//String passwd = null ;
			user.setPassword(new StandardPasswordEncoder("wztq").encode(user.getPassword()));
			userDao.save(user);
		}else{
			throw new RegisterException("用户名已经存在!");
		}

		
		return user;
		
	}

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
