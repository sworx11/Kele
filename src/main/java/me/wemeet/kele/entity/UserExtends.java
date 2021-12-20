package me.wemeet.kele.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
@Getter
@Setter
@TableName("user_extends")
public class UserExtends implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private String field;

    private String value;


}
