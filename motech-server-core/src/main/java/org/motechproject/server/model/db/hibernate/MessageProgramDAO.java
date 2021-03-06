package org.motechproject.server.model.db.hibernate;

import org.motechproject.server.model.MessageProgram;
import org.motechproject.server.model.ExpectedCareMessageDetails;

import java.util.List;

public interface MessageProgramDAO {
    List<ExpectedCareMessageDetails> messageDetails();

    MessageProgram weeklyProgram(String programName);
}
