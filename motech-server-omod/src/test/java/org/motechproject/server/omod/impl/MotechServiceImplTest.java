/**
 * MOTECH PLATFORM OPENSOURCE LICENSE AGREEMENT
 *
 * Copyright (c) 2010-11 The Trustees of Columbia University in the City of
 * New York and Grameen Foundation USA.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of Grameen Foundation USA, Columbia University, or
 * their respective contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY GRAMEEN FOUNDATION USA, COLUMBIA UNIVERSITY
 * AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL GRAMEEN FOUNDATION
 * USA, COLUMBIA UNIVERSITY OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.motechproject.server.omod.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.server.model.Facility;
import org.motechproject.server.omod.MotechService;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MotechServiceImplTest extends BaseModuleContextSensitiveTest {

    private static String DUPLICATE_PATIENTS = "duplicate-patients-dataset.xml";
    private static String PATIENT_FACILITY = "patient-facility-data.xml";

    @Before
    public void setUp() throws Exception {
        executeDataSet(DUPLICATE_PATIENTS);
        executeDataSet(PATIENT_FACILITY);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetAllDuplicatePatients() throws Exception {
        MotechService motechService = Context.getService(MotechService.class);
        assertEquals(2, motechService.getAllDuplicatePatients().size());
    }

    @Test
    public void shouldGetFacilityForPatient() {
        MotechService motechService = Context.getService(MotechService.class);
        Patient patient = new org.openmrs.Patient();
        patient.setId(1);
        Facility facility = motechService.facilityFor(patient);
        assertNotNull(facility);
    }
}
