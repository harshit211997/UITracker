package xyz.ylimit.uitracker;

import android.view.accessibility.AccessibilityEvent;

/**
 * Created by yuanchun on 31/10/2016.
 * Some consts used in UITracker
 */
public class Const {

    final static public Integer[] ACCESSIBILITY_EVENT_TO_HANDLE = {
            AccessibilityEvent.TYPE_VIEW_CLICKED,
            AccessibilityEvent.TYPE_VIEW_LONG_CLICKED,
            AccessibilityEvent.TYPE_VIEW_FOCUSED,
            AccessibilityEvent.TYPE_VIEW_SCROLLED,
//            AccessibilityEvent.TYPE_VIEW_SELECTED,
//            AccessibilityEvent.TYPE_TOUCH_INTERACTION_START,
//            AccessibilityEvent.TYPE_TOUCH_INTERACTION_END,
//            AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START,
//            AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END,
//            AccessibilityEvent.TYPE_GESTURE_DETECTION_START,
//            AccessibilityEvent.TYPE_GESTURE_DETECTION_END,
//            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED,
//            AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED,
//            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED,
//            AccessibilityEvent.TYPE_WINDOWS_CHANGED,
//            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
    };

    final static public String[] EXCLUDE_PACKAGES = {
            "xyz.ylimit.uitracker",
//            "com.android.systemui",
    };

    final static public String ProjectName = "UITracker";
}
