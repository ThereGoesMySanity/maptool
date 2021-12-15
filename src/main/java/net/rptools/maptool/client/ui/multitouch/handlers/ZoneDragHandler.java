package net.rptools.maptool.client.ui.multitouch.handlers;

import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;

import java.awt.event.MouseEvent;
import java.util.Map;

public class ZoneDragHandler implements ZoneHandler<DragEvent> {
    private static final Map<Integer, Integer> dragToMouseEvent = Map.of(
            MTGestureEvent.GESTURE_STARTED, MouseEvent.MOUSE_PRESSED,
            MTGestureEvent.GESTURE_RESUMED, MouseEvent.MOUSE_PRESSED,
            MTGestureEvent.GESTURE_UPDATED, MouseEvent.MOUSE_DRAGGED,
            MTGestureEvent.GESTURE_ENDED, MouseEvent.MOUSE_RELEASED,
            MTGestureEvent.GESTURE_CANCELED, MouseEvent.MOUSE_RELEASED
    );
    @Override
    public void handleEvent(ZoneRenderer zoneRenderer, DragEvent event) {
        MouseEvent fakeEvent = new MouseEvent(zoneRenderer, dragToMouseEvent.get(event.getId()), event.getTimeStamp(),
                0, (int)event.getTo().x, (int)event.getTo().y, 1, false, MouseEvent.BUTTON1);
        zoneRenderer.dispatchEvent(fakeEvent);
    }
}
