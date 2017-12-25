package cash.borrow.android.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cash.borrow.android.model.UserItem;

public class SampleUserProvider {
    public static List<UserItem> userItemList;
    public static Map<String, UserItem> userItemMap;

    static {
        userItemList = new ArrayList<>();
        userItemMap = new HashMap<>();

        addItem(new UserItem("1", "Kelly Kapoor", "kelly.jpg", 158, "101 Sunset Boulevard", "Los Angelas, CA", "Stylist for Khloe Khardashian", 600, -100));

        addItem(new UserItem("2", "Ryan Howard", "ryan.jpg", 87, "101 Sunset Boulevard", "Los Angelas, CA", "Amateur Drug Dealer", 800, -300));

        addItem(new UserItem("3", "Jan Levinson", "jan.jpg", 5, "216 W 101th Street", "New York, NY", "Corporate Manager at Dunder Mifflin", 100, 0));

        addItem(new UserItem("4", "Michael Scott", "michael.jpg", 208, "108 Paterson Avenue", "Scranton, NY", "Regional Manager at Dunder Mifflin", 400, 300));

        addItem(new UserItem("5", "Dwight Schrute", "dwight.jpg", 13, "58 Schrute Farm Road", "Scranton, NY", "Assistant Regional Manager at Dunder Mifflin", 300, 0));

        addItem(new UserItem("11", "Bojack Horseman", "bojack.jpg", 2, "5 Bojack Rd", "Hollywoo, CA", "Actor/Future Distance Runner?", 300, 0));

        addItem(new UserItem("12", "Sarah Lynn", "sarah.jpg", 1045, "2 Sunset Boulevard", "Hollywoo, CA", "Very Famous Actress", 300, 0));

        addItem(new UserItem("13", "Diane Nguyen", "diane.jpg", 43, "5 Random Rd", "Hollywoo, CA", "Writer", 300, 0));

        addItem(new UserItem("14", "Mr. Peanutbutter", "peanutbutter.jpg", 356, "5 Random Rd", "Hollywoo, CA", "Talk Show Host", 300, 0));

        addItem(new UserItem("15", "Todd Chavez", "todd.jpg", 32, "Unkown", "Hollywoo, CA", "Fulltime Angel Baby Boi", 300, 0));

        addItem(new UserItem("16", "Princess Carolyn", "carolyn.jpg", 422, "6 Some Rd", "Hollywoo, CA", "Manager/Former Agent", 300, 0));

    }

    private static void addItem(UserItem item){
        userItemList.add(item);
        userItemMap.put(item.getUserId(),item);
    }
}
