package cash.borrow.android.sample;

import java.util.ArrayList;
import java.util.List;

import cash.borrow.android.model.ProfileItem;

public class SampleSarahProfileProvider {
    public static List<ProfileItem> profileItemList;

    static {
        profileItemList = new ArrayList<>();

        addItem(new ProfileItem("You've borrowed", "$50", "BOLD", "#FF4081"));

        addItem(new ProfileItem("Your current active requests total", "$150", "BOLD", "#31926f"));

        addItem(new ProfileItem("Your Current Credit Limit", "$300", "BOLD", "#80000000"));

        addItem(new ProfileItem("Your Remaining Borrow Power", "$150", "BOLD", "#31926f"));

        addItem(new ProfileItem("Payment Due Date (dd/mm/yy)", "01/25/18", "NORMAL", "#000000"));

        addItem(new ProfileItem("Minimum Payment Due", "$25", "BOLD", "#31926f"));

    }

    private static void addItem(ProfileItem item){
        profileItemList.add(item);
    }
}
