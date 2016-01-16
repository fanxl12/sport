package com.agitation.sport.service.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agitation.sport.entity.Company;
import com.agitation.sport.repository.CompanyDao;

//Spring Bean的标识.
@Component
public class CompanyService {

	@Autowired
	private CompanyDao companyDao;

	public Company getCompany(Long id) {
		return companyDao.findOne(id);
	}
	
	public List<Company> getAllCompanys(){
		return companyDao.findAll();
	}
	
	@Transactional(readOnly = false)
	public void saveCompany(Company entity) {
		companyDao.save(entity);
	}
	

	@Transactional(readOnly = false)
	public void deleteCompany(Long id) {
		companyDao.delete(id);
	}

	public void updateCompany(Company entity) {
		companyDao.update(entity);
	}

}
