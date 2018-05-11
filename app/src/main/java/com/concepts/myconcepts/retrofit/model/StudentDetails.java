
package com.concepts.myconcepts.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentDetails {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("birthday")
    @Expose
    private Integer birthday;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emergencyContactNo")
    @Expose
    private String emergencyContactNo;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("transport")
    @Expose
    private Integer transport;
    @SerializedName("studentAcademicYear")
    @Expose
    private Integer studentAcademicYear;
    @SerializedName("parentProfession")
    @Expose
    private String parentProfession;
    @SerializedName("parentRelation")
    @Expose
    private String parentRelation;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("class_id")
    @Expose
    private Integer classId;
    @SerializedName("section_id")
    @Expose
    private Integer sectionId;
    @SerializedName("this_year_attendance")
    @Expose
    private String thisYearAttendance;
    @SerializedName("punchuality")
    @Expose
    private String punchuality;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmergencyContactNo() {
        return emergencyContactNo;
    }

    public void setEmergencyContactNo(String emergencyContactNo) {
        this.emergencyContactNo = emergencyContactNo;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Integer getTransport() {
        return transport;
    }

    public void setTransport(Integer transport) {
        this.transport = transport;
    }

    public Integer getStudentAcademicYear() {
        return studentAcademicYear;
    }

    public void setStudentAcademicYear(Integer studentAcademicYear) {
        this.studentAcademicYear = studentAcademicYear;
    }

    public String getParentProfession() {
        return parentProfession;
    }

    public void setParentProfession(String parentProfession) {
        this.parentProfession = parentProfession;
    }

    public String getParentRelation() {
        return parentRelation;
    }

    public void setParentRelation(String parentRelation) {
        this.parentRelation = parentRelation;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getThisYearAttendance() {
        return thisYearAttendance;
    }

    public void setThisYearAttendance(String thisYearAttendance) {
        this.thisYearAttendance = thisYearAttendance;
    }

    public String getPunchuality() {
        return punchuality;
    }

    public void setPunchuality(String punchuality) {
        this.punchuality = punchuality;
    }

}
