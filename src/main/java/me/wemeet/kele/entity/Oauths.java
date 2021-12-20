package me.wemeet.kele.entity;

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
public class Oauths implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private String oauthType;

    private String oauthId;

    private String unionId;

    private String credential;


}
