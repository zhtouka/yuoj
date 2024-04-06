package com.yupi.yuojbackendmodel.model.vo;

import com.yupi.yuojbackendmodel.model.entity.Comment;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @TableName comment
 */
@Data
public class CommentVO implements Serializable {
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
    private Integer replies;

    /**
     *
     */
    private Long questionId;

    /**
     * 创建时间
     */
    private Date createTime;

    private List<ReplyVO> commentReplies;


    private static final long serialVersionUID = 1L;

    public static CommentVO objToVo(Comment comment) {

        CommentVO commentVO = new CommentVO();
        commentVO.setId(comment.getId());
        commentVO.setUserId(comment.getUserId());
        commentVO.setUserName(comment.getUserName());
        commentVO.setUserAvatar(comment.getUserAvatar());
        commentVO.setContent(comment.getContent());
        commentVO.setLikes(comment.getLikes());
        commentVO.setReplies(comment.getReplies());
        commentVO.setQuestionId(comment.getQuestionId());
        commentVO.setCreateTime(comment.getCreateTime());

        return commentVO;
    }
}