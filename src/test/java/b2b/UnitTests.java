package b2b;

import org.custommonkey.xmlunit.XMLUnit;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.xmlunit.matchers.CompareMatcher;

import java.util.List;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;


public class UnitTests extends BaseClass {


    @BeforeEach
    public void executedBeforeEach(TestInfo testInfo) {
        log.info("\n...Starting test: " + testInfo.getDisplayName());
    }

    @AfterEach
    public void executedAfterEach() {

        log.info("End test\n\n");
    }

    @Test
    public void CalcV2_SuccessfulResponce_COMPLETED() throws Exception {
        List<String> response = Template.requestEuroplan("Evroplan_SuccessResponse.xml", environment);
        switch (environment) {
            case "stage":
                assertThat(response.get(1), containsString("<status>1</status>"));
                assertThat(response.get(1), containsString("<SUCCESS>true</SUCCESS>"));
                break;
            case "test":
                assertThat(response.get(1), containsString("<status>0</status>"));
                assertThat(response.get(1), containsString("<SUCCESS>false</SUCCESS>"));
                break;
        }
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual(response.get(5), response.get(6));
        assertThat(response.get(1), containsString(response.get(7)));
        org.junit.Assert.assertThat(response.get(5), CompareMatcher.isSimilarTo(response.get(6)).ignoreWhitespace().ignoreComments());
    }

    @Test
    public void CalcV2_RLSmapping_COMPLETED() throws Exception {
        List<String> responce = Template.requestEuroplan("Evroplan_SuccessResponse.xml", environment);
        assertThat(responce.get(2), containsString("AdapterRLS: call RLS EuroPlan->B2B"));
        assertThat(responce.get(2), containsString("AdapterRLS: before EuroPlan->B2B calling"));
        assertThat(responce.get(2), containsString("AdapterRLS: use RLS 'WebAutoPolicyProcessor'"));
        assertThat(responce.get(2), containsString("AdapterRLS: after EuroPlan->B2B calling"));
        assertThat(responce.get(2), containsString("AdapterVirtu: 'EUROPLAN' operation 'CalcV2' calling Virtu REST service with"));
        switch (environment) {
            case "stage":
                assertThat(responce.get(2), containsString("PartnersPolicyService AMQ CLUSTER DMZ: 'CalcV2' message successfully sent AMQ onto queue 'virtu.sys.in.queue'"));
                break;
            case "test":
                assertThat(responce.get(2), containsString("PartnersPolicyService AMQ CLUSTER: 'CalcV2' message successfully sent onto queue 'virtu.sys.in.queue'"));
                break;
        }
        assertThat(responce.get(2), containsString("PartnersPolicyService-Front: Operation 'CalcV2' complete"));
        assertThat(responce.get(3), containsString("<Id xmlns=\\\"http://ws.rls10\\\">AB64EBD6-8E51-4CDF-AD89-47585FFE3273</Id>"));
        assertThat(responce.get(3), containsString("<SysId xmlns=\\\"http://ws.rls10\\\">B2B</SysId>"));
        assertThat(responce.get(3), containsString("<Type xmlns=\\\"http://ws.rls10\\\">LINK_VEHICLE_MOD</Type>"));
        assertThat(responce.get(4), containsString("<Id xmlns=\\\"http://ws.rls10\\\">AB64EBD6-8E51-4CDF-AD89-47585FFE3273</Id>"));
        assertThat(responce.get(4), containsString("<SysId xmlns=\\\"http://ws.rls10\\\">B2B</SysId>"));
        assertThat(responce.get(4), containsString("<Type xmlns=\\\"http://ws.rls10\\\">LINK_VEHICLE_MOD</Type>"));
    }

    @Test
    public void CreatePolicyV2_COMPLETED() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("CreatePolicyV2.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void CreatePolicyV2tags_COMPLETED() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("createPolicyV2tags.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void CalcV2_allPREVIOUSPOLICYFROMOTHERINSCOMPANY_EXPIRATIONDATE() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("zimAll.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetTaskStatus_COMPLETED() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getTaskStatus.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void UpdatePolicyV2_allPREVIOUSPOLICYFROMOTHERINSCOMPANYEXPIRATIONDATE() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("updatePolicyPrevious.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetPolicyStatus() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getPolicyStatus.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void ResetUnderResults() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("stopUnderwriting.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetVehiclePhoto() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getVehiclePhoto.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void PrintDocument() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getPrintForm.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void CalcAndSavePayments() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("calcAndSavePaymentsZIM.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void AnnulatePayment() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("AnnulatedPaymentZIM.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void UpdatePolicyUndewritingStatusNoLogging() throws Exception {
        List<String> response = Template.caseUpdatePolicyUndewritingStatusNoLogging("UpdatePolicyUnderwritingStatusCalback.xml", environment);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLIdentical(response.get(4), containsString(response.get(3)));
    }

    private void assertXMLIdentical(String s, Matcher<String> containsString) {
    }

    @Test
    public void PrintPaymentDocument() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("PrintPaymentDocumentZIM.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void SetPolicyPaymentBinding() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("SetPolicyPaymentBinding.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void SetUnderStatus_return_PolicyNumber() throws Exception {
        List<String> responce = Template.requestSetUnderStatus(environment);
        assertThat(responce.get(2), containsString(responce.get(0)));
        assertThat(responce.get(3), containsString("<SetUnderStatus"));
        assertThat(responce.get(3), containsString(responce.get(1)));
        assertThat(responce.get(3), containsString("</SetUnderStatus"));
    }

    @Test
    public void ProlongatePolicy() throws Exception {
        List<String> responce = Template.requestProlongatePolicy("prolongatePolicy.xml", environment);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual(responce.get(3), responce.get(2));
        assertXMLEqual(responce.get(4), responce.get(5));
    }

    @Test
    public void GetPolicy() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getPolicy.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void SavePayments() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("savePayments.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetPaymentsWithPaidStatus() throws Exception {
        String fileName;
        switch (environment) {
            case "test":
                fileName = "GetPaymentsWithPaidStatusTest.xml";
                break;
            case "stage":
                fileName = "GetPaymentsWithPaidStatusStage.xml";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + environment);
        }
        List<String> responce = Template.caseGetPaymentsWithPaidStatus(fileName, environment);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual(responce.get(3), responce.get(2));
        assertXMLEqual(responce.get(4), responce.get(5));
    }


    @Test
    public void SendToUnderwriting() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("SendToUndewriting.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void SendToUnderwritingWithTasks() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("SendToUnderwritingWithTasks.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetPolicyPrintForms() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("GetPolicyPrintForms.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void CalcV2_RequestWithrRequiredTags() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("ZIM.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void GetVehiclePhotoFileList() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getVehiclePhotoFileList.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void DeleteVehiclePhoto() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("deleteVehiclePhoto.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetFranchise() throws Exception {
        String fileName;
        switch (environment) {
            case "stage":
                fileName = "getFranchiseStage.xml";
                break;
            case "test":
                fileName = "getFranchiseTest.xml";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + environment);
        }
        List<String> response = Template.requestGetFranchise(fileName, environment);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual(response.get(6), response.get(3));
        switch (environment) {
            case "stage":
                assertXMLEqual(response.get(4).replace("&#xD", ""), response.get(5).replace("&#13", ""));
                break;
            case "test":
                assertThat(response.get(4).replace("&#xD", ""), containsString("<status>0</status>"));
                assertThat(response.get(4).replace("&#xD", ""), containsString("Value cannot be null.;"));
                assertThat(response.get(4).replace("&#xD", ""), containsString("Parameter name: source</message></errors><SUCCESS>false</SUCCESS><MESSAGE>Value cannot be null.;"));
                assertThat(response.get(5).replace("&#13", ""), containsString("<status>0</status>"));
                assertThat(response.get(5).replace("&#13", ""), containsString("Value cannot be null.;"));
                assertThat(response.get(5).replace("&#13", ""), containsString("Parameter name: source</message></errors><SUCCESS>false</SUCCESS><MESSAGE>Value cannot be null.;"));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + environment);
        }


    }


    @Test
    public void UpdatePolicyV2withRequiredTags() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("updatePolicyTags.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


//    @Test
//    public void GetCarCost() throws Exception {
//        List<String> response = Template.requestGetCarCost("getCarCost.xml", environment);
//        XMLUnit.setIgnoreComments(true);
//        XMLUnit.setIgnoreWhitespace(true);
//        org.junit.Assert.assertThat(response.get(6), CompareMatcher.isSimilarTo(response.get(4)).ignoreWhitespace().ignoreComments());
//        assertThat(response.get(1), containsString(response.get(5)));
//    }


    @Test
    public void ChangePaymentMethod() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("changePaymentMethod.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void IssuePaymentMethod() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("IssuePayment.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void SetVehiclePhoto() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("setVehiclePhoto.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void SetReviewUnderwritingParams() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("setReviewUnderwritingParams.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void SetReviewUnderwritingResolution() throws Exception {
        List<String> responce = Template.b2bGUI("setReviewUnderwritingResolution.xml", environment);
        assertThat(responce.get(1), containsString("<BaseResponse"));
        assertThat(responce.get(5), containsString(responce.get(4)));
        assertThat(responce.get(6), containsString(responce.get(1)));
    }

//    @Test
//    public void ChangeDate() throws Exception {
//        List<String> responce = Template.b2bGUI("changeDate.xml", environment);
//        assertThat(responce.get(1), containsString("<ChangeDateResponse"));
//        assertThat(responce.get(5), containsString(responce.get(4)));
//        assertThat(responce.get(6), containsString(responce.get(1)));
//    }

//    @Test
//    public void SetPolicyStatus() throws Exception {
//        List<String> responce = Template.b2bGUI("setPolicyStatus.xml", environment);
//        assertThat(responce.get(1), containsString("<SetPolicyStatusResponse"));
//        assertThat(responce.get(5), containsString(responce.get(4)));
//        assertThat(responce.get(6), containsString(responce.get(1)));
//    }
}
class ESBRelease extends BaseClass {
    @BeforeEach
    public void executedBeforeEach(TestInfo testInfo) {
        log.info("\n...Starting test: " + testInfo.getDisplayName());
    }

    @AfterEach
    public void executedAfterEach() {

        log.info("End test\n\n");
    }
    @Test
    public void CalcV2_SuccessfulResponce_COMPLETED() throws Exception {
        List<String> response = Template.requestEuroplan("Evroplan_SuccessResponse.xml", environment);
        switch (environment) {
            case "stage":
                assertThat(response.get(1), containsString("<status>1</status>"));
                assertThat(response.get(1), containsString("<SUCCESS>true</SUCCESS>"));
                break;
            case "test":
                assertThat(response.get(1), containsString("<status>0</status>"));
                assertThat(response.get(1), containsString("<SUCCESS>false</SUCCESS>"));
                break;
        }

        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual(response.get(5), response.get(6));
        assertThat(response.get(1), containsString(response.get(7)));
        org.junit.Assert.assertThat(response.get(5), CompareMatcher.isSimilarTo(response.get(6)).ignoreWhitespace().ignoreComments());
    }

    @Test
    public void CalcV2_RLSmapping_COMPLETED() throws Exception {
        List<String> responce = Template.requestEuroplan("Evroplan_SuccessResponse.xml", environment);
        assertThat(responce.get(2), containsString("AdapterRLS: call RLS EuroPlan->B2B"));
        assertThat(responce.get(2), containsString("AdapterRLS: before EuroPlan->B2B calling"));
        assertThat(responce.get(2), containsString("AdapterRLS: use RLS 'WebAutoPolicyProcessor'"));
        assertThat(responce.get(2), containsString("AdapterRLS: after EuroPlan->B2B calling"));
        assertThat(responce.get(2), containsString("AdapterVirtu: 'EUROPLAN' operation 'CalcV2' calling Virtu REST service with"));
        switch (environment) {
            case "stage":
                assertThat(responce.get(2), containsString("PartnersPolicyService AMQ CLUSTER DMZ: 'CalcV2' message successfully sent AMQ onto queue 'virtu.sys.in.queue'"));
                break;
            case "test":
                assertThat(responce.get(2), containsString("PartnersPolicyService AMQ CLUSTER: 'CalcV2' message successfully sent onto queue 'virtu.sys.in.queue'"));
                break;
        }
        assertThat(responce.get(2), containsString("PartnersPolicyService-Front: Operation 'CalcV2' complete"));
        assertThat(responce.get(3), containsString("<Id xmlns=\\\"http://ws.rls10\\\">AB64EBD6-8E51-4CDF-AD89-47585FFE3273</Id>"));
        assertThat(responce.get(3), containsString("<SysId xmlns=\\\"http://ws.rls10\\\">B2B</SysId>"));
        assertThat(responce.get(3), containsString("<Type xmlns=\\\"http://ws.rls10\\\">LINK_VEHICLE_MOD</Type>"));
        assertThat(responce.get(4), containsString("<Id xmlns=\\\"http://ws.rls10\\\">AB64EBD6-8E51-4CDF-AD89-47585FFE3273</Id>"));
        assertThat(responce.get(4), containsString("<SysId xmlns=\\\"http://ws.rls10\\\">B2B</SysId>"));
        assertThat(responce.get(4), containsString("<Type xmlns=\\\"http://ws.rls10\\\">LINK_VEHICLE_MOD</Type>"));
    }

    @Test
    public void CalcV2_allPREVIOUSPOLICYFROMOTHERINSCOMPANY_EXPIRATIONDATE() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("zimAll.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void CalcV2_RequestWithrRequiredTags() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("ZIM.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetTaskStatus_COMPLETED() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getTaskStatus.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void SendToUnderwritingWithTasks() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("SendToUnderwritingWithTasks.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void UpdatePolicyV2withRequiredTags() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("updatePolicyTags.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void UpdatePolicyV2_allPREVIOUSPOLICYFROMOTHERINSCOMPANYEXPIRATIONDATE() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("updatePolicyPrevious.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void GetPolicyStatus() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getPolicyStatus.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void GetCarCost() throws Exception {
        List<String> response = Template.requestGetCarCost("getCarCost.xml", environment);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        org.junit.Assert.assertThat(response.get(6), CompareMatcher.isSimilarTo(response.get(4)).ignoreWhitespace().ignoreComments());
        assertThat(response.get(1), containsString(response.get(5)));
    }

    @Test
    public void ResetUnderResults() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("stopUnderwriting.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void DeleteVehiclePhoto() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("deleteVehiclePhoto.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetVehiclePhoto() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getVehiclePhoto.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetVehiclePhotoFileList() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getVehiclePhotoFileList.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void PrintDocument() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getPrintForm.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void CalcAndSavePayments() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("calcAndSavePaymentsZIM.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void AnnulatePayment() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("AnnulatedPaymentZIM.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void UpdatePolicyUndewritingStatusNoLogging() throws Exception {
        List<String> response = Template.caseUpdatePolicyUndewritingStatusNoLogging("UpdatePolicyUnderwritingStatusCalback.xml", environment);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLIdentical(response.get(4), containsString(response.get(3)));
    }

    private void assertXMLIdentical(String s, Matcher<String> containsString) {
    }


    @Test
    public void PrintPaymentDocument() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("PrintPaymentDocumentZIM.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void SetPolicyPaymentBinding() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("SetPolicyPaymentBinding.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    //    @Test
//    public void ChangeDate() throws Exception {
//        List<String> responce = Template.b2bGUI("changeDate.xml", environment);
//        assertThat(responce.get(1), containsString("<ChangeDateResponse"));
//        assertThat(responce.get(5), containsString(responce.get(4)));
//        assertThat(responce.get(6), containsString(responce.get(1)));
//    }

//    @Test
//    public void SetPolicyStatus() throws Exception {
//        List<String> responce = Template.b2bGUI("setPolicyStatus.xml", environment);
//        assertThat(responce.get(1), containsString("<SetPolicyStatusResponse"));
//        assertThat(responce.get(5), containsString(responce.get(4)));
//        assertThat(responce.get(6), containsString(responce.get(1)));
//    }


    @Test
    public void ChangePaymentMethod() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("changePaymentMethod.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetPolicyPrintForms() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("GetPolicyPrintForms.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void SetUnderStatus_return_PolicyNumber() throws Exception {
        List<String> responce = Template.requestSetUnderStatus(environment);
        assertThat(responce.get(2), containsString(responce.get(0)));
        assertThat(responce.get(3), containsString("<SetUnderStatus"));
        assertThat(responce.get(3), containsString(responce.get(1)));
        assertThat(responce.get(3), containsString("</SetUnderStatus"));
    }


    @Test
    public void GetPolicy() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getPolicy.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetPaymentsWithPaidStatus() throws Exception {
        String fileName;
        switch (environment) {
            case "test":
                fileName = "GetPaymentsWithPaidStatusTest.xml";
                break;
            case "stage":
                fileName = "GetPaymentsWithPaidStatusStage.xml";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + environment);
        }
        List<String> responce = Template.caseGetPaymentsWithPaidStatus(fileName, environment);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual(responce.get(3), responce.get(2));
        assertXMLEqual(responce.get(4), responce.get(5));
    }

    @Test
    public void CreatePolicyV2_COMPLETED() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("CreatePolicyV2.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }
}

class ESBHotfix extends BaseClass {
    @BeforeEach
    public void executedBeforeEach(TestInfo testInfo) {
        log.info("Starting test: " + testInfo.getDisplayName());
    }

    @AfterEach
    public void executedAfterEach() {

        log.info("End test\n");
    }
    @Test
    public void CalcV2_SuccessfulResponce_COMPLETED() throws Exception {
        List<String> response = Template.requestEuroplan("Evroplan_SuccessResponse.xml", environment);
        switch (environment) {
            case "stage":
                assertThat(response.get(1), containsString("<status>1</status>"));
                assertThat(response.get(1), containsString("<SUCCESS>true</SUCCESS>"));
                break;
            case "test":
                assertThat(response.get(1), containsString("<status>0</status>"));
                assertThat(response.get(1), containsString("<SUCCESS>false</SUCCESS>"));
                break;
        }

        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual(response.get(5), response.get(6));
        assertThat(response.get(1), containsString(response.get(7)));
        org.junit.Assert.assertThat(response.get(5), CompareMatcher.isSimilarTo(response.get(6)).ignoreWhitespace().ignoreComments());
    }

    @Test
    public void CalcV2_RLSmapping_COMPLETED() throws Exception {
        List<String> responce = Template.requestEuroplan("Evroplan_SuccessResponse.xml", environment);
        assertThat(responce.get(2), containsString("AdapterRLS: call RLS EuroPlan->B2B"));
        assertThat(responce.get(2), containsString("AdapterRLS: before EuroPlan->B2B calling"));
        assertThat(responce.get(2), containsString("AdapterRLS: use RLS 'WebAutoPolicyProcessor'"));
        assertThat(responce.get(2), containsString("AdapterRLS: after EuroPlan->B2B calling"));
        assertThat(responce.get(2), containsString("AdapterVirtu: 'EUROPLAN' operation 'CalcV2' calling Virtu REST service with"));
        switch (environment) {
            case "stage":
                assertThat(responce.get(2), containsString("PartnersPolicyService AMQ CLUSTER DMZ: 'CalcV2' message successfully sent AMQ onto queue 'virtu.sys.in.queue'"));
                break;
            case "test":
                assertThat(responce.get(2), containsString("PartnersPolicyService AMQ CLUSTER: 'CalcV2' message successfully sent onto queue 'virtu.sys.in.queue'"));
                break;
        }
        assertThat(responce.get(2), containsString("PartnersPolicyService-Front: Operation 'CalcV2' complete"));
        assertThat(responce.get(3), containsString("<Id xmlns=\\\"http://ws.rls10\\\">AB64EBD6-8E51-4CDF-AD89-47585FFE3273</Id>"));
        assertThat(responce.get(3), containsString("<SysId xmlns=\\\"http://ws.rls10\\\">B2B</SysId>"));
        assertThat(responce.get(3), containsString("<Type xmlns=\\\"http://ws.rls10\\\">LINK_VEHICLE_MOD</Type>"));
        assertThat(responce.get(4), containsString("<Id xmlns=\\\"http://ws.rls10\\\">AB64EBD6-8E51-4CDF-AD89-47585FFE3273</Id>"));
        assertThat(responce.get(4), containsString("<SysId xmlns=\\\"http://ws.rls10\\\">B2B</SysId>"));
        assertThat(responce.get(4), containsString("<Type xmlns=\\\"http://ws.rls10\\\">LINK_VEHICLE_MOD</Type>"));
    }

    @Test
    public void CalcV2_allPREVIOUSPOLICYFROMOTHERINSCOMPANY_EXPIRATIONDATE() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("zimAll.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }



    @Test
    public void GetTaskStatus_COMPLETED() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getTaskStatus.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void SendToUnderwritingWithTasks() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("SendToUnderwritingWithTasks.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }




    @Test
    public void UpdatePolicyV2_allPREVIOUSPOLICYFROMOTHERINSCOMPANYEXPIRATIONDATE() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("updatePolicyPrevious.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void GetPolicyStatus() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getPolicyStatus.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void GetCarCost() throws Exception {
        List<String> response = Template.requestGetCarCost("getCarCost.xml", environment);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        org.junit.Assert.assertThat(response.get(6), CompareMatcher.isSimilarTo(response.get(4)).ignoreWhitespace().ignoreComments());
        assertThat(response.get(1), containsString(response.get(5)));
    }

    @Test
    public void ResetUnderResults() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("stopUnderwriting.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void DeleteVehiclePhoto() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("deleteVehiclePhoto.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetVehiclePhoto() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getVehiclePhoto.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetVehiclePhotoFileList() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getVehiclePhotoFileList.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void PrintDocument() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getPrintForm.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void CalcAndSavePayments() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("calcAndSavePaymentsZIM.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void AnnulatePayment() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("AnnulatedPaymentZIM.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void UpdatePolicyUndewritingStatusNoLogging() throws Exception {
        List<String> response = Template.caseUpdatePolicyUndewritingStatusNoLogging("UpdatePolicyUnderwritingStatusCalback.xml", environment);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLIdentical(response.get(4), containsString(response.get(3)));
    }

    private void assertXMLIdentical(String s, Matcher<String> containsString) {
    }


    @Test
    public void PrintPaymentDocument() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("PrintPaymentDocumentZIM.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void SetPolicyPaymentBinding() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("SetPolicyPaymentBinding.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    //    @Test
//    public void ChangeDate() throws Exception {
//        List<String> responce = Template.b2bGUI("changeDate.xml", environment);
//        assertThat(responce.get(1), containsString("<ChangeDateResponse"));
//        assertThat(responce.get(5), containsString(responce.get(4)));
//        assertThat(responce.get(6), containsString(responce.get(1)));
//    }

//    @Test
//    public void SetPolicyStatus() throws Exception {
//        List<String> responce = Template.b2bGUI("setPolicyStatus.xml", environment);
//        assertThat(responce.get(1), containsString("<SetPolicyStatusResponse"));
//        assertThat(responce.get(5), containsString(responce.get(4)));
//        assertThat(responce.get(6), containsString(responce.get(1)));
//    }


    @Test
    public void ChangePaymentMethod() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("changePaymentMethod.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetPolicyPrintForms() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("GetPolicyPrintForms.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void SetUnderStatus_return_PolicyNumber() throws Exception {
        List<String> responce = Template.requestSetUnderStatus(environment);
        assertThat(responce.get(2), containsString(responce.get(0)));
        assertThat(responce.get(3), containsString("<SetUnderStatus"));
        assertThat(responce.get(3), containsString(responce.get(1)));
        assertThat(responce.get(3), containsString("</SetUnderStatus"));
    }


    @Test
    public void GetPolicy() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("getPolicy.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void GetPaymentsWithPaidStatus() throws Exception {
        String fileName;
        switch (environment) {
            case "test":
                fileName = "GetPaymentsWithPaidStatusTest.xml";
                break;
            case "stage":
                fileName = "GetPaymentsWithPaidStatusStage.xml";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + environment);
        }
        List<String> responce = Template.caseGetPaymentsWithPaidStatus(fileName, environment);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual(responce.get(3), responce.get(2));
        assertXMLEqual(responce.get(4), responce.get(5));
    }

    @Test
    public void CreatePolicyV2_COMPLETED() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("CreatePolicyV2.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }
}

class ESBSmoke extends BaseClass {
    @BeforeEach
    public void executedBeforeEach(TestInfo testInfo) {
        log.info("\n...Starting test: " + testInfo.getDisplayName());
    }

    @AfterEach
    public void executedAfterEach() {

        log.info("End test\n\n");
    }

    @Test
    public void CalcV2_SuccessfulResponce_COMPLETED() throws Exception {
        List<String> response = Template.requestEuroplan("Evroplan_SuccessResponse.xml", environment);
        switch (environment) {
            case "stage":
                assertThat(response.get(1), containsString("<status>1</status>"));
                assertThat(response.get(1), containsString("<SUCCESS>true</SUCCESS>"));
                break;
            case "test":
                assertThat(response.get(1), containsString("<status>0</status>"));
                assertThat(response.get(1), containsString("<SUCCESS>false</SUCCESS>"));
                break;
        }

        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual(response.get(5), response.get(6));
        assertThat(response.get(1), containsString(response.get(7)));
        org.junit.Assert.assertThat(response.get(5), CompareMatcher.isSimilarTo(response.get(6)).ignoreWhitespace().ignoreComments());
    }

    @Test
    public void CalcV2_RLSmapping_COMPLETED() throws Exception {
        List<String> responce = Template.requestEuroplan("Evroplan_SuccessResponse.xml", environment);
        assertThat(responce.get(2), containsString("AdapterRLS: call RLS EuroPlan->B2B"));
        assertThat(responce.get(2), containsString("AdapterRLS: before EuroPlan->B2B calling"));
        assertThat(responce.get(2), containsString("AdapterRLS: use RLS 'WebAutoPolicyProcessor'"));
        assertThat(responce.get(2), containsString("AdapterRLS: after EuroPlan->B2B calling"));
        assertThat(responce.get(2), containsString("AdapterVirtu: 'EUROPLAN' operation 'CalcV2' calling Virtu REST service with"));
        switch (environment) {
            case "stage":
                assertThat(responce.get(2), containsString("PartnersPolicyService AMQ CLUSTER DMZ: 'CalcV2' message successfully sent AMQ onto queue 'virtu.sys.in.queue'"));
                break;
            case "test":
                assertThat(responce.get(2), containsString("PartnersPolicyService AMQ CLUSTER: 'CalcV2' message successfully sent onto queue 'virtu.sys.in.queue'"));
                break;
        }
        assertThat(responce.get(2), containsString("PartnersPolicyService-Front: Operation 'CalcV2' complete"));
        assertThat(responce.get(3), containsString("<Id xmlns=\\\"http://ws.rls10\\\">AB64EBD6-8E51-4CDF-AD89-47585FFE3273</Id>"));
        assertThat(responce.get(3), containsString("<SysId xmlns=\\\"http://ws.rls10\\\">B2B</SysId>"));
        assertThat(responce.get(3), containsString("<Type xmlns=\\\"http://ws.rls10\\\">LINK_VEHICLE_MOD</Type>"));
        assertThat(responce.get(4), containsString("<Id xmlns=\\\"http://ws.rls10\\\">AB64EBD6-8E51-4CDF-AD89-47585FFE3273</Id>"));
        assertThat(responce.get(4), containsString("<SysId xmlns=\\\"http://ws.rls10\\\">B2B</SysId>"));
        assertThat(responce.get(4), containsString("<Type xmlns=\\\"http://ws.rls10\\\">LINK_VEHICLE_MOD</Type>"));
    }

    @Test
    public void CreatePolicyV2_COMPLETED() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("CreatePolicyV2.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void CreatePolicyV2tags_COMPLETED() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("createPolicyV2tags.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void CalcV2_allPREVIOUSPOLICYFROMOTHERINSCOMPANY_EXPIRATIONDATE() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("zimAll.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }

    @Test
    public void UpdatePolicyV2_allPREVIOUSPOLICYFROMOTHERINSCOMPANYEXPIRATIONDATE() throws Exception {
        List<String> responce = Template.requestIPartnersPolicyService("updatePolicyPrevious.xml", environment);
        assertThat(responce.get(4), containsString(responce.get(2)));
        assertThat(responce.get(5), containsString(responce.get(3)));
        assertThat(responce.get(1), containsString(responce.get(6)));
    }


    @Test
    public void SetUnderStatus_return_PolicyNumber() throws Exception {
        List<String> responce = Template.requestSetUnderStatus(environment);
        assertThat(responce.get(2), containsString(responce.get(0)));
        assertThat(responce.get(3), containsString("<SetUnderStatus"));
        assertThat(responce.get(3), containsString(responce.get(1)));
        assertThat(responce.get(3), containsString("</SetUnderStatus"));
    }
}