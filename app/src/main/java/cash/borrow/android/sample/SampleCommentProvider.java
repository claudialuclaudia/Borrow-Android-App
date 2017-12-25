package cash.borrow.android.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Pack200;

import cash.borrow.android.model.CommentItem;

public class SampleCommentProvider {
    public static List<CommentItem> commentItemList;
    public static Map<String, CommentItem> commentItemMap;
    public static Map<String, Map<String, CommentItem>> requestCommentItemMap;
    public static Map<String, Double> requestProgress;//request Id : progress so far
    public static Map<String, Set> lentMap;//userId to list of all requestId that he has contributed to

    static {
        commentItemList = new ArrayList<>();
        commentItemMap = new HashMap<>();
        requestCommentItemMap = new HashMap<>();
        requestProgress = new HashMap<>();
        lentMap = new HashMap<>();

        addItem(new CommentItem("2", "1", "Kelly Kapoor", 45, false, 0, "Ryan!!!", "1"));

        addItem(new CommentItem("2", "1", "Kelly Kapoor", 40, false, 0, "Pick up my calls!!", "2"));

        addItem(new CommentItem("2", "4", "Michael Scott", 30, false, 0, "Hahaha nice!", "3"));

        addItem(new CommentItem("3", "4", "Michael Scott", 150, false, 0, "Jan!!", "1"));

        addItem(new CommentItem("3", "3", "Jan Levinson", 100, false, 0, "@Michael Scott: Please don't talk to me in public Michael...", "2"));

        addItem(new CommentItem("5", "1", "Kelly Kapoor", 200, false, 0, "Dwight you dumb dumb Jim doesn't need a nose job", "1"));

        addItem(new CommentItem("3", "4", "Michael Scott", 145, true, 3, null, "3"));

        addItem(new CommentItem("3", "4", "Michael Scott", 140, true, 2, null, "4"));

        addItem(new CommentItem("8", "1", "Kelly Kapoor", 120, true, 20, null, "1"));

        addItem(new CommentItem("6", "4", "Michael Scott", 25, true, 50, null, "1"));

        addItem(new CommentItem("7", "4", "Michael Scott", 10, true, 40, null, "1"));

        addItem(new CommentItem("1", "4", "Michael Scott", 2, true, 23, null, "1"));

        addItem(new CommentItem("2", "4", "Michael Scott", 25, true, 35, null, "4"));

        addItem(new CommentItem("10", "3", "Jan Levinson", 17, true, 5, null, "1"));

        addItem(new CommentItem("10", "2", "Ryan Howard", 8, true, 30, null, "2"));

        addItem(new CommentItem("5", "4", "Michael Scott", 180, true, 0.1, null, "2"));

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

        if (item.isLent()) {
            if (requestProgress.containsKey(item.getRequestId())){
                double newProgress = requestProgress.get(item.getRequestId()) + item.getLentAmount();
                requestProgress.put(item.getRequestId(), newProgress);
            } else {
                double progress = item.getLentAmount();
                requestProgress.put(item.getRequestId(), progress);
            }

            if(lentMap.containsKey(item.getCommenterId())){
                lentMap.get(item.getCommenterId()).add(item.getRequestId());
            } else {
                Set<String> set = new HashSet<>();
                set.add(item.getRequestId());
                lentMap.put(item.getCommenterId(),set);
            }
        }
    }
}
