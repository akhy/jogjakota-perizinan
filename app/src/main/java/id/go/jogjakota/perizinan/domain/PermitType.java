package id.go.jogjakota.perizinan.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PermitType implements Parcelable {
    private String name;
    private int maxDays = 3;
    private LinkedHashMap<String, String> fieldNames;

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }

    public HashMap<String, String> getFieldNames() {
        return fieldNames;
    }

    public void clearFieldNames() {
        fieldNames.clear();
    }

    public void addFieldName(String key, String name) {
        if (fieldNames == null)
            fieldNames = new LinkedHashMap<>();

        fieldNames.put(key, name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaxDaysString() {
        return String.format("Maks. %d hari kerja", maxDays);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(maxDays);
        dest.writeInt(fieldNames.size());
        for (String key : fieldNames.keySet()) {
            dest.writeString(key);
            dest.writeString(fieldNames.get(key));
        }
    }

    public PermitType() {
    }

    private PermitType(Parcel in) {
        name = in.readString();
        maxDays = in.readInt();
        int fieldCount = in.readInt();
        fieldNames = new LinkedHashMap<>();
        for (int i = 0; i < fieldCount; i++)
            fieldNames.put(in.readString(), in.readString());
    }

    public static final Parcelable.Creator<PermitType> CREATOR = new Parcelable.Creator<PermitType>() {
        public PermitType createFromParcel(Parcel source) {
            return new PermitType(source);
        }

        public PermitType[] newArray(int size) {
            return new PermitType[size];
        }
    };
}
