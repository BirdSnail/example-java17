package birdsnail.example.office.word;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 区块对实现手动添加分节符
 */
public class PoiTlPageDemo {

    public static void main(String[] args) throws IOException {
        File wordTemp = new File("src/main/resources/template/poi-tl-word.docx");
        // 绑定插件
        LoopRowTableRenderPolicy loopRowPolicy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder()
                .bind("propertiesTable", loopRowPolicy)
                .bind("pageBreak", new PageBreakRenderPolicy())
                .build();

        XWPFTemplate template = XWPFTemplate.compile(wordTemp, config).render(createModel());
        template.writeAndClose(new FileOutputStream("project-word-out.docx"));
    }

    static PolWordDemoModel createModel() {
        PolWordDemoModel model = new PolWordDemoModel();
        model.setTitle("hahh");
        PageTestBlock block = new PageTestBlock();
        block.setSomething("1.文件是开了个1");
        block.setContent("fadsfsadfsa");
        block.setPageBreak(true);

        PageTestBlock block2 = new PageTestBlock();
        block2.setSomething("2.飞洒地方");
        block2.setContent("范德萨范德萨分");
        block2.setPageBreak(true);

        model.setPageTest(Lists.newArrayList(block, block2));
        return model;
    }

    @Data
    public static class PolWordDemoModel {
        private String title;
        private NameValue properties;
        private TableData tables;
        private List<PageTestBlock> pageTest;
    }

    @Data
    private static class TableData{
        private List<TableItem> propertiesTable;
    }

    @Data
    public static class TableItem{
        private String name;
        private String required;
        private String description;
        private String schema;
    }

    @Data
    public static class NameValue{
        private String name;
        private String value;
    }

    @Data
    public static class PageTestBlock{
        private boolean pageBreak;
        private String something;
        private String content;
    }

}
