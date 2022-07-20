package birdsnail.example.算法.动态规划;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会议信息
 */
@Getter
@AllArgsConstructor
public class MeetingInfo {

    /**
     * 会议名称
     */
    private String name;

    /**
     * 会议开始时间
     */
    private long startTime;

    /**
     * 会议结束时间
     */
    private long endTime;

}
