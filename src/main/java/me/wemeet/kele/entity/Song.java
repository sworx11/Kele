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
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long  id;

    private String mid;

    private String source;

    private Long changeBy;

    private LocalDateTime changeDate;


}
