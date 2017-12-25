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

        addItem(new UserItem("2", "Ryan Howard", "ryan.jpg", 87, "101 Sunset Boulevard", "Los Angelas, CA", "Freelancer", 800, -300));

        addItem(new UserItem("3", "Jan Levinson", "jan.jpg", 5, "216 W 101th Street", "New York, NY", "Corporate Manager at Dunder Mifflin", 100, 0));

        addItem(new UserItem("4", "Michael Scott", "michael.jpg", 208, "108 Paterson Avenue", "Scranton, NY", "Regional Manager at Dunder Mifflin", 400, 300));

        addItem(new UserItem("5", "Dwight Schrute", "dwight.jpg", 13, "58 Schrute Farm Road", "Scranton, NY", "Assistant Regional Manager at Dunder Mifflin", 300, 0));
    }

    private static void addItem(UserItem item){
        userItemList.add(item);
        userItemMap.put(item.getUserId(),item);
    }
}
