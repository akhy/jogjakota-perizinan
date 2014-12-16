package id.go.jogjakota.perizinan.data;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import id.go.jogjakota.perizinan.domain.Permit;
import id.go.jogjakota.perizinan.domain.User;

public class Dummies {
    public static void init() {
        List<User> users = new ArrayList<>();

        UserDB userDB = UserDB.get();
        PermitDB permitDB = PermitDB.get();

        User akhyar = new User();
        akhyar.setUsername("6371030306910005");
        akhyar.setFullName("Akhyar Amarullah");
        akhyar.setEmail("akhyrul@gmail.com");
        akhyar.setPassword("1234");
        users.add(akhyar);

        User noprianto = new User();
        noprianto.setFullName("Noprianto");
        noprianto.setUsername("noprianto");
        noprianto.setPassword("1234");
        noprianto.setEmail("lepengdados@gmail.com");
        users.add(noprianto);

        User esi = new User();
        esi.setFullName("Esi Oktavia");
        esi.setUsername("esi");
        esi.setPassword("1234");
        esi.setEmail("esi.oktavia@gmail.com");
        users.add(esi);

        try {
            userDB.addUser(akhyar);
            userDB.addUser(noprianto);
            userDB.addUser(esi);
        } catch (UserDB.UserExistException ignored) {
        }

        for (User user : users) {
            Permit kkn = new Permit();
            kkn.setType(PermitTypes.KKN);
            kkn.setUser(user);
            kkn.addFieldValue(PermitTypes.KKN_INSTITUTION_NAME, "UGM");
            kkn.addFieldValue(PermitTypes.KKN_START_DATE, "8 Januari 2015");
            kkn.addFieldValue(PermitTypes.KKN_END_DATE, "14 Juni 2015");
            kkn.addFieldValue(PermitTypes.KKN_LOCATION, "Tegalrejo");
            kkn.addFieldValue(PermitTypes.KKN_RESPONSIBLE_PERSON, "Noprianto");
            kkn.addFieldValue(PermitTypes.KKN_PEOPLE, "Akhyar, Esi");
            kkn.setRequestTime(DateTime.now().minusDays(30));
            kkn.setResponseTime(DateTime.now());
            kkn.setApproved(true);

            Permit research = new Permit();
            research.setType(PermitTypes.RESEARCH);
            research.setUser(user);
            research.addFieldValue(PermitTypes.RESEARCH_INSTITUTION_NAME, "TKI Research Club");
            research.addFieldValue(PermitTypes.RESEARCH_START_DATE, "5 Desember 2016");
            research.addFieldValue(PermitTypes.RESEARCH_END_DATE, "10 Desember 2016");
            research.addFieldValue(PermitTypes.RESEARCH_LOCATION, "Gedung JTETI UGM");
            research.addFieldValue(PermitTypes.RESEARCH_RESPONSIBLE_PERSON, "Esi Oktavia");
            research.addFieldValue(PermitTypes.RESEARCH_MEMBERS, "Noprianto");
            research.setRequestTime(DateTime.now().minusDays(30));
            research.setResponseTime(DateTime.now());
            research.setApproved(false);

            permitDB.addPermit(kkn);
            permitDB.addPermit(research);
        }

//        User defUser = users.get(DateTime.now().getSecondOfMinute() % 3);
//        Session.get().login(defUser);
    }
}
