package id.go.jogjakota.perizinan.domain;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Permit implements Parcelable {

    private User user;
    private DateTime requestTime;
    private DateTime responseTime;
    private PermitType type;
    private LinkedHashMap<String, String> fieldValues;
    private boolean approved;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashMap<String, String> getFieldValues() {
        return fieldValues;
    }

    public void addFieldValue(String key, String value) {
        if (fieldValues == null)
            fieldValues = new LinkedHashMap<>();

        fieldValues.put(key, value);
    }

    public DateTime getRequestTime() {
        return requestTime;
    }

    public String getRequestTimeString() {
        return requestTime.toString(DateTimeFormat.longDate());
    }

    public void setRequestTime(DateTime requestTime) {
        this.requestTime = requestTime;
    }

    public DateTime getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(DateTime responseTime) {
        this.responseTime = responseTime;
    }

    public PermitType getType() {
        return type;
    }

    public void setType(PermitType type) {
        this.type = type;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getStatusString() {
        return approved
                ? "Pengajuan anda telah disetujui dan dapat dicetak di kantor Dinas Perizinan."
                : "Maaf, pengajuan anda belum selesai diproses. ";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(user);
        dest.writeValue(requestTime);
        dest.writeValue(responseTime);
        dest.writeValue(type);
        dest.writeByte((byte) (approved ? 0x01 : 0x00));

        dest.writeInt(fieldValues.size());
        for (String key : fieldValues.keySet()) {
            dest.writeString(key);
            dest.writeString(fieldValues.get(key));
        }
    }

    public Permit() {
    }

    private Permit(Parcel in) {
        user = (User) in.readValue(User.class.getClassLoader());
        requestTime = (DateTime) in.readValue(DateTime.class.getClassLoader());
        responseTime = (DateTime) in.readValue(DateTime.class.getClassLoader());
        type = (PermitType) in.readValue(PermitType.class.getClassLoader());
        approved = in.readByte() != 0x00;

        int fieldCount = in.readInt();
        fieldValues = new LinkedHashMap<>();
        for (int i = 0; i < fieldCount; i++)
            fieldValues.put(in.readString(), in.readString());
    }

    public static final Parcelable.Creator<Permit> CREATOR = new Parcelable.Creator<Permit>() {
        public Permit createFromParcel(Parcel source) {
            return new Permit(source);
        }

        public Permit[] newArray(int size) {
            return new Permit[size];
        }
    };
}
