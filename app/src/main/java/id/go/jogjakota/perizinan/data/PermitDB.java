package id.go.jogjakota.perizinan.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import id.go.jogjakota.perizinan.domain.Permit;
import id.go.jogjakota.perizinan.domain.User;

public class PermitDB {
    private static PermitDB permitDB;

    private LinkedList<Permit> permits = new LinkedList<>();

    public static PermitDB get() {
        if (permitDB == null)
            permitDB = new PermitDB();

        return permitDB;
    }

    public void addPermit(Permit permit) {
        permits.add(permit);
    }

    public List<Permit> getPermitsBy(User user) {
        List<Permit> result = new ArrayList<>();
        for (Permit permit : permits) {
            if (permit.getUser().equals(user))
                result.add(permit);
        }

        return result;
    }

}
