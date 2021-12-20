package me.wemeet.kele.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class Source implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String icon;

    private Integer weight;

    private String cookie;

    private String active;

    private String changeBy;

    private LocalDateTime changeDate;


}
