package com.dynast.examportal.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResultData {
    private String seqId;
    private String questionId;
    private String submittedAnswerId;
}
