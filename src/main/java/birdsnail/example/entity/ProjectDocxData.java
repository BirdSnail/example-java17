package birdsnail.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDocxData {

    private String companyName;

    private String planningDate;

    private String planningCompany;

    /**
     * 可申领补贴数量
     */
    private String projectRecommendSize;

    private List<ProjectTable> projectTables;

    private String policySize;

    private String policyMoney;

    /**
     * 已申领政策补贴
     */
    private List<PolicyListVO> policyList;

    private List<PeerSummaryTable> peerSummary;

    private PeerProjectSubsidyInfo peerProjectSubsidyList;


    /**
     * 申报项目列表
     */
    @Data
    public static class ProjectTable {

        private String projectYear;

        private List<ProjectListVO> projectList;
    }

    /**
     * 申报项目列表vo
     */
    @Data
    public static class ProjectListVO {

        private Integer num;

        private String projectName;

        private String good;

        private String subsidyMoney;

        private String suitList;

        private String applyTimeStr;
    }

    @Data
    public static class PolicyListVO {

        private Integer num;

        private String projectName;

        /**
         * 级别
         */
        private String grade;

        private String department;

        private String publicDate;

        private String subsidyMoney;
    }

    @Data
    public static  class PeerSummaryTable{

        private String enterpriseName;

        private String description;
    }

}
