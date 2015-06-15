package com.reylibutan.tabularasa.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reylibutan.tabularasa.dao.PersonDAO;
import com.reylibutan.tabularasa.entity.Person;
import com.reylibutan.tabularasa.service.PersonService;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonDAO personDAO;
	
	@Override
	public List<Person> findAll() {
		return personDAO.findAll();
	}
}