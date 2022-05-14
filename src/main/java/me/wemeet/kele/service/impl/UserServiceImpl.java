package me.wemeet.kele.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import me.wemeet.kele.common.helper.RedisHelper;
import me.wemeet.kele.common.util.KeleUtils;
import me.wemeet.kele.entity.User;
import me.wemeet.kele.mapper.UserMapper;
import me.wemeet.kele.service.CommonService;
import me.wemeet.kele.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public long countUserByEmail(String email) {
        email = new String(Base64.getDecoder().decode(email), StandardCharsets.UTF_8);
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("email", email);
        return count(wrapper);
    }

    @Override
    public long countUserByPhone(String phone) {
        phone = new String(Base64.getDecoder().decode(phone), StandardCharsets.UTF_8);
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("phone", phone);
        return count(wrapper);
    }

    @Override
    public long countUserByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("name", name);
        return count(wrapper);
    }

    @Override
    public void createUser(String email, String name, String password, String lang) {
        email = new String(Base64.getDecoder().decode(email), StandardCharsets.UTF_8);
        User user = new User();
        user.setNickName(KeleUtils.nextNickName(lang));
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        userMapper.insert(user);
    }

    @Override
    public User login(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("password", user.getPassword());
        if (user.getName() != null && !user.getName().isBlank()) {
            wrapper.eq("name", user.getName());
            return getOne(wrapper);
        }

        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            String email = new String(Base64.getDecoder().decode(user.getEmail()), StandardCharsets.UTF_8);
            wrapper.eq("email", email);
            return getOne(wrapper);
        }

        if (user.getPhone() != null && !user.getPhone().isBlank()) {
            String phone = new String(Base64.getDecoder().decode(user.getPhone()), StandardCharsets.UTF_8);
            wrapper.eq("phone", phone);
            return getOne(wrapper);
        }

        return null;
    }

    @Override
    public void updateAvatar(Long userId, String avatarUrl) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(User::getAvatarUrl, avatarUrl).eq(User::getId, userId);
        update(wrapper);
    }

    @Override
    public void updateNick(Long userId, String nikeName) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(User::getNickName, nikeName).eq(User::getId, userId);
        update(wrapper);
    }

    @Override
    public void updatePassword(Long userId, String password) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(User::getPassword, password).eq(User::getId, userId);
        update(wrapper);
    }
}
