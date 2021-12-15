package net.rptools.maptool.client.ui.multitouch.handlers;

import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import org.mt4j.input.inputProcessors.componentProcessors.zoomProcessor.ZoomEvent;

public class ZoneZoomHandler implements ZoneHandler<ZoomEvent> {
    @Override
    public void handleEvent(ZoneRenderer zoneRenderer, ZoomEvent zoomEvent) {
        float centerX = (zoomEvent.getFirstCursor().getCurrentEvtPosX()
                + zoomEvent.getSecondCursor().getCurrentEvtPosX()) / 2;
        float centerY = (zoomEvent.getFirstCursor().getCurrentEvtPosY()
                + zoomEvent.getSecondCursor().getCurrentEvtPosY()) / 2;
        zoneRenderer.zoomScale((int)centerX, (int)centerY, zoneRenderer.getScale() * zoomEvent.getZoomRatio());
    }
}
