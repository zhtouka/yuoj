package com.yupi.yuojbackendmodel.model.dto.comment;


import lombok.Data;

import java.io.Serializable;

@Data
public class CommentAddRequest implements Serializable {

    private Long questionId;

    private String content;

    private Long commentId;

    private Long replyId;

}
