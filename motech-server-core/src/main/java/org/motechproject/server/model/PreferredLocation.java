package org.motechproject.server.model;

import flexjson.JSONSerializer;

public class PreferredLocation {

    private String region;
    private String district ;
    private String subDistrict;
    private Integer facilityId;
    private Integer communityId;

    public PreferredLocation(String region, String district, String subDistrict, Integer facilityId, Integer communityId) {
        this.region = region;
        this.district = district;
        this.subDistrict = subDistrict;
        this.facilityId = facilityId;
        this.communityId = communityId;
    }

    public String getRegion() {
        return region;
    }

    public String getDistrict() {
        return district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreferredLocation that = (PreferredLocation) o;

        if (communityId != null ? !communityId.equals(that.communityId) : that.communityId != null) return false;
        if (facilityId != null ? !facilityId.equals(that.facilityId) : that.facilityId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = facilityId != null ? facilityId.hashCode() : 0;
        result = 31 * result + (communityId != null ? communityId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
       return new JSONSerializer().serialize(this);
    }


}
