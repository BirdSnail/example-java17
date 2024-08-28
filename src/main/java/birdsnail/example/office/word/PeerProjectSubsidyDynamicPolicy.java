package birdsnail.example.office.word;

import birdsnail.example.entity.PeerProjectSubsidyInfo;
import birdsnail.example.entity.ProjectDocxData;
import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.Cells;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.util.TableTools;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.List;

public class PeerProjectSubsidyDynamicPolicy extends DynamicTableRenderPolicy {

    private static final int AREA_START_ROW = 9;
    private static final int CITY_START_ROW = 7;
    private static final int PROVINCE_START_ROW = 5;
    private static final int COUNTRY_START_ROW = 3;

    private List<ProjectDocxData.PeerSummaryTable> peerSummary;

    public PeerProjectSubsidyDynamicPolicy(List<ProjectDocxData.PeerSummaryTable> peerSummary) {
        this.peerSummary = peerSummary;
    }

    @Override
    public void render(XWPFTable table, Object data) throws Exception {
        if (null == data) {
            return;
        }
        PeerProjectSubsidyInfo projectSubsidyInfo = (PeerProjectSubsidyInfo) data;

        List<PeerProjectSubsidyInfo.ProjectSubsidyItem> areaProjectList = projectSubsidyInfo.getAreaProjectList();
        if (CollectionUtils.isNotEmpty(areaProjectList)) {
            writeProjectSubsidy(areaProjectList, AREA_START_ROW, table);
        }

        List<PeerProjectSubsidyInfo.ProjectSubsidyItem> cityProjectList = projectSubsidyInfo.getCityProjectList();
        if (CollectionUtils.isNotEmpty(cityProjectList)) {
            writeProjectSubsidy(cityProjectList, CITY_START_ROW, table);
        }

        List<PeerProjectSubsidyInfo.ProjectSubsidyItem> provProjectList = projectSubsidyInfo.getProvProjectList();
        if (CollectionUtils.isNotEmpty(provProjectList)) {
            writeProjectSubsidy(provProjectList, PROVINCE_START_ROW, table);
        }

        List<PeerProjectSubsidyInfo.ProjectSubsidyItem> countryProjectList = projectSubsidyInfo.getCountryProjectList();
        if (CollectionUtils.isNotEmpty(countryProjectList)) {
            writeProjectSubsidy(countryProjectList, COUNTRY_START_ROW, table);
        }

        peerSummary = null;
    }

    private void writeProjectSubsidy(List<PeerProjectSubsidyInfo.ProjectSubsidyItem> projectSubsidyItems, int startRow, XWPFTable table) throws Exception {

        table.removeRow(startRow);
        int colLength = peerSummary.size() + 1;
        // 循环插入行
        for (PeerProjectSubsidyInfo.ProjectSubsidyItem subsidyItem : projectSubsidyItems) {
            XWPFTableRow insertNewTableRow = table.insertNewTableRow(startRow);
            for (int j = 0; j < colLength; j++) {
                insertNewTableRow.createCell();
            }
            // 单行渲染
            TableRenderPolicy.Helper.renderRow(table.getRow(startRow), toRowData(subsidyItem, colLength));
        }
        // 合并单元格
        TableTools.mergeCellsHorizonal(table, startRow - 1, 0, colLength - 1);
    }

    private RowRenderData toRowData(PeerProjectSubsidyInfo.ProjectSubsidyItem subsidyItem, int colLength) {
        CellRenderData nameCell = Cells.of(subsidyItem.getProjectName()).create();
        Rows.RowBuilder rowBuilder = Rows.of(nameCell);

        List<PeerProjectSubsidyInfo.SubsidyItem> subsidyItems = subsidyItem.getSubsidyItems();
        for (int i = 0; i < colLength - 1; i++) {
            PeerProjectSubsidyInfo.SubsidyItem item = subsidyItems.get(i);
            String value = item.getCount() != null && item.getCount() > 0 ? "√" : "×";
            if (item.getSubsidyMoney() != null && item.getSubsidyMoney() > 0) {
                value = value.concat("\n").concat("获批" + item.getSubsidyMoney() + "万元");
            }
            rowBuilder.addCell(Cells.of(value).center().create());
        }
        return rowBuilder.create();
    }

}
