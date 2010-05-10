package org.motechproject.server.model;

import java.io.Serializable;
import java.util.Set;

import org.openmrs.Location;

public class Facility implements Serializable {

	private static final long serialVersionUID = -791545747208269078L;

	private Long id;
	private Long facilityId;
	private Location location;
	private String phoneNumber;
	private Set<Location> communities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<Location> getCommunities() {
		return communities;
	}

	public void setCommunities(Set<Location> communities) {
		this.communities = communities;
	}
}