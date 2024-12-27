package com.twesche.model;

import java.io.Serializable;

public class EGRGenerateStatus implements Serializable {
    private EGRTypeData dataSource;
    private boolean stepSuccess;
    private String message;

    public EGRTypeData getDataSource() {
        return dataSource;
    }

    public void setDataSource(EGRTypeData dataSource) {
        this.dataSource = dataSource;
    }

    public boolean isStepSuccess() {
        return stepSuccess;
    }

    public void setStepSuccess(boolean stepSuccess) {
        this.stepSuccess = stepSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EGRGenerateStatus{" +
                "dataSource=" + dataSource +
                ", stepSuccess=" + stepSuccess +
                ", message='" + message + '\'' +
                '}';
    }
}
