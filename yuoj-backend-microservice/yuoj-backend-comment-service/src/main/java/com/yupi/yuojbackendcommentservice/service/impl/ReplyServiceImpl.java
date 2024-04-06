package com.yupi.yuojbackendcommentservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuojbackendcommentservice.mapper.ReplyMapper;
import com.yupi.yuojbackendcommentservice.service.ReplyService;
import com.yupi.yuojbackendcommon.constant.CommonConstant;
import com.yupi.yuojbackendcommon.utils.SqlUtils;
import com.yupi.yuojbackendmodel.model.dto.comment.ReplyQueryRequest;
import com.yupi.yuojbackendmodel.model.entity.Reply;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
* @author Touka
* @description 针对表【reply】的数据库操作Service实现
* @createDate 2023-11-20 00:38:34
*/
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply>
    implements ReplyService {

    @Override
    public QueryWrapper<Reply> getQueryWrapper(ReplyQueryRequest replyQueryRequest) {
        Long commentId = replyQueryRequest.getCommentId();
        String sortField = replyQueryRequest.getSortField();
        String sortOrder = replyQueryRequest.getSortOrder();

        QueryWrapper<Reply> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(ObjectUtils.isNotEmpty(commentId), "commentId", commentId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);

        return queryWrapper;
    }
}




