package com.yupi.yuojbackendcommentservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuojbackendcommentservice.service.CommentService;
import com.yupi.yuojbackendcommentservice.service.ReplyService;
import com.yupi.yuojbackendcommon.common.BaseResponse;
import com.yupi.yuojbackendcommon.common.ErrorCode;
import com.yupi.yuojbackendcommon.common.ResultUtils;
import com.yupi.yuojbackendcommon.exception.BusinessException;
import com.yupi.yuojbackendcommon.exception.ThrowUtils;
import com.yupi.yuojbackendmodel.model.dto.comment.CommentAddRequest;
import com.yupi.yuojbackendmodel.model.dto.comment.CommentQueryRequest;
import com.yupi.yuojbackendmodel.model.dto.comment.ReplyQueryRequest;
import com.yupi.yuojbackendmodel.model.entity.Comment;
import com.yupi.yuojbackendmodel.model.entity.Reply;
import com.yupi.yuojbackendmodel.model.entity.User;
import com.yupi.yuojbackendmodel.model.vo.CommentVO;
import com.yupi.yuojbackendmodel.model.vo.ReplyVO;
import com.yupi.yuojbackendserviceclient.service.UserFeignClient;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private UserFeignClient userService;

    @Resource
    private ReplyService replyService;

    @PostMapping("/list/page")
    public BaseResponse<Page<CommentVO>> getCommentsByQuestionId(@RequestBody CommentQueryRequest commentQueryRequest, HttpServletRequest request) {
        long current = commentQueryRequest.getCurrent();
        long size = commentQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Comment> commentPage = commentService.page(new Page<>(current, size), commentService.getQueryWrapper(commentQueryRequest));
        return ResultUtils.success(commentService.listByQuestionId(commentPage, request));
    }

    @PostMapping("/add")
    public BaseResponse<Long> addComment(@RequestBody CommentAddRequest commentAddRequest, HttpServletRequest request) {
        if (commentAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        User loginUser = userService.getLoginUser(request);

        Long replyId = commentAddRequest.getReplyId();
        if (replyId != null) {
            Reply reply = new Reply();
            BeanUtils.copyProperties(commentAddRequest, reply);
            reply.setUserId(loginUser.getId());

            boolean result = replyService.save(reply);
            ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
            Long id = reply.getId();
            return ResultUtils.success(id);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentAddRequest, comment);
        comment.setUserId(loginUser.getId());

        boolean result = commentService.save(comment);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        Long id = comment.getId();
        return ResultUtils.success(id);
    }

    @PostMapping("/list/page/reply")
    public BaseResponse<Page<ReplyVO>> getRepliesByQuestionId(@RequestBody ReplyQueryRequest replyQueryRequest, HttpServletRequest request) {
        long current = replyQueryRequest.getCurrent();
        long size = replyQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Reply> replyPage = replyService.page(new Page<>(current, size), replyService.getQueryWrapper(replyQueryRequest));
        List<ReplyVO> replyVOList = replyPage.getRecords().stream().map(ReplyVO::objToVo).collect(Collectors.toList());
        Page<ReplyVO> replyVOPage = new Page<>(current, size);
        replyVOPage.setRecords(replyVOList);

        return ResultUtils.success(replyVOPage);
    }
}
