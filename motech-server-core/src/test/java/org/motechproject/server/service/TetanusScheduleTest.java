package org.motechproject.server.service;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.Capture;
import org.motechproject.server.model.ExpectedObs;
import org.motechproject.server.service.impl.ExpectedObsSchedule;
import org.motechproject.server.svc.RegistrarBean;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TetanusScheduleTest extends TestCase {

	ApplicationContext ctx;

	RegistrarBean registrarBean;
	ExpectedObsSchedule ttSchedule;
	ExpectedCareEvent tt1Event;
	ExpectedCareEvent tt2Event;
	ExpectedCareEvent tt3Event;
	ExpectedCareEvent tt4Event;
	ExpectedCareEvent tt5Event;

	@Override
	protected void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext(new String[] {
				"test-common-program-beans.xml",
				"services/pregnancy-tetanus-service.xml" });
		ttSchedule = (ExpectedObsSchedule) ctx
				.getBean("pregnancyTetanusSchedule");
		tt1Event = ttSchedule.getEvents().get(0);
		tt2Event = ttSchedule.getEvents().get(1);
		tt3Event = ttSchedule.getEvents().get(2);
		tt4Event = ttSchedule.getEvents().get(3);
		tt5Event = ttSchedule.getEvents().get(4);

		// EasyMock setup in Spring config
		registrarBean = (RegistrarBean) ctx.getBean("registrarBean");
	}

	@Override
	protected void tearDown() throws Exception {
		ctx = null;
		ttSchedule = null;
		tt1Event = null;
		tt2Event = null;
		tt3Event = null;
		tt4Event = null;
		tt5Event = null;
		registrarBean = null;
	}

	public void testCreateFirstDose() {
		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -30); // age is 30 years

		Patient patient = new Patient();
		patient.setGender("F");
		patient.setBirthdate(calendar.getTime());

		Capture<Date> dueDateCapture = new Capture<Date>();
		Capture<Date> lateDateCapture = new Capture<Date>();

		List<Obs> obsList = new ArrayList<Obs>();
		List<ExpectedObs> expectedObsList = new ArrayList<ExpectedObs>();

		expect(
				registrarBean.getObs(patient, ttSchedule.getConceptName(),
						ttSchedule.getValueConceptName(), patient
								.getBirthdate())).andReturn(obsList);
		expect(registrarBean.getExpectedObs(patient, ttSchedule.getName()))
				.andReturn(expectedObsList);
		expect(
				registrarBean.createExpectedObs(eq(patient), eq(ttSchedule
						.getConceptName()),
						eq(ttSchedule.getValueConceptName()), eq(tt1Event
								.getNumber()), (Date) anyObject(),
						capture(dueDateCapture), capture(lateDateCapture),
						(Date) anyObject(), eq(tt1Event.getName()),
						eq(ttSchedule.getName()))).andReturn(new ExpectedObs());

		replay(registrarBean);

		ttSchedule.updateSchedule(patient, date);

		verify(registrarBean);

		Date dueDate = dueDateCapture.getValue();
		Date lateDate = lateDateCapture.getValue();

		assertNotNull("Due date is null", dueDate);
		assertNotNull("Late date is null", lateDate);
		assertTrue("Late date not equal to due date", lateDate.equals(dueDate));
	}

	public void testCreateExpectedDose() {
		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -30); // age is 30 years

		Patient patient = new Patient();
		patient.setGender("F");
		patient.setBirthdate(calendar.getTime());

		Capture<ExpectedObs> expectedObsCapture = new Capture<ExpectedObs>();
		Capture<Date> dueDateCapture = new Capture<Date>();
		Capture<Date> lateDateCapture = new Capture<Date>();

		List<Obs> obsList = new ArrayList<Obs>();
		Obs obs3 = new Obs();
		obs3.setObsDatetime(date);
		obs3.setValueNumeric(new Double(3));
		obsList.add(obs3);
		Obs obs4 = new Obs();
		obs4.setObsDatetime(date);
		obs4.setValueNumeric(new Double(4));
		obsList.add(obs4);

		List<ExpectedObs> expectedObsList = new ArrayList<ExpectedObs>();
		ExpectedObs expectedObs = new ExpectedObs();
		expectedObs.setName(tt4Event.getName());
		expectedObsList.add(expectedObs);

		expect(
				registrarBean.getObs(patient, ttSchedule.getConceptName(),
						ttSchedule.getValueConceptName(), patient
								.getBirthdate())).andReturn(obsList);
		expect(registrarBean.getExpectedObs(patient, ttSchedule.getName()))
				.andReturn(expectedObsList);
		expect(registrarBean.saveExpectedObs(capture(expectedObsCapture)))
				.andReturn(new ExpectedObs());
		expect(
				registrarBean.createExpectedObs(eq(patient), eq(ttSchedule
						.getConceptName()),
						eq(ttSchedule.getValueConceptName()), eq(tt5Event
								.getNumber()), (Date) anyObject(),
						capture(dueDateCapture), capture(lateDateCapture),
						(Date) anyObject(), eq(tt5Event.getName()),
						eq(ttSchedule.getName()))).andReturn(new ExpectedObs());

		replay(registrarBean);

		ttSchedule.updateSchedule(patient, date);

		verify(registrarBean);

		ExpectedObs tt4ExpectedObs = expectedObsCapture.getValue();
		assertEquals(tt4Event.getName(), tt4ExpectedObs.getName());
		assertEquals(Boolean.TRUE, tt4ExpectedObs.getVoided());
		assertEquals(obs4, tt4ExpectedObs.getObs());

		Date dueDate = dueDateCapture.getValue();
		Date lateDate = lateDateCapture.getValue();

		assertNotNull("Due date is null", dueDate);
		assertNotNull("Late date is null", lateDate);
		assertTrue("Late date is not after due date", lateDate.after(dueDate));
	}

	public void testNoActionDose() {
		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -30); // age is 30 years

		Patient patient = new Patient();
		patient.setGender("F");
		patient.setBirthdate(calendar.getTime());

		List<Obs> obsList = new ArrayList<Obs>();
		Obs obs = new Obs();
		obs.setObsDatetime(date);
		obs.setValueNumeric(new Double(5));
		obsList.add(obs);

		List<ExpectedObs> expectedObsList = new ArrayList<ExpectedObs>();

		expect(
				registrarBean.getObs(patient, ttSchedule.getConceptName(),
						ttSchedule.getValueConceptName(), patient
								.getBirthdate())).andReturn(obsList);
		expect(registrarBean.getExpectedObs(patient, ttSchedule.getName()))
				.andReturn(expectedObsList);

		replay(registrarBean);

		ttSchedule.updateSchedule(patient, date);

		verify(registrarBean);
	}

	public void testNoActionAge() {
		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -6); // age is 6 years

		Patient patient = new Patient();
		patient.setGender("F");
		patient.setBirthdate(calendar.getTime());

		List<ExpectedObs> expectedObsList = new ArrayList<ExpectedObs>();

		expect(registrarBean.getExpectedObs(patient, ttSchedule.getName()))
				.andReturn(expectedObsList);

		replay(registrarBean);

		ttSchedule.updateSchedule(patient, date);

		verify(registrarBean);
	}

	public void testNoActionGender() {
		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -30); // age is 30 years

		Patient patient = new Patient();
		patient.setGender("M"); // Male gender
		patient.setBirthdate(calendar.getTime());

		List<ExpectedObs> expectedObsList = new ArrayList<ExpectedObs>();

		expect(registrarBean.getExpectedObs(patient, ttSchedule.getName()))
				.andReturn(expectedObsList);

		replay(registrarBean);

		ttSchedule.updateSchedule(patient, date);

		verify(registrarBean);
	}

}
