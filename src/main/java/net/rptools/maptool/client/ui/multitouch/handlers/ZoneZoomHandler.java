/*
 * This software Copyright by the RPTools.net development team, and
 * licensed under the Affero GPL Version 3 or, at your option, any later
 * version.
 *
 * MapTool Source Code is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public
 * License * along with this source Code.  If not, please visit
 * <http://www.gnu.org/licenses/> and specifically the Affero license
 * text at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool.client.ui.multitouch.handlers;

import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import org.mt4j.input.inputProcessors.componentProcessors.zoomProcessor.ZoomEvent;

public class ZoneZoomHandler implements ZoneHandler<ZoomEvent> {
  @Override
  public void handleEvent(ZoneRenderer zoneRenderer, ZoomEvent zoomEvent) {
    float centerX =
        (zoomEvent.getFirstCursor().getCurrentEvtPosX()
                + zoomEvent.getSecondCursor().getCurrentEvtPosX())
            / 2;
    float centerY =
        (zoomEvent.getFirstCursor().getCurrentEvtPosY()
                + zoomEvent.getSecondCursor().getCurrentEvtPosY())
            / 2;
    zoneRenderer.zoomScale(
        (int) centerX, (int) centerY, zoneRenderer.getScale() * zoomEvent.getZoomRatio());
  }
}
