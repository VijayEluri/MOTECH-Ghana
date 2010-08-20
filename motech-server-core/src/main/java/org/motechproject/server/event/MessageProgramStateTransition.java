package org.motechproject.server.event;

import java.util.Date;

import org.motechproject.server.model.MessageProgramEnrollment;

public interface MessageProgramStateTransition extends BaseInterface {

	MessageProgramState getPrevState();

	MessageProgramState getNextState();

	MessagesCommand getCommand();

	boolean evaluate(MessageProgramEnrollment enrollment, Date currentDate);
}
