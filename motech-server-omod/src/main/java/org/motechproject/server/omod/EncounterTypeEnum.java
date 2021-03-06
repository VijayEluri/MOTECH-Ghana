package org.motechproject.server.omod;

import org.openmrs.EncounterType;
import org.openmrs.api.EncounterService;

public enum EncounterTypeEnum {
    
    ENCOUNTER_TYPE_ANCVISIT("ANCVISIT"),
    ENCOUNTER_TYPE_PREGREGVISIT("PREGREGVISIT"),
    ENCOUNTER_TYPE_PREGTERMVISIT("PREGTERMVISIT"),
    ENCOUNTER_TYPE_PREGDELVISIT("PREGDELVISIT"),
    ENCOUNTER_TYPE_PREGDELNOTIFYVISIT("PREGDELNOTIFYVISIT"),
    ENCOUNTER_TYPE_OUTPATIENTVISIT("OUTPATIENTVISIT"),
    ENCOUNTER_TYPE_TTVISIT("TTVISIT"),
    ENCOUNTER_TYPE_CWCVISIT("CWCVISIT"),
    ENCOUNTER_TYPE_PNCMOTHERVISIT("PNCMOTHERVISIT"),
    ENCOUNTER_TYPE_PNCCHILDVISIT("PNCCHILDVISIT"),
    ENCOUNTER_TYPE_ANCREGVISIT("ANCREGVISIT"),
    ENCOUNTER_TYPE_CWCREGVISIT("CWCREGVISIT"),
    ENCOUNTER_TYPE_BIRTHVISIT("BIRTHVISIT"),
    ENCOUNTER_TYPE_PATIENTREGVISIT("PATIENTREGVISIT"),
    ENCOUNTER_TYPE_PATIENTHISTORY("PATIENTHISTORY");

    private String encounterTypeName;

    EncounterTypeEnum(String s) {
        this.encounterTypeName = s;
    }

   public EncounterType getEncounterType(EncounterService encounterService){
        return encounterService.getEncounterType(encounterTypeName);
    }
}
