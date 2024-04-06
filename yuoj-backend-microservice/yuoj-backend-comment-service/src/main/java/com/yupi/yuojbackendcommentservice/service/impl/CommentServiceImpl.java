package com.yupi.yuojbackendcommentservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuojbackendcommentservice.mapper.CommentMapper;
import com.yupi.yuojbackendcommentservice.service.CommentService;
import com.yupi.yuojbackendcommentservice.service.ReplyService;
import com.yupi.yuojbackendcommon.constant.CommonConstant;
import com.yupi.yuojbackendcommon.utils.SqlUtils;
import com.yupi.yuojbackendmodel.model.dto.comment.CommentQueryRequest;
import com.yupi.yuojbackendmodel.model.entity.Comment;
import com.yupi.yuojbackendmodel.model.entity.Reply;
import com.yupi.yuojbackendmodel.model.vo.CommentVO;
import com.yupi.yuojbackendmodel.model.vo.ReplyVO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Touka
 * @description 针对表【comment】的数据库操作Service实现
 * @createDate 2023-11-20 00:33:41
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {

    @Resource
    private ReplyService replyService;


    public Page<CommentVO> listByQuestionId(Page<Comment> commentPage, HttpServletRequest request) {
        List<Comment> records = commentPage.getRecords();
        Page<CommentVO> commentVOPage = new Page<>(commentPage.getCurrent(), commentPage.getSize(), commentPage.getTotal());
        if (records.isEmpty()) {
            return commentVOPage;
        }

        List<CommentVO> commentVOList = new ArrayList<>();
        List<Comment> smallComments = new ArrayList<>();
        List<Comment> largeComments = new ArrayList<>();
        for (Comment comment : records) {
            if (comment.getReplies() > 10) {
                largeComments.add(comment);
            } else {
                smallComments.add(comment);
            }
        }

        for (Comment comment : largeComments) {
            Page<Reply> replyPage = replyService.page(new Page<>(0, 10),
                    new QueryWrapper<Reply>()
                            .eq(ObjectUtils.isNotEmpty(comment.getId()), "commentId", comment.getId()));
            CommentVO commentVO = CommentVO.objToVo(comment);
            List<ReplyVO> replyVOList = replyPage.getRecords()
                    .stream()
                    .map(ReplyVO::objToVo)
                    .collect(Collectors.toList());
            commentVO.setCommentReplies(replyVOList);
            commentVOList.add(commentVO);
        }

        QueryWrapper<Reply> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .in("commentId", smallComments.stream().map(Comment::getId).collect(Collectors.toList()));
        Page<Reply> smallReplyPage = replyService.page(new Page<>(0, 10L * smallComments.size()), queryWrapper);

        for (Comment comment : smallComments) {
            CommentVO commentVO = CommentVO.objToVo(comment);
            List<ReplyVO> collect = smallReplyPage.getRecords()
                    .stream()
                    .filter(reply -> reply.getCommentId().equals(commentVO.getId()))
                    .map(ReplyVO::objToVo)
                    .collect(Collectors.toList());
            commentVO.setCommentReplies(collect);
            commentVOList.add(commentVO);
        }

        commentVOPage.setRecords(commentVOList);

        return commentVOPage;
    }

    public QueryWrapper<Comment> getQueryWrapper(CommentQueryRequest commentQueryRequest) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        if (commentQueryRequest == null) {
            return null;
        }

        Long questionId = commentQueryRequest.getQuestionId();
        String sortField = commentQueryRequest.getSortField();
        String sortOrder = commentQueryRequest.getSortOrder();

        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);

        return queryWrapper;
    }

}




