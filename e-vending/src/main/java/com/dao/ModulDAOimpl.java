/*
 * Copyright 2015 e-vending.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dao;

import com.model.Modul;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

/**
 *
 * @author mishka
 */
@Service("modulDAOimpl")
public class ModulDAOimpl extends HibernateDaoSupport implements ModuleDAO {

	@Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
	
	@Autowired
	public void init() {
	    setSessionFactory(sessionFactory);
	}

    public List<Modul> getAllModul() {
    	return (List<Modul>) getHibernateTemplate().find("from Modul");
    }

}
