package com.ssm.rpc;

public class UserServiceImpl implements UserService {

    @Override
    public String getById(String id) {
        return "User_" + id;
    }

}
