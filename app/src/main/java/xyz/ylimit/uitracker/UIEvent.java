package xyz.ylimit.uitracker;

import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuanchun on 31/10/2016.
 * UI event
 */
public class UIEvent {
    private String mType;
    private String mText;
    private String mResourceId;
    private String mClass;
    private String mSourceViewStr;
    private String mGestureEventStr;
    private String mKeyName;
    private String mKeyEventStr;
    private long mEventTime;
    private String mPackage;
    private List<String> mTexts;

    private static Map<Integer, String> eventId2Type = new HashMap<>();

    private static String getEventTypeStr(int eventTypeId) {
        if (eventId2Type.isEmpty()) {
            eventId2Type.put(AccessibilityEvent.TYPE_VIEW_CLICKED, "VIEW_CLICKED");
            eventId2Type.put(AccessibilityEvent.TYPE_VIEW_LONG_CLICKED, "VIEW_LONGCLICKED");
            eventId2Type.put(AccessibilityEvent.TYPE_VIEW_FOCUSED, "VIEW_FOCUSED");
            eventId2Type.put(AccessibilityEvent.TYPE_VIEW_SCROLLED, "VIEW_SCROLLED");
        }
        if (eventId2Type.containsKey(eventTypeId)) {
            return eventId2Type.get(eventTypeId);
        }
        else {
            return String.format("VIEW_%d", eventTypeId);
        }
    }

    private static Map<Integer, String> keyCode2Name = new HashMap<>();

    private static String getKeyName(int keyCode) {
        if (keyCode2Name.isEmpty()) {
            keyCode2Name.put(KeyEvent.KEYCODE_HOME, "HOME");
            keyCode2Name.put(KeyEvent.KEYCODE_BACK, "BACK");
            keyCode2Name.put(KeyEvent.KEYCODE_MENU, "MENU");
            keyCode2Name.put(KeyEvent.KEYCODE_VOLUME_UP, "VOLUME_UP");
            keyCode2Name.put(KeyEvent.KEYCODE_VOLUME_DOWN, "VOLUME_DOWN");
        }
        if (keyCode2Name.containsKey(keyCode)) {
            return keyCode2Name.get(keyCode);
        }
        else {
            return String.format("KEY_%d", keyCode);
        }
    }

    public UIEvent(AccessibilityEvent gestureEvent) {
        this.mType = getEventTypeStr(gestureEvent.getEventType());
        AccessibilityNodeInfo sourceNode = gestureEvent.getSource();
        if (sourceNode != null) {
//            this.mClass = (String) sourceNode.getClassName();
//            this.mText = (String) sourceNode.getText();
            this.mResourceId = sourceNode.getViewIdResourceName();
            this.mSourceViewStr = sourceNode.toString();
        }
        this.mGestureEventStr = gestureEvent.toString();
        this.mEventTime = gestureEvent.getEventTime();
        this.mPackage = (String) gestureEvent.getPackageName();
        this.mClass = (String) gestureEvent.getClassName();
        this.mText = StringUtils.join(gestureEvent.getText(), ",,,");
    }

    public UIEvent(KeyEvent keyEvent) {
        this.mType = "KEY";
        this.mKeyName = getKeyName(keyEvent.getKeyCode());
        this.mKeyEventStr = keyEvent.toString();
        this.mEventTime = keyEvent.getEventTime();
    }

    public Map<String, String> toMap() {
        Map<String, String> eventMap = new HashMap<>();
        eventMap.put("event_type", this.mType);
        eventMap.put("event_time", String.valueOf(this.mEventTime));
        eventMap.put("text", this.mText);
        eventMap.put("class", this.mClass);
        eventMap.put("resource_id", this.mResourceId);
        eventMap.put("name", this.mKeyName);
        eventMap.put("package", this.mPackage);
//        eventMap.put("source_view_str", this.mSourceViewStr);
//        eventMap.put("key_event_str", this.mKeyEventStr);
//        eventMap.put("gesture_event_str", this.mGestureEventStr);
        return eventMap;
    }

    public JSONObject toJson() {
        return new JSONObject(this.toMap());
    }

    public String toString() {
        return this.toJson().toString();
    }
}
