package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author demo
 * @since 2023-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Securities对象", description="")
public class Securities implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "证券名")
    private String name;

    @ApiModelProperty(value = "优先级别")
    @TableField("securitiesClass")
    private Integer securitiesclass;

    @ApiModelProperty(value = "分类名")
    @TableField("securitiesType")
    private Integer securitiestype;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "备注")
    private String remark;


}
