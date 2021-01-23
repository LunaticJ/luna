package com.lunaticj.luna.themis.entity;

import com.lunaticj.luna.themis.constant.MatterStatusEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * matter entity
 *
 * @author kuro
 * @create 2021-01-23
 **/
@Document(collation = "matter")
public class MatterEntity {
    @Id
    private String id;
    private String content;
    private long allowFinishTime;
    private long createTime;
    private int priority;
    private Long recommendedStartTime;
    private List<WorkTime> workTimeList;
    private Long actualWorkHour;
    private Long actualFinishTime;
    private String status;
    private String exceptionStatus;
    private String finishedType;
    @Version
    private Long version;

    public MatterEntity() {
    }

    public MatterEntity(String id, String content, long allowFinishTime, int priority) {
        this.id = id;
        this.content = content;
        this.allowFinishTime = allowFinishTime;
        this.createTime = System.currentTimeMillis();
        this.priority = priority;
        this.status = MatterStatusEnum.WAITING.name();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getAllowFinishTime() {
        return allowFinishTime;
    }

    public void setAllowFinishTime(long allowFinishTime) {
        this.allowFinishTime = allowFinishTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Long getRecommendedStartTime() {
        return recommendedStartTime;
    }

    public void setRecommendedStartTime(Long recommendedStartTime) {
        this.recommendedStartTime = recommendedStartTime;
    }

    public List<WorkTime> getWorkTimeList() {
        return workTimeList;
    }

    public void setWorkTimeList(List<WorkTime> workTimeList) {
        this.workTimeList = workTimeList;
    }

    public Long getActualWorkHour() {
        return actualWorkHour;
    }

    public void setActualWorkHour(Long actualWorkHour) {
        this.actualWorkHour = actualWorkHour;
    }

    public Long getActualFinishTime() {
        return actualFinishTime;
    }

    public void setActualFinishTime(Long actualFinishTime) {
        this.actualFinishTime = actualFinishTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExceptionStatus() {
        return exceptionStatus;
    }

    public void setExceptionStatus(String exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }

    public String getFinishedType() {
        return finishedType;
    }

    public void setFinishedType(String finishedType) {
        this.finishedType = finishedType;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
