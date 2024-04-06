package com.yupi.yuojbackendmodel.model.vo;

import com.yupi.yuojbackendmodel.model.entity.Reply;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName reply
 */
@Data
public class ReplyVO implements Serializable {
    /**
     *
     */
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


    private static final long serialVersionUID = 1L;

    public static ReplyVO objToVo(Reply reply) {

        ReplyVO replyVO = new ReplyVO();
        replyVO.setId(reply.getId());
        replyVO.setUserId(reply.getUserId());
        replyVO.setUserName(reply.getUserName());
        replyVO.setUserAvatar(reply.getUserAvatar());
        replyVO.setContent(reply.getContent());
        replyVO.setLikes(reply.getLikes());
        replyVO.setQuestionId(reply.getQuestionId());
        replyVO.setCreateTime(reply.getCreateTime());
        replyVO.setCommentId(reply.getCommentId());
        replyVO.setReplyId(reply.getReplyId());

        return replyVO;
    }
}