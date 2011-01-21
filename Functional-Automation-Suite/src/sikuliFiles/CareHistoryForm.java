package sikuliFiles;

import java.text.ParseException;

public class CareHistoryForm extends SikuliBaseClass{
	
	public void care_history_form_with_itp_value(String staff_id,String facility_id,String date,String Motech_id,IPTValues ipt, String last_ipt_date )throws ParseException {
		try {
			// selecting form 
			selectForm(FormName.CARE_HISTORY);
			
			 //Entering values in TT_Non_Pregnant form 
	         //1. filling the staff id
	          inputTextbox(staff_id);
	         
	          //2. Filling the facility_id 
	          inputTextbox(facility_id);
	          
	         //3. Filling the date of visit 
	          selectDate(stringToDateConvertor(date));
	          
	         //4. Filling the motech id 
	          inputTextbox(Motech_id);
	          
	         //5. Filling History for IPT value and last IPT date
	          selectHistoryStatus(addHistoryValues.IPT);
	          selectIPTValue(ipt);
	          selectDate(stringToDateConvertor(date));
	             
	        //6. Saving the form
	          saveMform();
	          
	          //7. Moving to Main Menu and uploading the form
	          traverseToMainMenuAndUploadForm();
		}
        catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}