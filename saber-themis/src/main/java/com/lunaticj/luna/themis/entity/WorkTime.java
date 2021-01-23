package com.lunaticj.luna.themis.entity;

/**
 * work time
 *
 * @author kuro
 * @create 2021-01-23
 **/
public class WorkTime {
    private Long startTime;
    private Long endTime;

    public WorkTime() {
    }

    public WorkTime(Long startTime, Long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
