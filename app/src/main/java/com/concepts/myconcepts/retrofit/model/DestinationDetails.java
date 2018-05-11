
package com.concepts.myconcepts.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DestinationDetails {

    @SerializedName("destination_id")
    @Expose
    private Integer destinationId;
    @SerializedName("tour_management_id")
    @Expose
    private String tourManagementId;
    @SerializedName("destination_name")
    @Expose
    private String destinationName;
    @SerializedName("destination_short_description")
    @Expose
    private String destinationShortDescription;
    @SerializedName("destination_long_description")
    @Expose
    private String destinationLongDescription;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("destination_image")
    @Expose
    private String destinationImage;
    @SerializedName("gujarati_audio")
    @Expose
    private String gujaratiAudio;
    @SerializedName("hindi_audio")
    @Expose
    private String hindiAudio;
    @SerializedName("english_audio")
    @Expose
    private String englishAudio;
    @SerializedName("visit_duration")
    @Expose
    private String visitDuration;
    @SerializedName("open_time")
    @Expose
    private String openTime;
    @SerializedName("close_time")
    @Expose
    private String closeTime;
    @SerializedName("priority_order")
    @Expose
    private String priorityOrder;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("walk_time")
    @Expose
    private String walkTime;
    @SerializedName("banners")
    @Expose
    private List<Banner> banners = null;

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public String getTourManagementId() {
        return tourManagementId;
    }

    public void setTourManagementId(String tourManagementId) {
        this.tourManagementId = tourManagementId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDestinationShortDescription() {
        return destinationShortDescription;
    }

    public void setDestinationShortDescription(String destinationShortDescription) {
        this.destinationShortDescription = destinationShortDescription;
    }

    public String getDestinationLongDescription() {
        return destinationLongDescription;
    }

    public void setDestinationLongDescription(String destinationLongDescription) {
        this.destinationLongDescription = destinationLongDescription;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDestinationImage() {
        return destinationImage;
    }

    public void setDestinationImage(String destinationImage) {
        this.destinationImage = destinationImage;
    }

    public String getGujaratiAudio() {
        return gujaratiAudio;
    }

    public void setGujaratiAudio(String gujaratiAudio) {
        this.gujaratiAudio = gujaratiAudio;
    }

    public String getHindiAudio() {
        return hindiAudio;
    }

    public void setHindiAudio(String hindiAudio) {
        this.hindiAudio = hindiAudio;
    }

    public String getEnglishAudio() {
        return englishAudio;
    }

    public void setEnglishAudio(String englishAudio) {
        this.englishAudio = englishAudio;
    }

    public String getVisitDuration() {
        return visitDuration;
    }

    public void setVisitDuration(String visitDuration) {
        this.visitDuration = visitDuration;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getPriorityOrder() {
        return priorityOrder;
    }

    public void setPriorityOrder(String priorityOrder) {
        this.priorityOrder = priorityOrder;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(String walkTime) {
        this.walkTime = walkTime;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

}
