package me.wemeet.kele.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.wemeet.kele.entity.Admin;
import me.wemeet.kele.mapper.AdminMapper;
import me.wemeet.kele.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Quino Wu
 * @since 2022-04-30
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Override
    public Admin login(Admin admin) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>().eq("password", admin.getPassword());
        if (admin.getName() != null && !admin.getName().isBlank()) {
            wrapper.eq("name", admin.getName());
            return getOne(wrapper);
        }

        if (admin.getEmail() != null && !admin.getEmail().isBlank()) {
            String email = new String(Base64.getDecoder().decode(admin.getEmail()), StandardCharsets.UTF_8);
            wrapper.eq("email", email);
            return getOne(wrapper);
        }

        if (admin.getPhone() != null && !admin.getPhone().isBlank()) {
            String phone = new String(Base64.getDecoder().decode(admin.getPhone()), StandardCharsets.UTF_8);
            wrapper.eq("phone", phone);
            return getOne(wrapper);
        }

        return null;
    }
}
