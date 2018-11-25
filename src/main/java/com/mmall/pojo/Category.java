package com.mmall.pojo;

import lombok.*;

import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class Category {
    private Integer id;

    private Integer parentId;

    private String categoryName;

    private Boolean status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;
}