package me.wemeet.kele.service.impl;

import me.wemeet.kele.entity.User;
import me.wemeet.kele.mapper.UserMapper;
import me.wemeet.kele.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
