package flows;

import bsh.ParseException;
import forms.RegisterClientForm;
import forms.SikuliBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePageLinksEnum;
import pages.MoTeCHDashBoardPage;
import pages.OpenMRSLoginPage;
import pages.ViewDataPage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterNewChildTest {
    @Test
    public void test()throws ParseException, java.text.ParseException{
        RegisterClientForm obj = new RegisterClientForm();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        String date = sdf.format(Calendar.getInstance().getTime());
        String staffId = "465";
        String facilityId = "11117";
        String regDate = "1/4/2010";
        String regPhoneNo ="0123456789";
        String firstName ="Child";
        String middleName ="middle";
        String lastName ="Last"+date;
        String preferredName = "preferred";
        String DOB ="1/1/2009";
        String motherMotechId = "1234568";
        obj.registerNonInsuredChild(staffId, facilityId, regDate, regPhoneNo, SikuliBase.clientTypes.CHILD_UNDER_5, firstName, middleName, lastName, preferredName, DOB, SikuliBase.DOBTypes.EXACT_DOB, SikuliBase.clientGender.MALE,motherMotechId );
        OpenMRSLoginPage loginPage = new OpenMRSLoginPage();
        loginPage.getOpenMRSDashBoard();
        MoTeCHDashBoardPage moTeCHDashBoardPage = new MoTeCHDashBoardPage();
        moTeCHDashBoardPage.navigateToPage(HomePageLinksEnum.VIEW_DATA);
        ViewDataPage viewPage = new ViewDataPage();
        Assert.assertTrue(viewPage.verifyPatientLastName(firstName),"Patient is registered successfully");

    }
}

