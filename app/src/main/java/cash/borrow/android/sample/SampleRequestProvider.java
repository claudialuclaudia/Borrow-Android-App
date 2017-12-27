package cash.borrow.android.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cash.borrow.android.model.RequestItem;

public class SampleRequestProvider {
    public static List<RequestItem> requestItemList;
    public static Map<String, RequestItem> requestItemMap;
    public static Map<String, List<RequestItem>> userMap; //userId to list of RequestItems

    static {
        requestItemList = new ArrayList<>();
        requestItemMap = new HashMap<>();
        userMap = new HashMap<>();

        addItem(new RequestItem("1", "1", "Kelly Kapoor", 5, 80, "Taylor Swift concert", "kelly.jpg"));

        addItem(new RequestItem("2", "2", "Ryan Howard", 60, 200, "need some stripper ASAP", "ryan.jpg"));

        addItem(new RequestItem("3", "3", "Jan Levinson", 200, 10, "trying this app out", "jan.jpg"));

        addItem(new RequestItem("4", "4", "Michael Scott", 40, 100, "locked myself out of my apartment... locksmith $", "michael.jpg"));

        addItem(new RequestItem("5", "5", "Dwight Schrute", 300, 0.1, "Jim Halpert's nose job money", "dwight.jpg"));

        addItem(new RequestItem("6", "1", "Kelly Kapoor", 40, 180, "Beyonce concert", "kelly.jpg"));

        addItem(new RequestItem("7", "1", "Kelly Kapoor", 25, 150, "Drake concert", "kelly.jpg"));

        addItem(new RequestItem("8", "4", "Michael Scott", 200, 150, "locked myself out of my car...", "michael.jpg"));

        addItem(new RequestItem("9", "2", "Ryan Howard", 20, 200, "ASAPPPPPP", "ryan.jpg"));

        addItem(new RequestItem("10", "5", "Dwight Schrute", 30, 300, "fund for Dwight Schrute's plan for the world", "dwight.jpg"));

        addItem(new RequestItem("11", "11", "Bojack Horseman", 48, 80, "groceries for my fat horse ass...", "bojack.jpg"));

        addItem(new RequestItem("12", "12", "Sarah Lynn", 234, 150, "I'm back from the dead you dumb shits!!!", "sarah.jpg"));

        addItem(new RequestItem("13", "13", "Diane Nguyen", 78, 30, "money for the new David Sedaris book", "diane.jpg"));

        addItem(new RequestItem("14", "14", "Mr. Peanutbutter", 85, 250, "bouncy house needs to be fixed! :(", "peanutbutter.jpg"));

        addItem(new RequestItem("15", "15", "Todd Chavez", 8, 30, "kettlecorns!!", "todd.jpg"));

        addItem(new RequestItem("16", "15", "Todd Chavez", 208, 20, "butter scotches!!", "todd.jpg"));

        addItem(new RequestItem("17", "14", "Mr. Peanutbutter", 25, 79, "new bow ties!", "peanutbutter.jpg"));

    }

    private static void addItem(RequestItem item){
        requestItemList.add(item);
        requestItemMap.put(item.getRequestId(), item);
        if (!userMap.containsKey(item.getUserId())) {
            List<RequestItem> list = new ArrayList<>();
            list.add(item);
            userMap.put(item.getUserId(), list);
        } else {
            userMap.get(item.getUserId()).add(item);
        }
    }
}
