package com.agitation.sport.repository;

import java.util.List;

import com.agitation.sport.entity.Company;

@MyBatisRepository
public interface CompanyDao {
	
	void save(Company company);

	Company findOne(Long id);
	
	List<Company> findAll();
	
	int delete(Long id);
	
	void update(Company company);
}
