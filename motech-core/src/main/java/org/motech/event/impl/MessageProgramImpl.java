package org.motech.event.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.motech.event.Command;
import org.motech.event.MessageProgram;
import org.motech.event.MessageProgramState;
import org.motech.event.MessageProgramStateTransition;
import org.openmrs.Patient;

public class MessageProgramImpl extends BaseInterfaceImpl implements
		MessageProgram {

	private static Log log = LogFactory.getLog(MessageProgramImpl.class);

	private MessageProgramState startState;
	private MessageProgramState endState;
	private String conceptName;
	private String conceptValue;

	public MessageProgramState getStartState() {
		return startState;
	}

	public void setStartState(MessageProgramState startState) {
		this.startState = startState;
	}

	public MessageProgramState getEndState() {
		return endState;
	}

	public void setEndState(MessageProgramState endState) {
		this.endState = endState;
	}

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public String getConceptValue() {
		return conceptValue;
	}

	public void setConceptValue(String conceptValue) {
		this.conceptValue = conceptValue;
	}

	public MessageProgramState determineState(Patient patient) {
		MessageProgramState state = startState;
		MessageProgramStateTransition transition = state.getTransition(patient);
		while (!transition.getNextState().equals(state)) {
			state = transition.getNextState();
			transition = state.getTransition(patient);
		}

		if (log.isDebugEnabled()) {
			log.debug("Message Program determineState: patient id: "
					+ patient.getPatientId() + ", state: " + state.getName());
		}

		// Perform state action using date
		Command command = state.getCommand();
		List<Command> allCommands = null;
		if (command instanceof CompositeCommand) {
			allCommands = ((CompositeCommand) command).getCommands();
		} else {
			allCommands = new ArrayList<Command>();
			allCommands.add(command);
		}
		// Handle setting properties for all commands incase composite command
		boolean performExecute = true;
		for (Command commandInList : allCommands) {
			if (commandInList instanceof ScheduleMessageCommand) {
				Date messageDate = state.getDateOfAction(patient);
				if (messageDate == null) {
					performExecute = false;
				}
				((ScheduleMessageCommand) commandInList)
						.setMessageDate(messageDate);
				((ScheduleMessageCommand) commandInList)
						.setMessageRecipientId(patient.getPatientId());
				((ScheduleMessageCommand) commandInList).setMessageGroup(this
						.getName());
			} else if (commandInList instanceof RemoveMessagesCommand) {
				((RemoveMessagesCommand) commandInList)
						.setMessageRecipientId(patient.getPatientId());
				((RemoveMessagesCommand) commandInList).setMessageGroup(this
						.getName());
			} else if (commandInList instanceof RemoveEnrollmentCommand) {
				((RemoveEnrollmentCommand) commandInList).setPersonId(patient
						.getPatientId());
				((RemoveEnrollmentCommand) commandInList).setProgramName(this
						.getName());
			}
		}
		if (performExecute) {
			command.execute();
		}

		return state;
	}

	public MessageProgramState updateState(Patient patient) {
		MessageProgramState state = determineState(patient);
		if (state.equals(endState)) {
			return state;
		}
		MessageProgramStateTransition transition = state.getTransition(patient);
		transition.getCommand().execute();
		MessageProgramState newState = transition.getNextState();

		return newState;
	}

}