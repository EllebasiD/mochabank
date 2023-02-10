package com.anotherbank.mochabank.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.anotherbank.mochabank.domain.model.Counter;

public interface CounterRepository extends CrudRepository<Counter, String> {

}
