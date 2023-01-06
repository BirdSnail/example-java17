package birdsnail.example.base;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import lombok.Data;

public class StringTest {


    public static void main(String[] args) {
        // String source = "hello, ${p1}, welcome ${p2},---";
        // Map<String, String> values = Map.of("p1", "yhd", "p2", "zhensheng");
        // System.out.println(StringSubstitutor.replace(source, values));

        //
        // String s = """
        //         {"mainBusinessVO":{"mainProductTax":"","occupancy":"0.00","taxAmountJson":""},"greatPunishVO":{},"researchCarrierVO":{"doProduction":1,"doResearchCost":0},"fixedAssetsVO":{"equipmentAmountJson":"","fixedNetJson":""},"investFinancingVO":{},"legalVO":{},"popularityVO":{"address":"","brandCodes":"[1,2,3]","countryCode":"CN","doFamous":1,"industryRankingCode":2,"brandName":"老字号,驰名商标,名牌产品","industryRankingName":"行业前三","countryName":"中国","provinceName":"","cityName":"","countyName":""},"staffVO":{"allStaff":"","researchCount":10,"undergraduateCount":0,"middleCount":0,"masterCount":1,"highCount":0,"doctorCount":1,"doDoctorCooperation":0,"juniorCount":0},"equipmentVO":{"floorSpace":0,"officeSpace":"","doInfoSystem":0,"doResearch":1,"doTechInsurance":0,"loans":0,"machineCodes":"[7]","systemUseCodes":"[14]","machineName":"无","doInfoSystemInput":""},"otherVO":{"creatorBg":"","creditRatingCode":1,"doFamousSupplier":1,"doFinancing":1,"doIntellectual":1,"eiaProceduresFlag":0,"famousSupplierName":"23","faultYear":"","founderHonor":"","founderHonorName":"","founderHonorYear":"","intellectualHonorCode":1,"preTrialCode":1,"researchCode":1,"salesAreaCodes":"[\\"CN\\"]","standardizationFlag":0,"researchName":"国家级","intellectualHonorName":"知识产权示范企业","creditRatingName":"A","salesAreaNames":"中国"},"financeVOS":[{"assets":5000,"boothCost":5,"brandCost":10,"businessIncome":1000,"equipmentCost":10,"id":7882,"infoCost":10,"netAssets":500,"netMargin":500,"researchIncome":10,"researchScale":1,"taxes":500,"yearNumber":"2019","editStatus":true},{"assets":11,"boothCost":2,"brandCost":123,"businessIncome":1111,"equipmentCost":23,"id":7883,"infoCost":4,"netAssets":223,"netMargin":23,"researchIncome":111,"researchScale":9.99,"taxes":11,"yearNumber":"2020","editStatus":true}],"infoVO":{"niceclassList":[{"newName":"电子商务","niceCode":"4154f9d6ea373709b909f0e603f018c9","niceName":"电子商务"},{"newName":"其他未列明批发","niceCode":"670332bef8d4abbca07e97a65bfc76a6","niceName":"其他未列明批发"},{"newName":"批发","niceCode":"7c114133dc49b0d2ca20e24747100504","niceName":"批发"},{"newName":"保健食品","niceCode":"7530b7cd04d35be3be26e3e651764386","niceName":"保健食品"}],"staffCount":100,"techApplyCodes":""},"policyVO":{"constructIndustyQualifCodes":"","doHighTech":1,"doIntellectualCert":1,"doIsoCert":0,"doResearchCost":0,"doTech":0,"doWarIndustry":0,"eiaProceduresFlag":0,"haveQualityFlag":0,"haveZjzzFlag":0,"isoNumCodes":"","preTrialCode":1,"standardizationFlag":0,"constructIndustyQualiftName":"","isoName":""},"customerCode":"W000001291","id":67}
        //         """;
        // JSONObject cityCode = JSONObject.parseObject(s);
        // System.out.println(cityCode.size());

        // System.out.println(Objects.equals(null, null));
        // System.out.println(Long.parseLong("003"));


        // System.out.println(htmlParse("气相色谱GCIsoLinkforC,NandH,includingaTraceGCUltraTrisPlusautosampler,元素分析仪ElementalAnalyzerFlashHT,一个炉子测C，N；另一个测H，O。包括MAS200R自动进样器（固体）和AS3000进样器（液体）Gasbench： (1)气体采集系统； (2)免维护除水系统； (3)定量环进样系统； (4)等温气相色谱仪（GC）; (5)一个活动开口分流接口; (6)一个带三路参考气体接头的参考气体进样系统 (7)一到两个选件配置的液氮冷凝阱组成。PreCon:配备一套96位×10毫升的样品盘和GC-PAL自动进样器Interface：ConFloIVUniversalInterface。质谱仪MAT253Plus质谱仪 1)可测C、H/D、O、N、S; 2)质量数范围:1～150dalton; 3)分辨率:CNOS:m/&Delta;m=200(10%valley);H/D:m/&Delta;m=25(10%valley); 4)绝对灵敏度:600molecules/Ion(CO2=44); 5)离子源线性:0.02 /nA; 6)加速电压为10kV; 7)H3+因子:<10ppm/nA; 8)放大器输出范围:0～50V; 9)样品消耗:0.031nmol/sCO2Duelinlet; 10)ISODATNT–用于数据采集处理和仪器控制的多任务软件; 11)H/D接收器; 12)额外法拉第杯，用于测47,48和49; 13)DualInletSystem双路进样系统; 14)CH4/N2O痕量气体预浓缩装置(PreCon)； 15）配备44,45,46,47,48,49接收杯； 16）反硝化装置"));
        // System.out.println(htmlParse("颈复康"));

        // String s = "update device_base_info set technical_indicator = REPLACE(technical_indicator,'&lt;','<') <b>where</b> technical_indicator like '%&lt;%';";
        // String ss = "<p style=\"text-indent:2em;\"> 为做好2017年黟县上半年部分事业单位面向社会公开招聘人员工作，根据《事业单位人事管理条例》(国务院令第652号)和省委组织部、省人力资源和社会保障厅《关于印发<安徽省事业单位公开招聘人员暂行办法>的通知》(皖人社发〔2010〕78号)等规定，经县公开招聘工作领导组研究，并报市人力资源和社会保障局核准，现就2017年黟县上半年部分事业单位公开招聘人员工作有关事项公告如下：</p> <p style=\"text-indent:2em;\"> 一、招聘原则</p> <p style=\"text-indent:2em;\"> (一)坚持面向社会、公开招聘;</p> <p style=\"text-indent:2em;\"> (二)坚持考试考察、择优聘用;</p> <p style=\"text-indent:2em;\"> (三)坚持统一组织、分工负责。</p> <p style=\"text-indent:2em;\"> 二、招聘计划</p> <p style=\"text-indent:2em;\"> 2017年度黟县上半年部分事业单位公开招聘工作人员10名(详见附件)。县委组织部、县人力资源和社会保障局于2017年5月19日在黟县政府网(http://www.yixian.gov.cn/)、黟县先锋网(http://yxxfw.yixian.gov.cn/)、黟县人力资源和社会保障网(http://yxhr.yixian.gov.cn/)及相关媒体上统一发布招聘公告。今后相关的考务信息将陆续在黟县人力资源和社会保障网(http://yxhr.yixian.gov.cn/)上发布，请考生登陆网站查询。</p> <p style=\"text-indent:2em;\"> 三、招聘条件</p> <p style=\"text-indent:2em;\"> 招聘对象主要为国家承认学历的应、历届大专及以上学历毕业生，以及符合招聘岗位条件的人员，且必须符合以下条件：</p> <p style=\"text-indent:2em;\"> (一)具有中华人民共和国国籍;</p> <p style=\"text-indent:2em;\"> (二)遵守宪法和法律;</p> <p style=\"text-indent:2em;\"> (三)具有良好的品行;</p> <p style=\"text-indent:2em;\"> (四)岗位所需的专业或技能条件;</p> <p style=\"text-indent:2em;\"> (五)适应岗位要求的身体条件;</p>";
        // System.out.println(htmlParse(s));
        // System.out.println(htmlParse(ss));
        String src = "一种采用nano-CeO<Sub>2</Sub>/H<Sub>2</Sub>O<Sub>2</Sub>/O<Sub>3</Sub>体系处理酸性难降解废水的方法";
        System.out.println("原始内容-->" + src);
        // System.out.println("处理后的内容-->" + htmlParse(src));
        System.out.println("处理后并移除换行的内容-->" + htmlParse(src).replaceAll("[/|?%.]", "_"));
        // System.out.println("====");
        System.out.println("爱信诺?CeO2/H2O2/O3.fsf%132|aaa".replaceAll("[/|?%.]", "_"));

        // char c = '杨';
        // System.out.println((int) c);

        // System.out.println(System.getProperty("sun.arch.data.model"));

        // List<Integer> integers = Arrays.asList(1, null, 2);
        // int sum = integers.stream().mapToInt(it -> it == null ? 0 : it).sum();
        // System.out.println(sum);
        String month = "202203";
        LocalDate monthTime = LocalDate.parse(month, DateTimeFormatter.ofPattern("yyyyMM")).withDayOfMonth(1);
        Date monitorDate = Date.from(monthTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println(monitorDate);


    }


    /**
     * 解析html富文本，转换为常规文本
     */
    private static String htmlParse(String str) {
        ParserDelegator parserDelegator = new ParserDelegator();
        HtmlTextCallBack callBack = new HtmlTextCallBack();
        // HtmlTextCallBack callBack = new HtmlTextCallBack("_"); // 可以指定分隔符
        try (InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
             Reader reader = new InputStreamReader(inputStream)) {
            parserDelegator.parse(reader, callBack, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return callBack.getText();
    }


    static class HtmlTextCallBack extends HTMLEditorKit.ParserCallback {

        private StringBuilder sb = new StringBuilder();

        private String delimiter;

        public HtmlTextCallBack() {
        }

        public HtmlTextCallBack(String delimiter) {
            this.delimiter = delimiter;
        }

        @Override
        public void handleText(char[] data, int pos) {
            sb.append(data);
            if (delimiter != null) {
                sb.append(delimiter);
            }
        }

        public String getText() {
            return sb.toString();
        }
    }

    @Data
    static class CityCode {
        private String cityCode;
    }
}
