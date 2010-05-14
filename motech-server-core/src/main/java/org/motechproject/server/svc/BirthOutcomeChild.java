package org.motechproject.server.svc;

import org.motechproject.ws.BirthOutcome;
import org.motechproject.ws.Gender;
import org.motechproject.ws.RegistrationMode;

public class BirthOutcomeChild {

	BirthOutcome outcome;
	RegistrationMode idMode;
	Integer motechId;
	Gender sex;
	String firstName;
	Double weight;

	public BirthOutcomeChild(BirthOutcome outcome, RegistrationMode idMode,
			Integer motechId, Gender sex, String firstName, Double weight) {
		this.outcome = outcome;
		this.idMode = idMode;
		this.motechId = motechId;
		this.sex = sex;
		this.firstName = firstName;
		this.weight = weight;
	}

	public BirthOutcomeChild() {
	}

	public BirthOutcome getOutcome() {
		return outcome;
	}

	public void setOutcome(BirthOutcome outcome) {
		this.outcome = outcome;
	}

	public RegistrationMode getIdMode() {
		return idMode;
	}

	public void setIdMode(RegistrationMode idMode) {
		this.idMode = idMode;
	}

	public Integer getMotechId() {
		return motechId;
	}

	public void setMotechId(Integer motechId) {
		this.motechId = motechId;
	}

	public Gender getSex() {
		return sex;
	}

	public void setSex(Gender sex) {
		this.sex = sex;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

}
