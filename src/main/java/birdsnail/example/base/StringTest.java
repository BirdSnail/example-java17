package birdsnail.example.base;

import java.util.Objects;

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

        System.out.println(Objects.equals(null, null));
    }

    @Data
    static class CityCode {
        private String cityCode;
    }
}
