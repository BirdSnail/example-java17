package birdsnail.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class PeerProjectSubsidyInfo {

    private List<ProjectSubsidyItem> areaProjectList;

    private List<ProjectSubsidyItem> cityProjectList;

    private List<ProjectSubsidyItem> provProjectList;

    private List<ProjectSubsidyItem> countryProjectList;

    @Data
    public static class ProjectSubsidyItem{

        private String projectName;

        private List<SubsidyItem> subsidyItems;

    }

    @Data
    public static class SubsidyItem{

        private Integer count;

        private Double subsidyMoney;

    }

}
