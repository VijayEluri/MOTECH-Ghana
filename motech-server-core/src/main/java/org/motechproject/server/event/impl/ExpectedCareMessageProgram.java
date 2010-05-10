package org.motechproject.server.event.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.motechproject.server.event.MessageProgram;
import org.motechproject.server.event.MessageProgramState;
import org.motechproject.server.model.ExpectedEncounter;
import org.motechproject.server.model.ExpectedObs;
import org.motechproject.server.model.Message;
import org.motechproject.server.model.MessageProgramEnrollment;
import org.motechproject.server.model.ScheduledMessage;
import org.motechproject.server.svc.RegistrarBean;
import org.motechproject.server.time.TimePeriod;
import org.openmrs.Patient;

public class ExpectedCareMessageProgram extends BaseInterfaceImpl implements
		MessageProgram {

	private static Log log = LogFactory
			.getLog(ExpectedCareMessageProgram.class);

	private List<ExpectedCareMessageDetails> careMessageDetails = new ArrayList<ExpectedCareMessageDetails>();
	private RegistrarBean registrarBean;

	public MessageProgramState determineState(
			MessageProgramEnrollment enrollment) {

		Date currentDate = new Date();
		// Calculate date 1 day in future to use for message dates calculated in
		// past
		Date nextDate = calculateDate(currentDate, 1, TimePeriod.day);

		Integer maxPatientReminders = registrarBean
				.getMaxPatientCareReminders();

		// Get patient from enrollment person Id
		Patient patient = registrarBean
				.getPatientById(enrollment.getPersonId());
		if (patient == null) {
			log.debug("Person of enrollment is not a patient: "
					+ enrollment.getPersonId());
			return null;
		}

		// Get all active expected care for patient (obs and encounter)
		List<ExpectedEncounter> expectedEncounters = registrarBean
				.getExpectedEncounters(patient);
		List<ExpectedObs> expectedObservations = registrarBean
				.getExpectedObs(patient);
		// Get all messages for enrollment (includes sent and not sent)
		List<ScheduledMessage> scheduledMessages = registrarBean
				.getScheduledMessages(enrollment);

		// Create predicates for expected care (by group) and scheduled messages
		// (by key)
		ExpectedEncounterPredicate expectedEncounterPredicate = new ExpectedEncounterPredicate();
		ExpectedObsPredicate expectedObsPredicate = new ExpectedObsPredicate();
		ScheduledMessagePredicate scheduledMessagePredicate = new ScheduledMessagePredicate();

		for (ExpectedCareMessageDetails careDetails : careMessageDetails) {
			String careGroup = careDetails.getName();
			String upcomingKey = careDetails.getUpcomingMessageKey();
			String overdueKey = careDetails.getOverdueMessageKey();

			// Set predicates for care details
			expectedEncounterPredicate.setGroup(careGroup);
			expectedObsPredicate.setGroup(careGroup);
			scheduledMessagePredicate.resetKeys(upcomingKey, overdueKey);

			// Get scheduled messages for care, removing from enrollment list
			List<ScheduledMessage> careScheduledMessages = getScheduledMessages(
					scheduledMessages, scheduledMessagePredicate);
			scheduledMessages.removeAll(careScheduledMessages);

			// Get expected obs and encounters for care details
			List<ExpectedEncounter> careExpectedEncounters = getExpectedEncounters(
					expectedEncounters, expectedEncounterPredicate);
			List<ExpectedObs> careExpectedObs = getExpectedObs(
					expectedObservations, expectedObsPredicate);

			List<ScheduledMessage> verifiedScheduledMessages = new ArrayList<ScheduledMessage>();

			// Schedule messages for expected care (encounters and obs) and add
			// message to verified list
			for (ExpectedEncounter expectedEncounter : careExpectedEncounters) {
				Date dueDate = expectedEncounter.getDueEncounterDatetime();
				Date lateDate = expectedEncounter.getLateEncounterDatetime();
				String care = expectedEncounter.getName();

				// Create new scheduled message or return existing matching
				ScheduledMessage message = scheduleCareMessage(currentDate,
						nextDate, dueDate, lateDate, careDetails, care,
						enrollment, careScheduledMessages, maxPatientReminders);
				if (message != null) {
					verifiedScheduledMessages.add(message);
				}
			}
			for (ExpectedObs expectedObs : careExpectedObs) {
				Date dueDate = expectedObs.getDueObsDatetime();
				Date lateDate = expectedObs.getLateObsDatetime();
				String care = expectedObs.getName();

				// Create new scheduled message or return existing matching
				ScheduledMessage message = scheduleCareMessage(currentDate,
						nextDate, dueDate, lateDate, careDetails, care,
						enrollment, careScheduledMessages, maxPatientReminders);
				if (message != null) {
					verifiedScheduledMessages.add(message);
				}
			}

			// Cancel unsent messages for care (not matching the newly
			// scheduled)
			careScheduledMessages.removeAll(verifiedScheduledMessages);
			if (!careScheduledMessages.isEmpty()) {
				registrarBean.removeUnsentMessages(careScheduledMessages);
			}
		}

		// Cancel any unsent messages for enrollment (for unhandled care)
		if (!scheduledMessages.isEmpty()) {
			registrarBean.removeUnsentMessages(scheduledMessages);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	protected List<ExpectedEncounter> getExpectedEncounters(
			List<ExpectedEncounter> expectedEncounterList,
			ExpectedEncounterPredicate expectedEncounterPredicate) {
		return (List<ExpectedEncounter>) CollectionUtils.select(
				expectedEncounterList, expectedEncounterPredicate);
	}

	@SuppressWarnings("unchecked")
	protected List<ExpectedObs> getExpectedObs(
			List<ExpectedObs> expectedObsList,
			ExpectedObsPredicate expectedObsPredicate) {
		return (List<ExpectedObs>) CollectionUtils.select(expectedObsList,
				expectedObsPredicate);
	}

	@SuppressWarnings("unchecked")
	protected List<ScheduledMessage> getScheduledMessages(
			List<ScheduledMessage> scheduledMessagesList,
			ScheduledMessagePredicate scheduledMessagePredicate) {
		return (List<ScheduledMessage>) CollectionUtils.select(
				scheduledMessagesList, scheduledMessagePredicate);
	}

	protected ScheduledMessage getMatchingScheduledMessage(
			List<ScheduledMessage> scheduledMessagesList,
			ScheduledMessagePredicate scheduledMessagePredicate) {
		return (ScheduledMessage) CollectionUtils.find(scheduledMessagesList,
				scheduledMessagePredicate);
	}

	protected boolean isUpcoming(Date currentDate, Date expectedCareDate,
			ExpectedCareMessageDetails careDetails) {
		// Upcoming care is considered twice the value for the care
		Date endDate = calculateDate(currentDate, 2 * careDetails
				.getTimeValue(), careDetails.getTimePeriod());
		return currentDate.before(expectedCareDate)
				&& endDate.after(expectedCareDate);
	}

	protected boolean isOverdue(Date currentDate, Date expectedCareDate) {
		return currentDate.after(expectedCareDate);
	}

	protected ScheduledMessage scheduleCareMessage(Date currentDate,
			Date nextDate, Date dueDate, Date lateDate,
			ExpectedCareMessageDetails careDetails, String care,
			MessageProgramEnrollment enrollment,
			List<ScheduledMessage> careScheduledMessages,
			Integer maxPatientReminders) {

		if (isUpcoming(currentDate, dueDate, careDetails)) {
			// Schedule reminder value/period before care due date
			Date messageDate = calculateDate(dueDate, (-1 * careDetails
					.getTimeValue()), careDetails.getTimePeriod());
			messageDate = verifyFutureDate(currentDate, nextDate, messageDate);

			// Set predicate and get upcoming scheduled message if exists
			ScheduledMessagePredicate scheduledMessagePredicate = new ScheduledMessagePredicate();
			scheduledMessagePredicate.resetKeys(careDetails
					.getUpcomingMessageKey());
			scheduledMessagePredicate.setCare(care);
			ScheduledMessage upcomingMessage = getMatchingScheduledMessage(
					careScheduledMessages, scheduledMessagePredicate);

			// Create new scheduled message only if not already exist
			if (upcomingMessage == null) {
				return registrarBean.scheduleCareMessage(careDetails
						.getUpcomingMessageKey(), enrollment, messageDate,
						false, care);
			} else {
				return upcomingMessage;
			}

		} else if (isOverdue(currentDate, lateDate)) {
			// Schedule reminder value/period after care late date
			Date reminderDate = calculateDate(lateDate, careDetails
					.getTimeValue(), careDetails.getTimePeriod());
			reminderDate = verifyFutureDate(currentDate, nextDate, reminderDate);

			// Set predicate and get previous reminder scheduled message if
			// exists
			ScheduledMessagePredicate scheduledMessagePredicate = new ScheduledMessagePredicate();
			scheduledMessagePredicate.resetKeys(careDetails
					.getOverdueMessageKey());
			scheduledMessagePredicate.setCare(care);
			ScheduledMessage reminderMessage = getMatchingScheduledMessage(
					careScheduledMessages, scheduledMessagePredicate);

			if (reminderMessage == null) {
				// Schedule reminder if no previous reminders
				return registrarBean.scheduleCareMessage(careDetails
						.getOverdueMessageKey(), enrollment, reminderDate,
						false, care);
			} else {
				// Determine last reminder date
				List<Message> attempts = reminderMessage.getMessageAttempts();
				Date previousReminderDate = null;
				if (!attempts.isEmpty()) {
					previousReminderDate = attempts.get(0).getAttemptDate();
				} else {
					previousReminderDate = reminderMessage.getScheduledFor();
				}

				// Schedule reminder value/period after most recent reminder
				// if number of previous reminders less than max property
				if (attempts.size() < maxPatientReminders) {
					Date newReminderDate = calculateDate(previousReminderDate,
							careDetails.getTimeValue(), careDetails
									.getTimePeriod());
					Date maxReminderDate = calculateDate(currentDate,
							careDetails.getTimeValue(), careDetails
									.getTimePeriod());
					// Prevent scheduling reminders too far in future
					// Only schedule one reminder ahead
					if (!newReminderDate.after(maxReminderDate)) {
						newReminderDate = verifyFutureDate(currentDate,
								nextDate, newReminderDate);
						registrarBean.addMessageAttempt(reminderMessage,
								newReminderDate);
					}
				}
				return reminderMessage;
			}
		}
		return null;
	}

	protected Date calculateDate(Date date, Integer value, TimePeriod period) {
		if (date == null || value == null || period == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		switch (period) {
		case minute:
			calendar.add(Calendar.MINUTE, value);
			break;
		case hour:
			calendar.add(Calendar.HOUR, value);
			break;
		case day:
			calendar.add(Calendar.DATE, value);
			break;
		case week:
			// Add weeks as days
			calendar.add(Calendar.DATE, value * 7);
			break;
		case month:
			calendar.add(Calendar.MONTH, value);
			break;
		case year:
			calendar.add(Calendar.YEAR, value);
			break;
		}
		return calendar.getTime();
	}

	protected Date verifyFutureDate(Date currentDate, Date nextDate,
			Date calculatedDate) {
		if (currentDate.after(calculatedDate)) {
			return nextDate;
		}
		return calculatedDate;
	}

	public String getConceptName() {
		return null;
	}

	public String getConceptValue() {
		return null;
	}

	public MessageProgramState getEndState() {
		return null;
	}

	public MessageProgramState getStartState() {
		return null;
	}

	public MessageProgramState updateState(MessageProgramEnrollment enrollment) {
		return null;
	}

	public List<ExpectedCareMessageDetails> getCareMessageDetails() {
		return careMessageDetails;
	}

	public void setCareMessageDetails(
			List<ExpectedCareMessageDetails> careMessageDetails) {
		this.careMessageDetails = careMessageDetails;
	}

	public RegistrarBean getRegistrarBean() {
		return registrarBean;
	}

	public void setRegistrarBean(RegistrarBean registrarBean) {
		this.registrarBean = registrarBean;
	}

}