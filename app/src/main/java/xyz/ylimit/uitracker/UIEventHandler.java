package xyz.ylimit.uitracker;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by yuanchun on 31/10/2016.
 * For handle UI event
 */
class UIEventHandler extends Handler {
    private UITrackerService trackerService;

    private static final String eventLock = "EventLock";
    private List<UIEvent> pendingEvents;
    private static final int EVENT_WAITING_TIME_MILLISECOND = 1000;

    UIEventHandler(UITrackerService trackerService){
        this.trackerService = trackerService;
        this.pendingEvents = new ArrayList<>();
    }

    public void handleMessage(Message message) {
        Bundle data = message.getData();
    }

    private boolean isWaitingForEvents() {
        return pendingEvents.size() == 0;
    }

    private void startWaitingForEvents() {
        this.pendingEvents.clear();
    }

    void handle(AccessibilityEvent event){
        if(event == null)
            return;
        synchronized (eventLock) {
            if (isWaitingForEvents()) {
                this.postDelayed(new CollectEventTask(), EVENT_WAITING_TIME_MILLISECOND);
            }
            this.pendingEvents.add(new UIEvent(event));
        }
    }

    void handle(KeyEvent event) {
        if(event == null)
            return;
        synchronized (eventLock) {
            if (isWaitingForEvents()) {
                this.postDelayed(new CollectEventTask(), EVENT_WAITING_TIME_MILLISECOND);
            }
            this.pendingEvents.add(new UIEvent(event));
        }
    }

    private void collectEvents() {
        Debug.log("Start collecting UI events", trackerService);
        for (UIEvent event : this.pendingEvents) {
            Debug.log(event.toString(), trackerService);
        }
        Debug.log("Finish collecting UI events", trackerService);
    }

    private void dumpUIState() {
        Debug.log("Start dumping UI state", trackerService);
        AccessibilityNodeInfo rootNode = trackerService.getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> nodes = preOrderTraverse(rootNode);
            Debug.log("# nodes: " + nodes.size(), trackerService);
//            for (AccessibilityNodeInfo node : nodes) {
//                Debug.log(node.toString(), trackerService);
//            }
        }
        Debug.log("Finish dumping UI state", trackerService);
    }

    private class CollectEventTask implements Runnable {

        @Override
        public void run() {
            synchronized (eventLock) {
                // collect events
                collectEvents();

                // dump UI state
                dumpUIState();

                // start waiting for events
                startWaitingForEvents();
            }
        }
    }

    /**
     * traverse a tree from the root, and return all the notes in the tree
     * @param root the root node
     * @return a list of filtered nodes
     */
    private List<AccessibilityNodeInfo> preOrderTraverse(AccessibilityNodeInfo root){
        if(root == null)
            return null;
        List<AccessibilityNodeInfo> list = new ArrayList<>();
        list.add(root);
        int childCount = root.getChildCount();
        for(int i = 0; i < childCount; i ++){
            AccessibilityNodeInfo node = root.getChild(i);
            if(node != null)
                list.addAll(preOrderTraverse(node));
        }
        return list;
    }

}
