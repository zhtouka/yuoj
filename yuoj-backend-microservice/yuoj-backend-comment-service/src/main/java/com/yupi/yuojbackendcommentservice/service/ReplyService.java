package com.yupi.yuojbackendcommentservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuojbackendmodel.model.dto.comment.ReplyQueryRequest;
import com.yupi.yuojbackendmodel.model.entity.Reply;

/**
* @author Touka
* @description 针对表【reply】的数据库操作Service
* @createDate 2023-11-20 00:38:34
*/
public interface ReplyService extends IService<Reply> {

    QueryWrapper<Reply> getQueryWrapper(ReplyQueryRequest replyQueryRequest);

}
