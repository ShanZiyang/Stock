package com.ys.stock.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/8/27 8:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredictRequestDomain {
    private String stockName;
    private String day;
//    private String way;
}
