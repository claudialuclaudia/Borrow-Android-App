package cash.borrow.android.sample;

import java.util.ArrayList;
import java.util.List;

import cash.borrow.android.model.ProfileItem;

public class SampleSarahProfileProvider {
    public static List<ProfileItem> profileItemList;

    static {
        profileItemList = new ArrayList<>();

        addItem(new ProfileItem("You've requested", "$150"));

        addItem(new ProfileItem("You've borrowed", "$50"));

        addItem(new ProfileItem("Current Credit Limit", "$300"));

        addItem(new ProfileItem("Remaining Borrow Power", "$50"));

        addItem(new ProfileItem("Payment Due Date (dd/mm/yy)", "01/25/18"));

        addItem(new ProfileItem("Minimum Payment Due", "$25"));

    }

    private static void addItem(ProfileItem item){
        profileItemList.add(item);
    }
}
