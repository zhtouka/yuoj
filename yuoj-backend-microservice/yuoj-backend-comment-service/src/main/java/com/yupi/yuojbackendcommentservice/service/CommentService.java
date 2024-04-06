package com.yupi.yuojbackendcommentservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuojbackendmodel.model.dto.comment.CommentQueryRequest;
import com.yupi.yuojbackendmodel.model.entity.Comment;
import com.yupi.yuojbackendmodel.model.vo.CommentVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Touka
* @description 针对表【comment】的数据库操作Service
* @createDate 2023-11-20 00:33:41
*/
public interface CommentService extends IService<Comment> {

    Page<CommentVO> listByQuestionId(Page<Comment> commentPage, HttpServletRequest request);

    QueryWrapper<Comment> getQueryWrapper(CommentQueryRequest commentQueryRequest);
}
