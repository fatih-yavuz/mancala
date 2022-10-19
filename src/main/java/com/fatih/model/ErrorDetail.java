package com.fatih.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class ErrorDetail {

    private String key;
    private String message;
    private String errorCode;
    private String[] args = ArrayUtils.EMPTY_STRING_ARRAY;

    public String toString() {
        return (new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE))
                .append("key", this.key)
                .append("message", this.message)
                .append("errorCode", this.errorCode)
                .append("args", this.args)
                .toString();
    }


}
