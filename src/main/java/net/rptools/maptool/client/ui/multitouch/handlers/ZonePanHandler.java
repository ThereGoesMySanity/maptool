package net.rptools.maptool.client.ui.multitouch.handlers;

import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import org.mt4j.input.inputProcessors.componentProcessors.panProcessor.PanTwoFingerEvent;

public class ZonePanHandler implements ZoneHandler<PanTwoFingerEvent> {
    @Override
    public void handleEvent(ZoneRenderer zoneRenderer, PanTwoFingerEvent panEvent) {
        zoneRenderer.moveViewBy((int)panEvent.getTranslationVector().x, (int)panEvent.getTranslationVector().y);
    }
}
