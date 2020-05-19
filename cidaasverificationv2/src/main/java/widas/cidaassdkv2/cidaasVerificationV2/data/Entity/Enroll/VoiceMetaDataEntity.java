package widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Enroll;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceMetaDataEntity implements Serializable {
    String comment="";
    int number_voices_needed;
    int number_voices_uploaded;
    int score;
    int status_code;

    boolean voice_found;
    boolean more_than_one_voice_found;

    String user_id;
    String error;
    String file;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNumber_voices_needed() {
        return number_voices_needed;
    }

    public void setNumber_voices_needed(int number_voices_needed) {
        this.number_voices_needed = number_voices_needed;
    }

    public int getNumber_voices_uploaded() {
        return number_voices_uploaded;
    }

    public void setNumber_voices_uploaded(int number_voices_uploaded) {
        this.number_voices_uploaded = number_voices_uploaded;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public boolean isVoice_found() {
        return voice_found;
    }

    public void setVoice_found(boolean voice_found) {
        this.voice_found = voice_found;
    }

    public boolean isMore_than_one_voice_found() {
        return more_than_one_voice_found;
    }

    public void setMore_than_one_voice_found(boolean more_than_one_voice_found) {
        this.more_than_one_voice_found = more_than_one_voice_found;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
