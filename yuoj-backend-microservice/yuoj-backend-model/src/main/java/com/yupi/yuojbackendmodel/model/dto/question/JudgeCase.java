package com.yupi.yuojbackendmodel.model.dto.question;


import lombok.Data;

import java.io.Serializable;

@Data
public class JudgeCase implements Serializable {

    private String input;

    private String output;
}
