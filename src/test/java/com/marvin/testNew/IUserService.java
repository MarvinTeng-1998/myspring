package com.marvin.testNew;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-03 18:36
 **/
@Data
@Getter
@Setter
public class IUserService {
    private String uId;
    private String company;
    private String location;
    private IUserDao iUserDao;

    public String queryUserInfo(){
        return iUserDao.queryUserName(uId) + "," + company + "," + location;
    }
}
