package com.concepts.myconcepts.optimization;

/**
 * Created by tasol on 23/6/18.
 */

public class TempSubject {
    String id;
    String sectionID;
    String subjectID;
    String start;
    String end;


    public TempSubject(String id, String sectionID, String subjectID, String start, String end) {
        this.id = id;
        this.sectionID = sectionID;
        this.subjectID = subjectID;
        this.start = start;
        this.end = end;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
