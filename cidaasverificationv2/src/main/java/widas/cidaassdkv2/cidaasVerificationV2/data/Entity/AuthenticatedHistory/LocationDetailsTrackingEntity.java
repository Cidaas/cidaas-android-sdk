package widas.cidaassdkv2.cidaasVerificationV2.data.Entity.AuthenticatedHistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDetailsTrackingEntity implements Serializable {

    private String _id="";
    private IpAddressEntity ipaddress_info;
    private LocationEntity location_info;
    private Double distance_metter;
    private String createdTime="";
    private String updatedTime="";

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public IpAddressEntity getIpaddress_info() {
        return ipaddress_info;
    }

    public void setIpaddress_info(IpAddressEntity ipaddress_info) {
        this.ipaddress_info = ipaddress_info;
    }

    public LocationEntity getLocation_info() {
        return location_info;
    }

    public void setLocation_info(LocationEntity location_info) {
        this.location_info = location_info;
    }

    public Double getDistance_metter() {
        return distance_metter;
    }

    public void setDistance_metter(Double distance_metter) {
        this.distance_metter = distance_metter;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}
