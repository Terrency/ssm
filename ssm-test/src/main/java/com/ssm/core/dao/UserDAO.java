package com.ssm.core.dao;

import com.ssm.core.page.Page;

public interface UserDAO {

    Page queryForPage(Object parameter, int offset, int length);

}