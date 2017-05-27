package com.ssm.core.dao;

import com.ssm.core.page.Page;
import org.springframework.stereotype.Repository;

@Repository("userDAO")
public class UserDAOImpl extends AbstractDaoSupport implements UserDAO {

    @Override
    public Page queryForPage(Object parameter, int offset, int length) {
        return queryForPage("user.queryForPage", parameter, offset, length);
    }

}