package com.biblioapp.data.service;

import com.biblioapp.data.entity.SamplePerson;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SamplePersonRepository extends JpaRepository<SamplePerson, Integer> {

}