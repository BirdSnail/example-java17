package birdsnail.example.excel;

import birdsnail.example.entity.PeerProjectSubsidyInfo;
import birdsnail.example.entity.ProjectDocxData;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopColumnTableRenderPolicy;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PoiTlDemo {

    public static void main(String[] args) throws IOException {
        File wordTemp = new File("src/main/resources/template/project-plan.docx");
        ProjectDocxData projectDocxData = createProjectDocxData();

        // 绑定插件
        LoopRowTableRenderPolicy loopRowPolicy = new LoopRowTableRenderPolicy();
        LoopColumnTableRenderPolicy loopColumnPolicy = new LoopColumnTableRenderPolicy();

        Configure config = Configure.builder()
                .bind("projectList", loopRowPolicy)
                .bind("policyList", loopRowPolicy)
                .bind("peerSummary", loopColumnPolicy)
                .bind("peerProjectSubsidyList", new PeerProjectSubsidyDynamicPolicy(projectDocxData.getPeerSummary()))
                .build();

        XWPFTemplate template = XWPFTemplate.compile(wordTemp, config).render(projectDocxData);
        template.writeAndClose(new FileOutputStream("project-output.docx"));
    }

    private static ProjectDocxData createProjectDocxData() {
        ProjectDocxData docxData = new ProjectDocxData();
        docxData.setCompanyName("华为技术有限公司");
        docxData.setPlanningDate("2024年8月19日");
        docxData.setPlanningCompany("华为技术有限公司");
        docxData.setProjectRecommendSize("40");
        docxData.setProjectTables(getProjectTables());

        docxData.setPolicySize("10");
        docxData.setPolicyMoney("50");
        docxData.setPolicyList(getPolicyList());

        docxData.setPeerSummary(getPeerSummary());

        docxData.setPeerProjectSubsidyList(getPeerProjectSubsidyList());

        return docxData;
    }

    private static PeerProjectSubsidyInfo getPeerProjectSubsidyList() {
        PeerProjectSubsidyInfo projectSubsidyInfo = new PeerProjectSubsidyInfo();
        projectSubsidyInfo.setAreaProjectList(createSubsidyItemList());
        projectSubsidyInfo.setCityProjectList(createSubsidyItemList());
        projectSubsidyInfo.setProvProjectList(createSubsidyItemList());
        projectSubsidyInfo.setCountryProjectList(createSubsidyItemList());
        return projectSubsidyInfo;
    }

    private static List<PeerProjectSubsidyInfo.ProjectSubsidyItem> createSubsidyItemList() {
        PeerProjectSubsidyInfo.ProjectSubsidyItem item1 = new PeerProjectSubsidyInfo.ProjectSubsidyItem();
        item1.setProjectName("fdsjalf-1");
        PeerProjectSubsidyInfo.SubsidyItem subsidyItem1 = new PeerProjectSubsidyInfo.SubsidyItem();
        subsidyItem1.setCount(1);
        PeerProjectSubsidyInfo.SubsidyItem subsidyItem2 = new PeerProjectSubsidyInfo.SubsidyItem();
        subsidyItem2.setCount(0);
        PeerProjectSubsidyInfo.SubsidyItem subsidyItem3 = new PeerProjectSubsidyInfo.SubsidyItem();
        subsidyItem3.setCount(5);
        subsidyItem3.setSubsidyMoney(15.6);
        item1.setSubsidyItems(Lists.newArrayList(subsidyItem1, subsidyItem2, subsidyItem3));

        PeerProjectSubsidyInfo.ProjectSubsidyItem item2 = new PeerProjectSubsidyInfo.ProjectSubsidyItem();
        item2.setProjectName("fdsjalf-2");
        item2.setSubsidyItems(Lists.newArrayList(subsidyItem1, subsidyItem2, subsidyItem3));

        return Lists.newArrayList(item1, item2);
    }

    private static List<ProjectDocxData.PeerSummaryTable> getPeerSummary() {
        ProjectDocxData.PeerSummaryTable table1 = new ProjectDocxData.PeerSummaryTable();
        table1.setEnterpriseName("华为技术有限公司");
        table1.setDescription("jfaldksjf");

        ProjectDocxData.PeerSummaryTable table2 = new ProjectDocxData.PeerSummaryTable();
        table2.setEnterpriseName("华为技术有限公司2");
        table2.setDescription("jfaldksjf");

        ProjectDocxData.PeerSummaryTable table3 = new ProjectDocxData.PeerSummaryTable();
        table3.setEnterpriseName("华为技术有限公司3");
        table3.setDescription("jfaldksjf");

        return Lists.newArrayList(table1, table2);
    }

    private static List<ProjectDocxData.ProjectTable> getProjectTables() {
        ProjectDocxData.ProjectTable projectTable1 = new ProjectDocxData.ProjectTable();
        projectTable1.setProjectYear("2021");
        ProjectDocxData.ProjectListVO listVO1 = newListVO();
        listVO1.setNum(1);
        ProjectDocxData.ProjectListVO listVO2 = newListVO();
        listVO2.setNum(2);
        ProjectDocxData.ProjectListVO listVO3 = newListVO();
        listVO3.setNum(3);
        projectTable1.setProjectList(Lists.newArrayList(listVO1, listVO2, listVO3));

        ProjectDocxData.ProjectTable projectTable2 = new ProjectDocxData.ProjectTable();
        projectTable2.setProjectYear("2023");
        ProjectDocxData.ProjectListVO listVO4 = newListVO();
        listVO4.setNum(1);
        projectTable2.setProjectList(Lists.newArrayList(listVO4));

        ProjectDocxData.ProjectTable projectTable3 = new ProjectDocxData.ProjectTable();
        projectTable3.setProjectYear("2024");
        ProjectDocxData.ProjectListVO listVO5 = newListVO();
        listVO5.setNum(1);
        projectTable3.setProjectList(Lists.newArrayList(listVO5));

        return Lists.newArrayList(projectTable1, projectTable2, projectTable3);
    }

    private static List<ProjectDocxData.PolicyListVO> getPolicyList() {
        ProjectDocxData.PolicyListVO listVO1 = newPolicyListVO();
        listVO1.setNum(1);
        ProjectDocxData.PolicyListVO listVO2 = newPolicyListVO();
        listVO2.setNum(2);
        ProjectDocxData.PolicyListVO listVO3 = newPolicyListVO();
        listVO3.setNum(3);

        return Lists.newArrayList(listVO1, listVO2, listVO3);
    }

    private static ProjectDocxData.PolicyListVO newPolicyListVO() {
        ProjectDocxData.PolicyListVO listVO = new ProjectDocxData.PolicyListVO();
        listVO.setDepartment("河北省工业和信息化厅");
        listVO.setProjectName("名字-" + RandomUtils.nextInt(100, 500000));
        listVO.setGrade("国家级");
        listVO.setSubsidyMoney("100万元");
        listVO.setPublicDate("8月10日");
        return listVO;
    }

    private static ProjectDocxData.ProjectListVO newListVO() {
        ProjectDocxData.ProjectListVO listVO = new ProjectDocxData.ProjectListVO();
        listVO.setGood("好处2");
        listVO.setProjectName("名字-" + RandomUtils.nextInt(100, 500000));
        listVO.setSuitList("fasdf");
        listVO.setSubsidyMoney("100万元");
        listVO.setApplyTimeStr("8月10日");
        return listVO;
    }

}
