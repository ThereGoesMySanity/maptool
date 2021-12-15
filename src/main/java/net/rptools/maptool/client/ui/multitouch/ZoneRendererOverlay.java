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
package net.rptools.maptool.client.ui.multitouch;

import net.rptools.maptool.client.ui.multitouch.handlers.ZoneDragHandler;
import net.rptools.maptool.client.ui.multitouch.handlers.ZoneHandler;
import net.rptools.maptool.client.ui.multitouch.handlers.ZonePanHandler;
import net.rptools.maptool.client.ui.multitouch.handlers.ZoneZoomHandler;
import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import org.mt4j.AbstractMTApplication;
import org.mt4j.AbstractMTLayer;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.panProcessor.PanEvent;
import org.mt4j.input.inputProcessors.componentProcessors.panProcessor.PanProcessorTwoFingers;
import org.mt4j.input.inputProcessors.componentProcessors.panProcessor.PanTwoFingerEvent;
import org.mt4j.input.inputProcessors.componentProcessors.zoomProcessor.ZoomEvent;
import org.mt4j.input.inputProcessors.componentProcessors.zoomProcessor.ZoomProcessor;

import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ZoneRendererOverlay extends AbstractMTLayer<ZoneRenderer> {
  private HashMap<Class<?>, ZoneHandler<?>> handlers;
  public ZoneRendererOverlay(AbstractMTApplication app) {
    super(app);
    handlers = new HashMap<>();
    registerInputProcessor(new PanProcessorTwoFingers(500));
    handlers.put(PanTwoFingerEvent.class, new ZonePanHandler());
    registerInputProcessor(new ZoomProcessor(500));
    handlers.put(ZoomEvent.class, new ZoneZoomHandler());
    registerInputProcessor(new DragProcessor());
    handlers.put(DragEvent.class, new ZoneDragHandler());
  }

  @Override
  public boolean processGestureEvent(MTGestureEvent mtGestureEvent) {
//    System.out.println(mtGestureEvent.toString() + " " + mtGestureEvent.getId());
    ZoneRenderer zoneRenderer = getLayer().getView();
    try {
      Class<?> gestureType = mtGestureEvent.getClass();
      if (handlers.containsKey(gestureType)) {
        ZoneHandler<?> handler = handlers.get(gestureType);
        handler.getClass()
                .getDeclaredMethod("handleEvent", ZoneRenderer.class, gestureType)
                .invoke(handler, zoneRenderer, mtGestureEvent);
      }
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    return false;
  }
}
