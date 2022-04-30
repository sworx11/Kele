package me.wemeet.kele.service;

import me.wemeet.kele.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Quino Wu
 * @since 2022-04-30
 */
public interface IAdminService extends IService<Admin> {
    Admin login(Admin user);
}
