package com.yupi.yuojbackendmodel.model.dto.comment;


import com.yupi.yuojbackendcommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReplyQueryRequest extends PageRequest implements Serializable {

    private Long commentId;

}
