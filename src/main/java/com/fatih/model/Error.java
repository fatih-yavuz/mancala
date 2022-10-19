package com.fatih.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Error {
    private long timestamp = System.currentTimeMillis();

    @NonNull
    private String exception;
    private List<ErrorDetail> errors = new ArrayList<>();

    public void addErrorDetail(final ErrorDetail errorDetail) {
        this.errors.add(errorDetail);
    }

    public String toString() {
        return (new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE))
                .append("timestamp", this.timestamp)
                .append("exception", this.exception)
                .append("errors", this.errors)
                .toString();
    }
}
