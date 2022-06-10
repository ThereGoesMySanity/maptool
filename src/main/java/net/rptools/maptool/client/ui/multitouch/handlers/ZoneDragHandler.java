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

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.SwingUtilities;
import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;

public class ZoneDragHandler implements ZoneHandler<DragEvent> {
  private static final Map<Integer, Integer> dragToMouseEvent =
      Map.of(
          MTGestureEvent.GESTURE_STARTED, MouseEvent.MOUSE_PRESSED,
          MTGestureEvent.GESTURE_RESUMED, MouseEvent.MOUSE_PRESSED,
          MTGestureEvent.GESTURE_UPDATED, MouseEvent.MOUSE_DRAGGED,
          MTGestureEvent.GESTURE_ENDED, MouseEvent.MOUSE_RELEASED,
          MTGestureEvent.GESTURE_CANCELED, MouseEvent.MOUSE_RELEASED);

  private volatile MouseEvent queuedEvent = null;

  private void sendEvent(ZoneRenderer zoneRenderer) {
    zoneRenderer.dispatchEvent(queuedEvent);
    queuedEvent = null;
  }

  @Override
  public void handleEvent(ZoneRenderer zoneRenderer, DragEvent event) {
    int eventId = dragToMouseEvent.get(event.getId());
    if (eventId == MouseEvent.MOUSE_PRESSED) {
      MouseEvent fakeMove =
          new MouseEvent(
              zoneRenderer,
              MouseEvent.MOUSE_MOVED,
              event.getTimeStamp(),
              0,
              (int) event.getTo().x,
              (int) event.getTo().y,
              0,
              false);
      SwingUtilities.invokeLater(() -> zoneRenderer.dispatchEvent(fakeMove));
    }
    boolean doNewQueue = queuedEvent == null;
    queuedEvent =
        new MouseEvent(
            zoneRenderer,
            eventId,
            event.getTimeStamp(),
            InputEvent.BUTTON1_DOWN_MASK,
            (int) event.getTo().x,
            (int) event.getTo().y,
            (eventId == MouseEvent.MOUSE_PRESSED) ? 1 : 0,
            false,
            MouseEvent.BUTTON1);
    if (doNewQueue) {
      SwingUtilities.invokeLater(() -> sendEvent(zoneRenderer));
    }
  }
}
