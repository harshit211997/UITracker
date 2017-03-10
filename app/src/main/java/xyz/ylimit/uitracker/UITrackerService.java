package xyz.ylimit.uitracker;

import android.accessibilityservice.AccessibilityService;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by yuanchun on 31/10/2016.
 * Accessibility service for UI tracking
 */
public class UITrackerService extends AccessibilityService {
    UIEventHandler eventHandler;

    Set<Integer> accessibilityEventToHandle;
    Set<String> excludePackages;
    StatusIconManager statusIconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        eventHandler = new UIEventHandler(this);

        statusIconManager = new StatusIconManager(this, eventHandler);

        try {
            Debug.log("Service Started", this);
            statusIconManager.addStatusIcon();
        } catch (Exception e) {
            e.printStackTrace();
        }

        accessibilityEventToHandle = new HashSet<>(Arrays.asList(Const.ACCESSIBILITY_EVENT_TO_HANDLE));
        excludePackages = new HashSet<>(Arrays.asList(Const.EXCLUDE_PACKAGES));
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //return if the event is not among the accessibilityEventArrayToHandle
        if (!accessibilityEventToHandle.contains(event.getEventType())) {
            return;
        }

        if (event.getPackageName() != null && excludePackages.contains(event.getPackageName().toString()))
            return;

        eventHandler.handle(event);
    }

    @Override
    public void onInterrupt() {
    }

//    public boolean onGesture(int gestureId) {
//        String gestureStr = String.valueOf(gestureId);
//        Debug.log(gestureStr, this);
//        return super.onGesture(gestureId);
//    }

    public boolean onKeyEvent(KeyEvent keyEvent) {
        eventHandler.handle(keyEvent);
        return super.onKeyEvent(keyEvent);
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Debug.log("Service Stopped", this);
        if (statusIconManager != null) {
            try {
                statusIconManager.removeStatusIcon();
            } catch (Exception e) {
                //failed to remove status icon
                e.printStackTrace();
            }
        }
    }

}
