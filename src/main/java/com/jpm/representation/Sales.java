package com.jpm.representation;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Created by rahulranjan on 27/08/2018.
 * This file has been created to temporarily store the sales details at the time
 * of processing the message.
 */
public class Sales {

    private String productType;
    private BigDecimal value;
    private Long count;
    private Action action;

    public Sales() {
    }

    private Sales(Builder builder) {
        setProductType(builder.productType);
        setValue(builder.value);
        setCount(builder.count);
        setAction(builder.action);
    }

    public enum Action {
        ADD, SUBTRACT, MULTIPLY
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value.setScale(2, ROUND_HALF_UP);
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public static final class Builder {
        private String productType;
        private BigDecimal value;
        private Long count;
        private Action action;

        public Builder() {
        }

        public Builder withProductType(String val) {
            productType = val;
            return this;
        }

        public Builder withValue(BigDecimal val) {
            value = val;
            return this;
        }

        public Builder withCount(Long val) {
            count = val;
            return this;
        }

        public Builder withAction(Action val) {
            action = val;
            return this;
        }

        public Sales build() {
            return new Sales(this);
        }
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
