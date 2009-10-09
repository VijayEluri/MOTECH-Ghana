package org.motech.mobile.client;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.motechproject.ws.ContactNumberType;
import org.motechproject.ws.MediaType;
import org.motechproject.ws.Patient;
import org.motechproject.ws.mobile.MessageService;

/**
 * A stub implementation of the motech mobile message interface. This enables us
 * to configure the application to a working state without knowing the presence
 * of an active we service endpoint. The intent is that when actually deploying
 * the application, it will be reconfigured to point to the real endpoint.
 */
public class MessageServiceStub implements MessageService {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(MessageServiceStub.class);

	public String sendCHPSMessage(Long messageId, String workerName,
			String workerNumber, Patient[] patients, String langCode,
			MediaType mediaType, Long notificationType, Date startDate,
			Date endDate) {

		log.info("Motech Mobile Web Service Message\n"
				+ "---------------------------\n" + "<sendCHPSMessage>\n"
				+ "<messageId>"
				+ messageId
				+ "</messageId>\n"
				+ "<workerName>"
				+ workerName
				+ "</workerName>\n"
				+ "<workerNumber>"
				+ workerNumber
				+ "</workerNumber>\n"
				+ "<patientList></patientList>\n"
				+ "<langCode>"
				+ langCode
				+ "</langCode>\n"
				+ "<mediaType>"
				+ mediaType
				+ "</mediaType>\n"
				+ "<notificationType>"
				+ notificationType
				+ "</notificationType>\n"
				+ "<startDate>"
				+ startDate
				+ "</startDate>\n"
				+ "<endDate>"
				+ endDate
				+ "</endDate>\n"
				+ "</sendCHPSMessage>\n"
				+ "--------------------------------------");
		return "1";
	}

	public String sendPatientMessage(Long messageId, String patientName,
			String patientNumber, ContactNumberType patientNumberType,
			String langCode, MediaType mediaType, Long notificationType,
			Date startDate, Date endDate) {

		log.info("Motech Mobile Web Service Message\n"
				+ "---------------------------\n" + "<sendPatientMessage>\n"
				+ "<messageId>"
				+ messageId
				+ "</messageId>\n"
				+ "<patientName>"
				+ patientName
				+ "</patientName>\n"
				+ "<patientNumber>"
				+ patientNumber
				+ "</patientNumber>\n"
				+ "<contactNumberType>"
				+ patientNumberType
				+ "</contactNumberType>\n"
				+ "<langCode>"
				+ langCode
				+ "</langCode>\n"
				+ "<mediaType>"
				+ mediaType
				+ "</mediaType>\n"
				+ "<notificationType>"
				+ notificationType
				+ "</notificationType>\n"
				+ "<startDate>"
				+ startDate
				+ "</startDate>\n"
				+ "<endDate>"
				+ endDate
				+ "</endDate>\n"
				+ "</sendPatientMessage>\n"
				+ "--------------------------------------");
		return "1";
	}

}
