package me.wemeet.kele.service;

import me.wemeet.kele.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
public interface IUserService extends IService<User> {
    long countUserByEmail(String email);
    long countUserByPhone(String phone);
    void createUser(String email, String name, String password, String lang);
    User login(User user);
}
