package b2b;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static b2b.BaseClass.*;

public class Template {

    public static List<String> requestEuroplan(String fileName, String environment) throws Exception {
        List<String> result = Collections.singletonList("");
        try {
            result = Requests.europlanRequestMethod(fileName, environment);

        } catch (Exception e) {
            e.printStackTrace();
        }
        pauseMethod(80000);
        result.add(2, (Kibana.searchByCID(environment, result.get(0))));
        result.add(3, (Kibana.searchByParameters(environment, result.get(0), "AdapterRLS:%20after%20EuroPlan-B2B%20calling")));
        result.add(4, (Kibana.searchByParameters(environment, result.get(0), "AdapterVirtu:%20!'EUROPLAN!'%20operation%20!'CalcV2!'%20calling%20Virtu%20REST%20service%20with")));
        String bodySoap = new String(Files.readAllBytes(Paths.get(paths + fileName)), StandardCharsets.UTF_8);
        result.add(5, bodySoap);
        String kibanaRequest = Kibana.searchByParameters(environment, result.get(0), "PartnersPolicyService-Front:%20!'CalcV2!'%20calling%20with");
        kibanaRequest = kibanaRequest.substring(kibanaRequest.indexOf("<PRODUCT"));
        kibanaRequest = kibanaRequest.substring(0, kibanaRequest.indexOf("</CalcRequestV2")) + "</CalcRequestV2>";
        String blea = kibanaRequest.replace("\\n", "").replace("\\", "");
        result.add(6, "<CalcRequestV2 xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:u=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns=\"http://www.vsk.ru\">" + blea);
        String requestID = result.get(1);
        requestID = requestID.substring(requestID.indexOf("REQUESTID>") + 10);
        requestID = requestID.substring(0, requestID.indexOf("</REQUESTID"));
        String kibanaRequest2 = Kibana.searchByParameters(environment, requestID, "PartnersPolicyService:%20!'CalcV2!'%20virtu%20responded");
        kibanaRequest2 = kibanaRequest2.substring(kibanaRequest2.indexOf("<CalcResponse"));
        kibanaRequest2 = kibanaRequest2.substring(0, kibanaRequest2.indexOf("</CalcResponse")) + "</CalcResponse>";
        result.add(7, kibanaRequest2.replace("\\", ""));
        return result;
    }


    public static List<String> requestIPartnersPolicyService(String fileName, String environment) throws Exception {
        List<String> result = Collections.singletonList("");
        try {
            result = Requests.RequestMethodAll(fileName, environment);

        } catch (Exception e) {
            e.printStackTrace();
        }
        pauseMethod(85000);
        result.add(4, (Kibana.searchByParameters(environment, result.get(2), "PartnersPolicyService")));
        result.add(5, (Kibana.searchByParameters(environment, result.get(3), "PartnersPolicyService")));
        String logBody = result.get(5);
        logBody = logBody.substring(logBody.indexOf("Response") + 10);
        logBody = logBody.substring(0, logBody.indexOf("\",\""));
        result.add(6, logBody.replace("\\", ""));
        return result;
    }


    public static List<String> caseUpdatePolicyUndewritingStatusNoLogging(String fileName, String environment) throws Exception {
        List<String> result = Collections.singletonList("");
        try {
            result = Requests.RequestUpdatePolicyUndewritingStatusNoLogging(fileName, environment);

        } catch (Exception e) {
            e.printStackTrace();
        }
        pauseMethod(80000);
        String kibanaRequest = Kibana.searchByParameters(environment, result.get(2), "PartnersPolicyService-Front:%20!'UpdatePolicyUnderwritingStatus!'%20calling%20with");
        kibanaRequest = kibanaRequest.substring(kibanaRequest.indexOf("<vsk:UpdatePolicyUnderwritingStatusRequest"));
        kibanaRequest = kibanaRequest.substring(0, kibanaRequest.indexOf("UpdatePolicyUnderwritingStatusRequest>")) + "UpdatePolicyUnderwritingStatusRequest>";
        result.add(4, kibanaRequest);
        return result;
    }


    public static List<String> caseGetPaymentsWithPaidStatus(String fileName, String environment) throws Exception {
        List<String> result = Collections.singletonList("");
        try {
            result = Requests.RequestGetPaymentsWithPaidStatus(fileName, environment);

        } catch (Exception e) {
            e.printStackTrace();
        }
        pauseMethod(80000);
        String kibanaRequest = Kibana.searchByParameters(environment, result.get(0), "PartnersPolicyService-Front:%20!'GetPaymentsWithPaidStatus!'%20calling%20with");
        switch (environment) {
            case "stage":
                kibanaRequest = kibanaRequest.substring(kibanaRequest.indexOf("<PartnerGetPaymentsWithPaidStatusRequest"));
                kibanaRequest = kibanaRequest.substring(0, kibanaRequest.indexOf("PartnerGetPaymentsWithPaidStatusRequest>")) + "PartnerGetPaymentsWithPaidStatusRequest>";
                break;
            case "test":
                kibanaRequest = kibanaRequest.substring(kibanaRequest.indexOf("<vsk:PartnerGetPaymentsWithPaidStatusRequest"));
                kibanaRequest = kibanaRequest.substring(0, kibanaRequest.indexOf("PartnerGetPaymentsWithPaidStatusRequest>")) + "PartnerGetPaymentsWithPaidStatusRequest>";
                break;
        }
        result.add(3, kibanaRequest.replace("\\n", "").replace("\\", ""));
        String requestID = result.get(1);
        requestID = requestID.substring(requestID.indexOf("REQUESTID>") + 10);
        requestID = requestID.substring(0, requestID.indexOf("</REQUESTID"));
        log.info("requestID is " + requestID);
        String kibanaRequest2 = Kibana.searchByParameters(environment, requestID, "PartnersPolicyService:%20!'GetPaymentsWithPaidStatus!'%20virtu%20responded");
        kibanaRequest2 = kibanaRequest2.substring(kibanaRequest2.indexOf("<PartnerGetPaymentsWithPaidStatusResponse"));
        kibanaRequest2 = kibanaRequest2.substring(0, kibanaRequest2.indexOf("</PartnerGetPaymentsWithPaidStatusResponse>")) + "</PartnerGetPaymentsWithPaidStatusResponse>";
        result.add(4, kibanaRequest2.replace("\\n", "").replace("\\", ""));
        String soapResponse = result.get(1);
        soapResponse = soapResponse.substring(soapResponse.indexOf("<PartnerGetPaymentsWithPaidStatusResponse"));
        soapResponse = soapResponse.substring(0, soapResponse.indexOf("</PartnerGetPaymentsWithPaidStatusResponse>")) + "</PartnerGetPaymentsWithPaidStatusResponse>";
        result.add(5, soapResponse);
        return result;
    }


    public static List<String> requestProlongatePolicy(String fileName, String environment) throws Exception {
        List<String> result = Collections.singletonList("");
        try {
            result = Requests.RequestGetPaymentsWithPaidStatus(fileName, environment);

        } catch (Exception e) {
            e.printStackTrace();
        }
        pauseMethod(80000);
        String kibanaRequest = Kibana.searchByParameters(environment, result.get(0), "PartnersPolicyService-Front:%20!'ProlongatePolicy!'%20calling%20with");
        kibanaRequest = kibanaRequest.substring(kibanaRequest.indexOf("<vsk:ProlongatePolicyRequest"));
        kibanaRequest = kibanaRequest.substring(0, kibanaRequest.indexOf("ProlongatePolicyRequest>")) + "ProlongatePolicyRequest>";
        result.add(3, kibanaRequest.replace("\\n", "").replace("\\", ""));
        String requestID = result.get(1);
        requestID = requestID.substring(requestID.indexOf("REQUESTID>") + 10);
        requestID = requestID.substring(0, requestID.indexOf("</REQUESTID"));
        log.info("requestID is " + requestID);
        String kibanaRequest2 = Kibana.searchByParameters(environment, requestID, "PartnersPolicyService:%20!'ProlongatePolicy!'%20virtu%20responded");
        kibanaRequest2 = kibanaRequest2.substring(kibanaRequest2.indexOf("<ProlongatePolicyResponse"));
        kibanaRequest2 = kibanaRequest2.substring(0, kibanaRequest2.indexOf("</ProlongatePolicyResponse>")) + "</ProlongatePolicyResponse>";
        result.add(4, kibanaRequest2.replace("\\n", "").replace("\\", ""));
        String soapResponse = result.get(1);
        soapResponse = soapResponse.substring(soapResponse.indexOf("<ProlongatePolicyResponse"));
        soapResponse = soapResponse.substring(0, soapResponse.indexOf("</ProlongatePolicyResponse>")) + "</ProlongatePolicyResponse>";
        result.add(5, soapResponse);
        return result;
    }


    public static List<String> requestGetFranchise(String fileName, String environment) throws Exception {
        List<String> result = Collections.singletonList("");
        try {
            result = Requests.RequestMethodAll(fileName, environment);

        } catch (Exception e) {
            e.printStackTrace();
        }
        pauseMethod(80000);
        String kibanaRequest = Kibana.searchByParameters(environment, result.get(0), "PartnersPolicyService-Front:%20!'GetFranchise!'%20calling%20with");
        kibanaRequest = kibanaRequest.substring(kibanaRequest.indexOf("<vsk:GetFranchiseRequest"));
        kibanaRequest = kibanaRequest.substring(0, kibanaRequest.indexOf("GetFranchiseRequest>")) + "GetFranchiseRequest>";
        result.add(3, kibanaRequest.replace("\\n", "").replace("\\", ""));
        String requestID = result.get(1);
        requestID = requestID.substring(requestID.indexOf("REQUESTID>") + 10);
        requestID = requestID.substring(0, requestID.indexOf("</REQUESTID"));
        log.info("requestID is " + requestID);
        String kibanaRequest2 = Kibana.searchByParameters(environment, requestID, "PartnersPolicyService:%20!'GetFranchise!'%20virtu%20responded");
        kibanaRequest2 = kibanaRequest2.substring(kibanaRequest2.indexOf("<GetFranchiseResponse"));
        kibanaRequest2 = kibanaRequest2.substring(0, kibanaRequest2.indexOf("</GetFranchiseResponse>")) + "</GetFranchiseResponse>";
        result.add(4, kibanaRequest2.replace("\\n", "").replace("\\", ""));
        String soapResponse = result.get(1);
        soapResponse = soapResponse.substring(soapResponse.indexOf("<GetFranchiseResponse"));
        soapResponse = soapResponse.substring(0, soapResponse.indexOf("</GetFranchiseResponse>")) + "</GetFranchiseResponse>";
        result.add(5, soapResponse);
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + fileName)), StandardCharsets.UTF_8);
        result.add(6, rightBody);
        return result;
    }


    public static List<String> requestSetUnderStatus(String environment) throws Exception {
        List<String> result = Collections.singletonList("");
        try {
            result = Requests.RequestSetUnderStatus(environment);

        } catch (Exception e) {
            e.printStackTrace();
        }
        pauseMethod(180000);
        result.add(2, (Kibana.searchByParameters(environment, result.get(1), "AdapterVSKshop:%20!'SetUnderStatus!'%20calling%20REST%20service")));
        result.add(3, (Kibana.searchByParameters(environment, result.get(1), "SetUnderStatus")));
        return result;
    }


    public static List<String> requestGetCarCost(String filename, String environment) throws Exception {
        List<String> result = Collections.singletonList("");
        try {
            result = Requests.RequestMethodAll(filename, environment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pauseMethod(60000);
        String rightBody = new String(Files.readAllBytes(Paths.get(paths + filename)), StandardCharsets.UTF_8);
        String ID = rightBody;
        ID = ID.substring(ID.indexOf("<ws:Id>") + 7);
        ID = ID.substring(0, ID.indexOf("</ws:Id"));
        String kibanaRequest1 = Kibana.searchByParameters(environment, ID, "PartnersPolicyService-Front:%20!'GetCarCost!'%20calling%20with");
        kibanaRequest1 = kibanaRequest1.substring(kibanaRequest1.indexOf("<vsk:PRODUCT"));
        kibanaRequest1 = kibanaRequest1.substring(0, kibanaRequest1.indexOf("</vsk:GetCarCostRequest>")) + "</vsk:GetCarCostRequest>";
        String searchElement = "<vsk:GetCarCostRequest xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:vsk=\"http://www.vsk.ru\" xmlns:ws=\"http://ws.rls10\">";
        result.add(4, searchElement + kibanaRequest1.replace("\\n", "").replace("\\", ""));
        String kibanaRequest2 = Kibana.searchByParameters(environment, result.get(3), "PartnersPolicyService:%20!'GetCarCost!'%20virtu%20responded");
        kibanaRequest2 = kibanaRequest2.substring(kibanaRequest2.indexOf("<GetCarCostResponse"));
        kibanaRequest2 = kibanaRequest2.substring(0, kibanaRequest2.indexOf("</GetCarCostResponse>")) + "</GetCarCostResponse>";
        result.add(5, kibanaRequest2.replace("\\", ""));
        String respSoap = rightBody;
        respSoap = respSoap.substring(respSoap.indexOf("<vsk:GetCarCostRequest"));
        respSoap = respSoap.substring(0, respSoap.indexOf("</vsk:GetCarCostRequest>")) + "</vsk:GetCarCostRequest>";
        result.add(6, respSoap);
        return result;
    }

    public static List<String> b2bGUI(String fileName, String environment) {
        List<String> result = Collections.singletonList("");
        try {
            result = Requests.RequestGUI(fileName, environment);

        } catch (Exception e) {
            e.printStackTrace();
        }
        pauseMethod(95000);
        switch (fileName) {
            case "setReviewUnderwritingResolution.xml":
                result.add(6, (Kibana.searchByParameters(environment, result.get(2), "PartnersPolicyService-Front:%20!'SetReviewUnderwritingResolution!'%20calling%20with")));
                result.add(7, (Kibana.searchByParameters(environment, result.get(3), "PartnersPolicyService:%20!'SetReviewUnderwritingResolution!'%20virtu%20responded")));
                break;
            case "setPolicyStatus.xml":
                result.add(6, (Kibana.searchByParameters(environment, result.get(5), "PartnersPolicyService-Front:%20!'SetPolicyStatus!'%20calling%20with")));
                result.add(7, (Kibana.searchByParameters(environment, result.get(3), "PartnersPolicyService:%20!'SetPolicyStatus!'%20virtu%20responded")));
                break;
            case "changeDate.xml":
                result.add(6, (Kibana.searchByParameters(environment, result.get(5), "PartnersPolicyService-Front:%20!'ChangeDate!'%20calling%20with")));
                result.add(7, (Kibana.searchByParameters(environment, result.get(3), "PartnersPolicyService:%20!'ChangeDate!'%20virtu%20responded")));
                break;
        }
        return result;
    }
}

