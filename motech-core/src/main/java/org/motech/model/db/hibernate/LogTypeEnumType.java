package org.motech.model.db.hibernate;

import org.motech.model.LogType;

/**
 * An instantiated usertype, allowing storing readable values for the LogType
 * enumeration in the database.
 */
public class LogTypeEnumType extends EnumUserType<LogType> {

	public LogTypeEnumType() {
		super(LogType.class);
	}
}
