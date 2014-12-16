package id.go.jogjakota.perizinan.data;

import java.util.ArrayList;
import java.util.List;

import id.go.jogjakota.perizinan.domain.PermitType;

public class PermitTypes {
    public static final PermitType KKN;
    public static final PermitType RESEARCH;

    public static final String KKN_INSTITUTION_NAME = "institution_name";
    public static final String KKN_START_DATE = "start_date";
    public static final String KKN_END_DATE = "end_date";
    public static final String KKN_LOCATION = "location";
    public static final String KKN_RESPONSIBLE_PERSON = "responsible_person";
    public static final String KKN_PEOPLE = "people";
    public static final String RESEARCH_INSTITUTION_NAME = "institution_name";
    public static final String RESEARCH_START_DATE = "start_date";
    public static final String RESEARCH_END_DATE = "end_date";
    public static final String RESEARCH_LOCATION = "location";
    public static final String RESEARCH_RESPONSIBLE_PERSON = "responsible_person";
    public static final String RESEARCH_MEMBERS = "members";

    static {
        KKN = new PermitType();
        KKN.setName("Izin Kuliah Kerja Nyata (KKN)");
        KKN.addFieldName(KKN_INSTITUTION_NAME, "Nama kampus atau institusi pendidikan");
        KKN.addFieldName(KKN_START_DATE, "Tanggal mulai");
        KKN.addFieldName(KKN_END_DATE, "Tanggal selesai");
        KKN.addFieldName(KKN_LOCATION, "Lokasi KKN");
        KKN.addFieldName(KKN_RESPONSIBLE_PERSON, "Penanggung jawab");
        KKN.addFieldName(KKN_PEOPLE, "Nama anggota kelompok KKN");

        RESEARCH = new PermitType();
        RESEARCH.setName("Izin Penelitian");
        RESEARCH.addFieldName(RESEARCH_INSTITUTION_NAME, "Nama kampus atau institusi pendidikan");
        RESEARCH.addFieldName(RESEARCH_START_DATE, "Tanggal mulai");
        RESEARCH.addFieldName(RESEARCH_END_DATE, "Tanggal selesai");
        RESEARCH.addFieldName(RESEARCH_LOCATION, "Lokasi");
        RESEARCH.addFieldName(RESEARCH_RESPONSIBLE_PERSON, "Penanggung jawab");
        RESEARCH.addFieldName(RESEARCH_MEMBERS, "Nama anggota kelompok penelitian");
    }

    public static List<PermitType> getAll() {
        List<PermitType> result = new ArrayList<>();
        result.add(KKN);
        result.add(RESEARCH);
        return result;
    }
}
