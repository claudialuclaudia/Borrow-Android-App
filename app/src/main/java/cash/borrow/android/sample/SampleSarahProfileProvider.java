package cash.borrow.android.sample;

import java.util.ArrayList;
import java.util.List;

import cash.borrow.android.model.ProfileItem;

public class SampleSarahProfileProvider {
    public static List<ProfileItem> profileItemList;

    static {
        profileItemList = new ArrayList<>();

        addItem(new ProfileItem("You've Borrowed", "$100", "BOLD", "#a0a0a0"));

        addItem(new ProfileItem("You've Lent", "$50", "BOLD", "#a0a0a0"));

        addItem(new ProfileItem("Balance", "-$50", "BOLD", "#cc0000"));

        addItem(new ProfileItem("Currently Requesting", "$150", "BOLD", "#31926f"));

        addItem(new ProfileItem("Credit Limit", "$300", "BOLD", "#80000000"));

        addItem(new ProfileItem("Remaining Borrow Amount", "$150", "BOLD", "#80000000"));

        addItem(new ProfileItem("Payment Due Date (dd/mm/yy)", "01/25/18", "NORMAL", "#80000000"));

        addItem(new ProfileItem("Minimum Payment Due", "$25", "BOLD", "#a0a0a0"));

    }

    private static void addItem(ProfileItem item){
        profileItemList.add(item);
    }
}
