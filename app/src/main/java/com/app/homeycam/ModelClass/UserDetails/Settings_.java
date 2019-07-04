
package com.app.homeycam.ModelClass.UserDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings_ {

    @SerializedName("recording_start")
    @Expose
    private String recordingStart;
    @SerializedName("recording_end")
    @Expose
    private String recordingEnd;
    @SerializedName("recording_status")
    @Expose
    private String recordingStatus;
    @SerializedName("timezone")
    @Expose
    private String timezone;

    public String getRecordingStart() {
        return recordingStart;
    }

    public void setRecordingStart(String recordingStart) {
        this.recordingStart = recordingStart;
    }

    public String getRecordingEnd() {
        return recordingEnd;
    }

    public void setRecordingEnd(String recordingEnd) {
        this.recordingEnd = recordingEnd;
    }

    public String getRecordingStatus() {
        return recordingStatus;
    }

    public void setRecordingStatus(String recordingStatus) {
        this.recordingStatus = recordingStatus;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}
