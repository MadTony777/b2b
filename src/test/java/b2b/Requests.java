package b2b;

import b2b.Utilities.SoapClient;
import b2b.Utilities.SoapClientBuilder;
import b2b.Utilities.SoapRequest;
import b2b.Utilities.SoapResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static b2b.BaseClass.*;

public class Requests {

    public static SoapClient soapClient;

    public static List<String> europlanRequestMethod(String fileName, String environment) throws Exception {
        String URL = requesttURL(environment);
        String soapAction = "http://www.vsk.ru/IPartnersPolicyService/CalcV2";
        String cid = String.valueOf(UUID.randomUUID());
        log.info("cid is: " + cid);
        Map<String, String> map = new HashMap<>();
        map.put("JMSCorrelationID", cid);
        soapClient = SoapClientBuilder.create().withDigestAuth("EUROPLAN", "EUROPLAN").build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + fileName)), StandardCharsets.UTF_8);
        SoapResponse<String> response = soapClient.call(new SoapRequest(URL, soapAction, rightBody, map));
        String result = String.valueOf(response);
        log.info(result);
        soapClient.close();
        List<String> results = new ArrayList<>();
        results.add(0, cid);
        results.add(1, result);
        log.info("List is made");
        return results;
    }


    public static List<String> RequestMethodAll(String fileName, String environment) throws Exception {
        String URL = requesttURL(environment);
        String changeTarget = "";
        String changeValue = "";
        String changeTarget1 = "";
        String changeValue1 = "";
        String changeTarget2 = "";
        String changeValue2 = "";
        Date today = Calendar.getInstance().getTime();
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(today);
        String newstring = todayDate + "T00:00:00";
        String yesterday = new SimpleDateFormat("yyyy-MM").format(today);
        String newYesterday = yesterday + "-01T00:00:00";
        log.info("Yesterday date is: " + newYesterday);
        String cid = String.valueOf(UUID.randomUUID());
        log.info("cid is: " + cid);
        Map<String, String> map = new HashMap<>();
        switch (fileName) {
            case "zimAll.xml":
                map.put("X-VSK-ProductCode", "productCode666");
                map.put("X-VSK-ProductId", "productId");
                changeTarget = "${NAME}";
                changeValue = cid;
                break;
            case "ZIM.xml":
            case "getCarCost.xml":
            case "getFranchiseStage.xml":
            case "getFranchiseTest.xml":
                map.put("JMSCorrelationID", cid);
                break;
            case "getPolicyStatus.xml":

            case "stopUnderwriting.xml":
            case "getVehiclePhoto.xml":
            case "deleteVehiclePhoto.xml":
            case "getVehiclePhotoFileList.xml":
            case "getPrintForm.xml":
            case "calcAndSavePaymentsZIM.xml":
            case "getPolicy.xml":
            case "SendToUndewriting.xml":
            case "SendToUnderwritingWithTasks.xml":
            case "GetPolicyPrintForms.xml":
            case "setVehiclePhoto.xml":
                map.put("JMSCorrelationID", cid);
                changeTarget = "${POLICYID}";
                changeValue = getTag(environment, "POLICYID").get(2);
                log.info("changeValue is: " + changeValue);
                break;
            case "getTaskStatus.xml":
                map.put("JMSCorrelationID", cid);
                changeTarget = "${POLICYID}";
                changeValue = getTag(environment, "POLICYID").get(2);
                log.info("changeValue is: " + changeValue);
                sendToUnderwritingVoid(changeValue, environment);
                break;
            case "setReviewUnderwritingParams.xml":
                map.put("JMSCorrelationID", cid);
                changeTarget = "${POLICYID}";
                List<String> list = getTag(environment, "POLICYID");
                changeValue = list.get(2);
                log.info("changeValue is: " + changeValue);
                sendToUnderwritingVoid(changeValue, environment);
                setVehiclePhotoVoid(changeValue, environment);
                break;
            case "updatePolicyTags.xml":
                map.put("JMSCorrelationID", cid);
                changeTarget = "${TIME}";
                changeValue = newstring;
                changeTarget1 = "${POLICYID}";
                changeValue1 = CreateForUpdate(environment).get(2);
                break;
            case "updatePolicyInfoAll.xml":
                map.put("JMSCorrelationID", cid);
                changeTarget = "${TIME}";
                changeValue = newstring;
                break;
            case "AnnulatedPaymentZIM.xml":
            case "changePaymentMethod.xml":
                map.put("JMSCorrelationID", cid);
                changeTarget = "${PAYMENTID}";
                changeValue = RequestMethodIssue(environment);
                break;
            case "IssuePayment.xml":
                map.put("JMSCorrelationID", cid);
                changeTarget = "${PAYMENTID}";
                changeValue = getTag(environment, "PAYMENTID").get(2);
                break;
            case "CreatePolicyV2.xml":
            case "createPolicyV2tags.xml":
                map.put("X-VSK-ProductCode", "productCode666");
                map.put("X-VSK-ProductId", "productId");
                changeTarget = "${TIME}";
                changeValue = newstring;
                break;
            case "UpdatePolicyUnderwritingStatusCalback.xml":
                map.put("JMSCorrelationID", cid);
                changeTarget = "${POLICYID}";
                changeValue = getTag(environment, "POLICYID").get(2);
                changeTarget1 = "${POLICYNUMBER}";
                changeValue1 = getTag(environment, "PolicyNumber").get(2);
                changeTarget2 = "${TIME}";
                changeValue2 = newYesterday;
                sendToUnderwritingVoid(environment);
        }
        String soapAction = "";
        switch (fileName) {
            case "zimAll.xml":
            case "ZIM.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/CalcV2";
                break;
            case "CreatePolicyV2.xml":
            case "createPolicyV2tags.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/CreatePolicyV2";
                break;
            case "getPolicyStatus.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/GetPolicyStatus";
                break;
            case "getTaskStatus.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/GetTaskStatus";
                break;
            case "stopUnderwriting.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/ResetUnderResults";
                break;
            case "getVehiclePhoto.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/GetVehiclePhoto";
                break;
            case "setVehiclePhoto.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/SetVehiclePhoto";
                break;
            case "getPrintForm.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/PrintDocuments";
                break;
            case "calcAndSavePaymentsZIM.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/CalcAndSavePayments";
                break;
            case "updatePolicyInfoAll.xml":
            case "updatePolicyTags.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/UpdatePolicyV2";
                break;
            case "AnnulatePaymentZIM.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/AnnulatePayment";
                break;
            case "getPolicy.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/GetPolicy";
                break;
            case "IssuePayment.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/IssuePayment";
                break;
            case "SendToUndewriting.xml":
            case "SendToUnderwritingWithTasks.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/SendToUndewriting";
                break;
            case "UpdatePolicyUnderwritingStatusCalback.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/UpdatePolicyUnderwritingStatus";
                break;
            case "GetPolicyPrintForms.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/GetPolicyPrintForms";
                break;
            case "getVehiclePhotoFileList.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/GetVehiclePhotoFilesList";
                break;
            case "deleteVehiclePhoto.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/DeleteVehiclePhoto";
                break;
            case "getFranchiseStage.xml":
            case "getFranchiseTest.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/GetFranchise";
                break;
            case "getCarCost.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/GetCarCost";
                break;
            case "changePaymentMethod.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/ChangePaymentMethod";
                break;
            case "setReviewUnderwritingParams.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/SetReviewUnderwritingParams";
        }
        String logPass;
        if (fileName.equals("getFranchiseStage.xml")) {
            logPass = "test_dKASKO";
        } else {
            logPass = "ZIM";
        }
        soapClient = SoapClientBuilder.create().withDigestAuth(logPass, logPass).build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + fileName)), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace(changeTarget, changeValue).replace(changeTarget1, changeValue1).replace(changeTarget2, changeValue2);
        SoapResponse<String> response = soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
        String result = String.valueOf(response);
        log.info(result);
        soapClient.close();
        List<String> results = new ArrayList<>();
        results.add(0, cid);
        results.add(1, result);
        results.add(2, changeValue);
        String rID = result;
        String requestidTag;
        int requestidIndex;
        if (fileName.equals("stopUnderwriting.xml") || fileName.equals("deleteVehiclePhoto.xml") || fileName.equals("setReviewUnderwritingParams.xml")) {
            requestidTag = "REQUESTID xmlns=\"http://www.vsk.ru\">";
            requestidIndex = 36;
        } else {
            requestidTag = "REQUESTID>";
            requestidIndex = 10;
        }
        rID = rID.substring(rID.indexOf(requestidTag) + requestidIndex);
        rID = rID.substring(0, rID.indexOf("</REQUESTID"));
        log.info("RequestID is: " + rID);
        results.add(3, rID);
        log.info("List is made");
        return results;
    }


    public static String RequestMethodIssue(String environment) throws Exception {
        String URL = requesttURL(environment);
        String cid = String.valueOf(UUID.randomUUID());
        log.info("cid is: " + cid);
        Map<String, String> map = new HashMap<>();
        map.put("JMSCorrelationID", cid);
        String changeTarget = "${PAYMENTID}";
        String changeValue = getPaymentID(environment).get(2);
        String soapAction = "http://www.vsk.ru/IPartnersPolicyService/IssuePayment";
        soapClient = SoapClientBuilder.create().withDigestAuth("ZIM", "ZIM").build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + "IssuePayment.xml")), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace(changeTarget, changeValue);
        soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
        return changeValue;
    }


    public static List<String> getTag(String environment, String tag) throws Exception {
        String URL = requesttURL(environment);
        String changeTarget, changeValue;
        Date today = Calendar.getInstance().getTime();
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(today);
        String newstring = todayDate + "T00:00:00";
        String cid = String.valueOf(UUID.randomUUID());
        Map<String, String> map = new HashMap<>();
        map.put("X-VSK-ProductCode", "productCode666");
        map.put("X-VSK-ProductId", "productId");
        changeTarget = "${TIME}";
        changeValue = newstring;
        String soapAction = "http://www.vsk.ru/IPartnersPolicyService/CreatePolicyV2";
        soapClient = SoapClientBuilder.create().withDigestAuth("ZIM", "ZIM").build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + "CreatePolicyV2.xml")), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace(changeTarget, changeValue);
        SoapResponse<String> response = soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
        String result = String.valueOf(response);
        log.info(result);
        soapClient.close();
        List<String> results = new ArrayList<>();
        results.add(0, cid);
        results.add(1, result);
        log.info("List is made");
        String pID = results.get(1);
        pID = pID.substring(pID.indexOf("POLICYID>") + 9);
        pID = pID.substring(0, pID.indexOf("</POLICYID"));
        String pNUM = results.get(1);
        pNUM = pNUM.substring(pNUM.indexOf("POLICYNUMBER>") + 13);
        pNUM = pNUM.substring(0, pNUM.indexOf("</POLICYNUMBER"));
        String payID = results.get(1);
        payID = payID.substring(payID.indexOf("PAYMENTID>") + 10);
        payID = payID.substring(0, payID.indexOf("</PAYMENTID"));
        String returnTag = null;
        switch (tag) {
            case "POLICYID":
                returnTag = pID;
                break;
            case "PolicyNumber":
                returnTag = pNUM;
                break;
            case "PAYMENTID":
                returnTag = payID;
        }
        results.add(2, returnTag);
        return results;
    }


    public static List<String> CreateForUpdate(String environment) throws Exception {
        String URL = requesttURL(environment);
        String changeTarget, changeValue;
        Date today = Calendar.getInstance().getTime();
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(today);
        String newstring = todayDate + "T00:00:00";
        Map<String, String> map = new HashMap<>();
        map.put("X-VSK-ProductCode", "productCode666");
        map.put("X-VSK-ProductId", "productId");
        changeTarget = "${TIME}";
        changeValue = newstring;
        String cid = String.valueOf(UUID.randomUUID());
        String soapAction = "http://www.vsk.ru/IPartnersPolicyService/CreatePolicyV2";
        soapClient = SoapClientBuilder.create().withDigestAuth("ZIM", "ZIM").build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + "createPolicyV2Tags.xml")), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace(changeTarget, changeValue);
        SoapResponse<String> response = soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
        String result = String.valueOf(response);
        log.info(result);
        soapClient.close();
        List<String> results = new ArrayList<>();
        results.add(0, cid);
        results.add(1, result);
        log.info("List is made");
        String pID = results.get(1);
        pID = pID.substring(pID.indexOf("POLICYID>") + 9);
        pID = pID.substring(0, pID.indexOf("</POLICYID"));
        results.add(2, pID);
        return results;
    }


    public static void sendToUnderwritingVoid(String environment) throws Exception {
        String URL = requesttURL(environment);
        String changeTarget;
        String changeValue;
        String cid = String.valueOf(UUID.randomUUID());
        Map<String, String> map = new HashMap<>();
        map.put("JMSCorrelationID", cid);
        changeTarget = "${POLICYID}";
        changeValue = getTag(environment, "POLICYID").get(2);
        String soapAction = "http://www.vsk.ru/IPartnersPolicyService/SendToUndewriting";
        soapClient = SoapClientBuilder.create().withDigestAuth("ZIM", "ZIM").build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + "SendToUndewriting.xml")), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace(changeTarget, changeValue);
        soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
    }


    public static void sendToUnderwritingVoid(String policyID, String environment) throws Exception {
        String URL = requesttURL(environment);
        String changeTarget;
        String changeValue;
        String cid = String.valueOf(UUID.randomUUID());
        Map<String, String> map = new HashMap<>();
        map.put("JMSCorrelationID", cid);
        changeTarget = "${POLICYID}";
        changeValue = policyID;
        String soapAction = "http://www.vsk.ru/IPartnersPolicyService/SendToUndewriting";
        soapClient = SoapClientBuilder.create().withDigestAuth("ZIM", "ZIM").build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + "SendToUndewriting.xml")), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace(changeTarget, changeValue);
        soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
    }


    public static void setVehiclePhotoVoid(String policyID, String environment) throws Exception {
        String URL = requesttURL(environment);
        String changeTarget;
        String changeValue;
        String cid = String.valueOf(UUID.randomUUID());
        Map<String, String> map = new HashMap<>();
        map.put("JMSCorrelationID", cid);
        changeTarget = "${POLICYID}";
        changeValue = policyID;
        String soapAction = "http://www.vsk.ru/IPartnersPolicyService/SetVehiclePhoto";
        soapClient = SoapClientBuilder.create().withDigestAuth("ZIM", "ZIM").build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + "setVehiclePhoto.xml")), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace(changeTarget, changeValue);
        soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
    }


    public static List<String> getPaymentID(String environment) throws Exception {
        String URL = requesttURL(environment);
        String changeTarget;
        String changeValue;
        Date today = Calendar.getInstance().getTime();
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(today);
        String newstring = todayDate + "T00:00:00";
        String cid = String.valueOf(UUID.randomUUID());
        Map<String, String> map = new HashMap<>();
        map.put("X-VSK-ProductCode", "productCode666");
        map.put("X-VSK-ProductId", "productId");
        changeTarget = "${TIME}";
        changeValue = newstring;
        String soapAction = "http://www.vsk.ru/IPartnersPolicyService/CreatePolicyV2";
        soapClient = SoapClientBuilder.create().withDigestAuth("ZIM", "ZIM").build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + "CreatePolicyV2.xml")), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace(changeTarget, changeValue);
        SoapResponse<String> response = soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
        String result = String.valueOf(response);
        log.info(result);
        soapClient.close();
        List<String> results = new ArrayList<>();
        results.add(0, cid);
        results.add(1, result);
        log.info("List is made");
        String pID = results.get(1);
        pID = pID.substring(pID.indexOf("PAYMENTID>") + 10);
        pID = pID.substring(0, pID.indexOf("</PAYMENTID"));
        results.add(2, pID);
        return results;
    }


    public static List<String> RequestUpdatePolicyUndewritingStatusNoLogging(String fileName, String environment) throws Exception {
        String URL = requesttURL(environment);
        String changeTarget, changeValue, changeTarget1, changeValue1, changeTarget2, changeValue2;
        Date today = Calendar.getInstance().getTime();
        String yesterday = new SimpleDateFormat("yyyy-MM").format(today);
        String newYesterday = yesterday + "-01T00:00:00";
        log.info("Yesterday date is: " + newYesterday);
        String cid = String.valueOf(UUID.randomUUID());
        log.info("cid is: " + cid);
        Map<String, String> map = new HashMap<>();
        map.put("JMSCorrelationID", cid);
        changeTarget = "${POLICYID}";
        changeValue = getTag(environment, "POLICYID").get(2);
        changeTarget1 = "${POLICYNUMBER}";
        changeValue1 = getTag(environment, "PolicyNumber").get(2);
        changeTarget2 = "${TIME}";
        changeValue2 = newYesterday;
        sendToUnderwritingVoid(environment);
        String soapAction = "http://www.vsk.ru/IPartnersPolicyService/UpdatePolicyUnderwritingStatus";
        soapClient = SoapClientBuilder.create().withDigestAuth("ZIM", "ZIM").build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + fileName)), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace(changeTarget, changeValue).replace(changeTarget1, changeValue1).replace(changeTarget2, changeValue2);
        SoapResponse<String> response = soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
        String result = String.valueOf(response);
        log.info(result);
        soapClient.close();
        List<String> results = new ArrayList<>();
        results.add(0, cid);
        results.add(1, result);
        results.add(2, changeValue);
        results.add(3, bodyFinal);
        return results;
    }


    public static List<String> RequestGetPaymentsWithPaidStatus(String fileName, String environment) throws Exception {
        String URL = requesttURL(environment);
        String cid = String.valueOf(UUID.randomUUID());
        log.info("cid is: " + cid);
        Map<String, String> map = new HashMap<>();
        map.put("JMSCorrelationID", cid);
        String policyID = cid;
        sendToUnderwritingVoid(environment);
        String soapAction = "";
        switch (fileName) {
            case "GetPaymentsWithPaidStatusTest.xml":
            case "GetPaymentsWithPaidStatusStage.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/GetPaymentsWithPaidStatus";
                break;
            case "prolongatePolicy.xml":
                soapAction = "http://www.vsk.ru/IPartnersPolicyService/ProlongatePolicy";
        }
        soapClient = SoapClientBuilder.create().withDigestAuth("ZIM", "ZIM").build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + fileName)), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace("${POLICYID}", policyID);
        SoapResponse<String> response = soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
        String result = String.valueOf(response);
        log.info(result);
        soapClient.close();
        List<String> results = new ArrayList<>();
        results.add(0, policyID);
        results.add(1, result);
        results.add(2, bodyFinal);
        return results;
    }


    public static List<String> RequestSetUnderStatus(String environment) throws Exception {
        String URL = requesttURL(environment);
        String changeTarget, changeValue;
        Date today = Calendar.getInstance().getTime();
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(today);
        String newstring = todayDate + "T00:00:00";
        String cid = String.valueOf(UUID.randomUUID());
        Map<String, String> map = new HashMap<>();
        map.put("X-VSK-ProductCode", "productCode666");
        map.put("X-VSK-ProductId", "productId");
        changeTarget = "${TIME}";
        changeValue = newstring;
        String soapAction = "http://www.vsk.ru/IPartnersPolicyService/CreatePolicyV2";
        soapClient = SoapClientBuilder.create().withDigestAuth("ZIM", "ZIM").build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + "CreatePolicyV2.xml")), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace(changeTarget, changeValue);
        SoapResponse<String> response = soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
        String result = String.valueOf(response);
        log.info(result);
        soapClient.close();
        List<String> results = new ArrayList<>();
        results.add(0, cid);
        results.add(1, result);
        log.info("List is made");
        String pID = results.get(1);
        pID = pID.substring(pID.indexOf("POLICYID>") + 9);
        pID = pID.substring(0, pID.indexOf("</POLICYID"));
        String pNUM = results.get(1);
        pNUM = pNUM.substring(pNUM.indexOf("POLICYNUMBER>") + 13);
        pNUM = pNUM.substring(0, pNUM.indexOf("</POLICYNUMBER"));
        String policyId = pID;
        log.info("PolicyID is:" + policyId);
        String policyNumber = pNUM;
        sendToUnderwritingVoid(policyId, environment);
        log.info("PolicyNumber is: " + policyNumber);
        results.add(0, policyId);
        results.add(1, policyNumber);
        return results;
    }


    public static List<String> RequesSetReviewUnderwritingParams(String environment) throws Exception {
        String URL = requesttURL(environment);
        String changeTarget, changeValue;
        String changeTarget1 = "";
        String changeValue1 = "";
        String changeTarget2 = "";
        String changeValue2 = "";
        Date today = Calendar.getInstance().getTime();
        String yesterday = new SimpleDateFormat("yyyy-MM").format(today);
        String newYesterday = yesterday + "-01T00:00:00";
        log.info("Yesterday date is: " + newYesterday);
        String cid = String.valueOf(UUID.randomUUID());
        log.info("cid is: " + cid);
        Map<String, String> map = new HashMap<>();
        map.put("JMSCorrelationID", cid);
        changeTarget = "${POLICYID}";
        List<String> list = getTag(environment, "POLICYID");
        changeValue = list.get(2);
        String policyNumber = list.get(1);
        policyNumber = policyNumber.substring(policyNumber.indexOf("POLICYNUMBER>") + 13);
        policyNumber = policyNumber.substring(0, policyNumber.indexOf("</POLICYNUMBER"));
        log.info("changeValue is: " + changeValue);
        sendToUnderwritingVoid(changeValue, environment);
        setVehiclePhotoVoid(changeValue, environment);
        String soapAction = "http://www.vsk.ru/IPartnersPolicyService/SetReviewUnderwritingParams";
        String logPass = "ZIM";
        soapClient = SoapClientBuilder.create().withDigestAuth(logPass, logPass).build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + "setReviewUnderwritingParams.xml")), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace(changeTarget, changeValue).replace(changeTarget1, changeValue1).replace(changeTarget2, changeValue2);
        SoapResponse<String> response = soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
        String result = String.valueOf(response);
        log.info(result);
        soapClient.close();
        List<String> results = new ArrayList<>();
        results.add(0, cid);
        results.add(1, result);
        results.add(2, changeValue);
        String rID = result;
        String requestidTag = "REQUESTID xmlns=\"http://www.vsk.ru\">";
        int requestidIndex = 36;
        rID = rID.substring(rID.indexOf(requestidTag) + requestidIndex);
        rID = rID.substring(0, rID.indexOf("</REQUESTID"));
        log.info("RequestID is: " + rID);
        results.add(3, rID);
        log.info("List is made");
        results.add(4, policyNumber);
        return results;
    }


    public static List<String> RequestGUI(String fileName, String environment) throws Exception {
        List<String> preconditions = RequesSetReviewUnderwritingParams(environment);
        String policyNumber = preconditions.get(4);
        log.info("policyNumber is: " + policyNumber);
        String taskGuid;
        String taskID;
        switch (fileName) {
            case "setReviewUnderwritingResolution.xml":
                taskGuid = (requestSetUser(policyNumber, "Stage_Admin")).get(0);
                taskGuid = String.valueOf(getValuesForGivenKey("[" + taskGuid + "]", "Result"));
                taskGuid = taskGuid.replace("[", "").replace("]", "");
                log.info("TaskGuid is: " + taskGuid);
                taskID = requestTaskInfo(taskGuid);
                log.info("TaskId is: " + taskID);
                break;
            case "changeDate.xml":
            case "setPolicyStatus.xml":
                taskGuid = (requestSetUser(policyNumber, "Stage_Admin")).get(0);
                taskGuid = String.valueOf(getValuesForGivenKey("[" + taskGuid + "]", "Result"));
                taskGuid = taskGuid.replace("[", "").replace("]", "");
                log.info("TaskGuid is: " + taskGuid);
                taskID = requestTaskInfo(taskGuid);
                log.info("TaskId is: " + taskID);
                pauseMethod(30000);
                taskGuid = (requestSetUser(policyNumber, "ЗИМ ЗИМ ЗИМ")).get(0);
                taskGuid = String.valueOf(getValuesForGivenKey("[" + taskGuid + "]", "Result"));
                taskGuid = taskGuid.replace("[", "").replace("]", "");
                log.info("TaskGuid is: " + taskGuid);
                taskID = requestTaskInfo(taskGuid);
                log.info("TaskId is: " + taskID);
                requestResolution(taskID, environment);
                taskGuid = (requestSetUser(policyNumber, "Stage_Admin")).get(0);
                taskGuid = String.valueOf(getValuesForGivenKey("[" + taskGuid + "]", "Result"));
                taskGuid = taskGuid.replace("[", "").replace("]", "");
                log.info("TaskGuid is: " + taskGuid);
                taskID = requestTaskInfo(taskGuid);
                log.info("TaskId is: " + taskID);
                requestResolution(taskID, environment);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + fileName);
        }
        String URL = requesttURL(environment);
        String changeTarget = "";
        String changeValue = "";
        String changeTarget1 = "";
        String changeValue1 = "";
        String soapActionEnd = "";
        String cid = String.valueOf(UUID.randomUUID());
        Date today = Calendar.getInstance().getTime();
        String twoDaysAgo = new SimpleDateFormat("yyyy-MM").format(today);
        String newTwoDaysAgo = twoDaysAgo + "+02T00:00:00";
        log.info("New date is: " + newTwoDaysAgo);
        Map<String, String> map = new HashMap<>();
        switch (fileName) {
            case "setReviewUnderwritingResolution.xml":
                changeTarget = "${TASKID}";
                changeValue = taskID;
                soapActionEnd = "SetReviewUnderwritingResolution";
                break;
            case "setPolicyStatus.xml":
                changeTarget = "${POLICYID}";
                changeValue = preconditions.get(2);
                soapActionEnd = "SetPolicyStatus";
                break;
            case "changeDate.xml":
                changeTarget = "${POLICYID}";
                changeValue = preconditions.get(2);
                changeTarget1 = "${TIME}";
                changeValue1 = newTwoDaysAgo;
                soapActionEnd = "ChangeDate";
        }
        String soapAction = "http://www.vsk.ru/IPartnersPolicyService/" + soapActionEnd;
        soapClient = SoapClientBuilder.create().withDigestAuth("ZIM", "ZIM").build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + fileName)), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace(changeTarget, changeValue).replace(changeTarget1, changeValue1);
        SoapResponse<String> response = soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
        String result = String.valueOf(response);
        log.info(result);
        soapClient.close();
        List<String> results = new ArrayList<>();
        results.add(0, cid);
        results.add(1, result);
        results.add(2, taskID);
        log.info("List is made");
        String rID = results.get(1);
        rID = rID.substring(rID.indexOf("REQUESTID>") + 10);
        rID = rID.substring(0, rID.indexOf("</REQUESTID"));
        results.add(3, rID);
        results.add(4, bodyFinal);
        results.add(5, changeValue);
        return results;
    }


    static List<String> requestSetUser(String policyNumber, String user) throws IOException {
        String requestBody = new String(Files.readAllBytes(Paths.get(paths + "SetUserForUnderwriting.json")), StandardCharsets.UTF_8);
        requestBody = requestBody.replace("${POLICYNUMBER}", policyNumber).replace("${USER}", user);
        String cid = String.valueOf(UUID.randomUUID());
        List<String> result = new ArrayList<>();
        Response response = RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("http://autotestsrv.vsk.ru:81/B2B/UIStep/SetUserForUnderwriting/Test")
                .then()
                .statusCode(200)
                .extract()
                .response();
        String serviceResponse = response.body().asString();
        result.add(0, serviceResponse);
        System.out.println("Responce is: " + serviceResponse);
        result.add(1, cid);
        return result;
    }


    static void requestResolution(String taskId, String environment) throws Exception {
        String URL = requesttURL(environment);
        String changeTarget;
        String changeValue;
        String cid = String.valueOf(UUID.randomUUID());
        Map<String, String> map = new HashMap<>();
        map.put("JMSCorrelationID", cid);
        changeTarget = "${TASKID}";
        changeValue = taskId;
        String soapAction = "http://www.vsk.ru/IPartnersPolicyService/SendToUndewriting";
        soapClient = SoapClientBuilder.create().withDigestAuth("ZIM", "ZIM").build();
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + "setReviewUnderwritingResolution.xml")), StandardCharsets.UTF_8);
        String bodyFinal = rightBody.replace(changeTarget, changeValue);
        soapClient.call(new SoapRequest(URL, soapAction, bodyFinal, map));
    }


    static String requestTaskInfo(String taskGuid) {
        String taskID = null;
        boolean isStatusCorrect = false;
        long startMs = System.currentTimeMillis();
        while (!isStatusCorrect) {
            if (System.currentTimeMillis() - startMs >= 120000) {
                throw new RuntimeException("Expired; message was not found by the specified parameters.");
            }
            Response response = RestAssured.given().log().all()
                    .header("Content-Type", "application/json")
                    .queryParams("taskGuid", taskGuid)
                    .get("http://autotestsrv.vsk.ru:81/AsyncApiTask/TaskInfo")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();
            String taskInfoResponse = response.body().asString();
            log.info("taskInfoResponse is: " + taskInfoResponse);
            String taskInfoResult = String.valueOf(getValuesForGivenKey("[" + taskInfoResponse + "]", "Result"));
            log.info("taskInfoResult is: " + taskInfoResult);
            taskID = (String.valueOf(getValuesForGivenKey(taskInfoResult, "Result"))).replace("[", "").replace("]", "");
            String taskStatus = (String.valueOf(getValuesForGivenKey(taskInfoResult, "TaskStatus"))).replace("[", "").replace("]", "");
            log.info("taskStatus is: " + taskStatus);
            if (taskStatus.equals("Success") || taskStatus.equals("Error")) {
                isStatusCorrect = true;
            }
        }
        log.info("taskID is: " + taskID);
        return taskID;
    }
}
