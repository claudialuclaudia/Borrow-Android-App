package cash.borrow.android.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cash.borrow.android.model.RequestItem;

public class SampleRequestProvider {
    public static List<RequestItem> requestItemList;
    public static Map<String, RequestItem> requestItemMap;

    static {
        requestItemList = new ArrayList<>();
        requestItemMap = new HashMap<>();

        addItem(new RequestItem(null, null, "Kelly Kapoor", 5, 80, "Taylor Swift concert", "kelly.png"));

        addItem(new RequestItem(null, null, "Ryan Howard", 60, 200, "need some stripper ASAP", "ryan.jpg"));

        addItem(new RequestItem(null, null, "Jan Levinson", 15, 10, "trying this app out", "jan.jpg"));

        addItem(new RequestItem(null, null, "Michael Scott", 40, 100, "locked myself out of my apartment...locksmith $", "michael.jpg"));

        addItem(new RequestItem(null, null, "Dwight Schrute", 80, 0.1, "Jim Halpert has an ugly nose!!!", "dwight.jpg"));

        addItem(new RequestItem(null, null, "Kelly Kapoor", 40, 180, "Beyonce concert", "kelly.png"));
    }

    private static void addItem(RequestItem item){
        requestItemList.add(item);
        requestItemMap.put(item.getRequestId(), item);
    }
}
