package birdsnail.example.office.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 不创建对象读取并处理excel
 */
public class ExcelNoModelDataHandler {

    /**
     * 处理excel数据
     *
     * @param fileName       excel
     * @param recordsHandler 处理逻辑
     */
    public static void handleAll(String fileName, Consumer<List<Map<Integer, String>>> recordsHandler) {
        EasyExcel.read(fileName, new NoModelDataListener(recordsHandler)).sheet().doRead();
    }

    @Slf4j
    public static class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {

        /**
         * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
         */
        private static final int BATCH_COUNT = 100;
        private List<Map<Integer, String>> cachedDataList = new LinkedList<>();

        private final Consumer<List<Map<Integer, String>>> recordsHandler;

        public NoModelDataListener(Consumer<List<Map<Integer, String>>> recordsHandler) {
            this.recordsHandler = recordsHandler;
        }

        @Override
        public void invoke(Map<Integer, String> data, AnalysisContext context) {
            log.info("解析到一条数据:{}", JSON.toJSONString(data));
            cachedDataList.add(data);
//            if (cachedDataList.size() >= BATCH_COUNT) {
//                saveData();
//                cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
//            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            saveData();
            log.info("所有数据解析完成！");
            cachedDataList.clear();
            cachedDataList = new LinkedList<>();
        }

        /**
         * 加上存储数据库
         */
        private void saveData() {
            log.info("读取{}条数据，开始处理！", cachedDataList.size());
            recordsHandler.accept(cachedDataList);
        }
    }

}
