package net.rptools.maptool.client.ui.multitouch.handlers;

import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import org.mt4j.input.inputProcessors.MTGestureEvent;

public interface ZoneHandler<T extends MTGestureEvent> {
    void handleEvent(ZoneRenderer zoneRenderer, T event);
}
