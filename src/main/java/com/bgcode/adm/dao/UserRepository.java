package com.bgcode.adm.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bgcode.adm.domain.Duty;

@Repository
public interface UserRepository extends CrudRepository<Duty,String>{

}
