package org.motechproject.server.model.ghana;

import flexjson.JSON;
import org.openmrs.Location;

import java.io.Serializable;
import java.util.*;

public class HealthFacility implements Serializable {
    private String name;
    private String region;
    private String district;
    private String subDistrict;
    private Integer facilityId;
    private String phoneNumber;
    private String additionalPhoneNumber1;
    private String additionalPhoneNumber2;
    private String additionalPhoneNumber3;
    private List<Community> communities = new ArrayList<Community>();

    public HealthFacility(Facility facility) {
        Location location = facility.getLocation();
        this.facilityId = facility.getFacilityId();
        this.name = location.getName();
        this.region = location.getRegion();
        this.district = location.getCountyDistrict();
        this.subDistrict = location.getStateProvince();
        this.phoneNumber = facility.getPhoneNumber();
        this.additionalPhoneNumber1 = facility.getAdditionalPhoneNumber1();
        this.additionalPhoneNumber2 = facility.getAdditionalPhoneNumber2();
        this.additionalPhoneNumber3 = facility.getAdditionalPhoneNumber3();
        addBasicCommunityInformation(facility.getCommunities());
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getDistrict() {
        return district;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAdditionalPhoneNumber1() {
        return additionalPhoneNumber1;
    }

    public void setAdditionalPhoneNumber1(String additionalPhoneNumber1) {
        this.additionalPhoneNumber1 = additionalPhoneNumber1;
    }

    public String getAdditionalPhoneNumber2() {
        return additionalPhoneNumber2;
    }

    public void setAdditionalPhoneNumber2(String additionalPhoneNumber2) {
        this.additionalPhoneNumber2 = additionalPhoneNumber2;
    }

    public String getAdditionalPhoneNumber3() {
        return additionalPhoneNumber3;
    }

    public void setAdditionalPhoneNumber3(String additionalPhoneNumber3) {
        this.additionalPhoneNumber3 = additionalPhoneNumber3;
    }

    @JSON
    public List<Community> getCommunities() {
        Collections.sort(communities, new Comparator<Community>() {
            public int compare(Community o1, Community o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return communities;
    }

    private void addBasicCommunityInformation(Set<Community> communities) {
        if (communities == null) return;
        for (Community community : communities) {
            this.communities.add(community.basicInfo());
        }
    }
}
