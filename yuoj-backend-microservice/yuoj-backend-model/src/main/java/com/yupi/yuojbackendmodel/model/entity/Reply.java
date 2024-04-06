package com.yupi.yuojbackendmodel.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName reply
 */
@TableName(value ="reply")
@Data
public class Reply implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 
     */
    private String content;

    /**
     * 0 - 审核通过, 1 - 未通过
     */
    private Integer status;

    /**
     * 
     */
    private Integer likes;

    /**
     * 
     */
    private Long questionId;

    /**
     * 
     */
    private Long commentId;

    /**
     * 0 - 非回复评论, 其他为评论 id
     */
    private Long replyId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}