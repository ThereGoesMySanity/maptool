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
package net.rptools.maptool.client;

import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractListModel;
import net.rptools.maptool.model.ObservableList;
import net.rptools.maptool.model.player.Player;

public class PlayerListModel extends AbstractListModel implements Observer {

  private ObservableList<Player> playerList;

  public PlayerListModel(ObservableList<Player> playerList) {
    this.playerList = playerList;

    // TODO: Figure out how to clean this up when no longer in use
    // for now it doesn't matter, but, it's bad design
    playerList.addObserver(this);
  }

  public Object getElementAt(int index) {
    return playerList.get(index);
  }

  public int getSize() {
    return playerList.size();
  }

  ////
  // OBSERVER
  public void update(Observable o, Object arg) {
    fireContentsChanged(this, 0, playerList.size());
  }
}
