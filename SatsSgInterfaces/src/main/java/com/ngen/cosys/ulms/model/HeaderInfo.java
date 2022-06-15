package com.ngen.cosys.ulms.model;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.ulms.constant.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HeaderInfo extends BaseBO {
    private String messageId;
    private String clientId;
    private String accessToken;
    private String userName;
    private String userPassword;
    private ErrorCode errorCodeDesc;
}
