package cash.borrow.android.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cash.borrow.android.model.CommentItem;

public class SampleCommentProvider {
    public static List<CommentItem> commentItemList;
    public static Map<String, CommentItem> commentItemMap;
    public static Map<String, Map<String, CommentItem>> requestCommentItemMap;

    static {
        commentItemList = new ArrayList<>();
        commentItemMap = new HashMap<>();
        requestCommentItemMap = new HashMap<>();

        addItem(new CommentItem("2", "1", "Kelly Kapoor", 45, false, 0, "Ryan!!!", "1"));

        addItem(new CommentItem("2", "1", "Kelly Kapoor", 40, false, 0, "Pick up my calls!!", "2"));

        addItem(new CommentItem("2", "4", "Michael Scott", 30, false, 0, "Hahaha nice!", "3"));

        addItem(new CommentItem("3", "4", "Michael Scott", 150, false, 0, "Jan!!", "1"));

        addItem(new CommentItem("3", "3", "Jan Levinson", 100, false, 0, "@Michael Scott: Please don't talk to me in public Michael...", "2"));

        addItem(new CommentItem("5", "1", "Kelly Kapoor", 200, false, 0, "Dwight you dumb dumb Jim doesn't need a nose job", "1"));

    }

    private static void addItem(CommentItem item) {
        commentItemList.add(item);
        commentItemMap.put(item.getCommentId(), item);
        if (!requestCommentItemMap.containsKey(item.getRequestId())){
            Map<String, CommentItem> map = new HashMap<>();
            map.put(item.getCommentId(), item);
            requestCommentItemMap.put(item.getRequestId(), map);
        } else {
            requestCommentItemMap.get(item.getRequestId()).put(item.getCommentId(), item);
        }
    }
}
