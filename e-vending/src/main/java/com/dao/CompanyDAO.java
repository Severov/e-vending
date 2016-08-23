package com.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Company;
import com.model.Modul;

public interface CompanyDAO {

    @Transactional(readOnly = true)
    Company getCompany(Integer id);
}
