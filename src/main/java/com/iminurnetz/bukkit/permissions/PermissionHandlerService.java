/**
 * LICENSING
 * 
 * This software is copyright by sunkid <sunkid@iminurnetz.com> and is
 * distributed under a dual license:
 * 
 * Non-Commercial Use:
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Commercial Use:
 *    Please contact sunkid@iminurnetz.com
 */

package com.iminurnetz.bukkit.permissions;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.dataholder.worlds.WorldsHolder;
import org.bukkit.plugin.PluginManager;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.iminurnetz.bukkit.plugin.BukkitPlugin;
import com.nijikokun.bukkit.Permissions.Permissions;

public class PermissionHandlerService {
    private static Logger log;
	
	public static PermissionHandler getHandler(BukkitPlugin plugin) {
		
		log = plugin.getMyLogger();
		
		PermissionHandler handler = null;
		PluginManager pm = plugin.getServer().getPluginManager();
		
		if (pm.getPlugin("GroupManager") != null) {
			log.log(Level.INFO, "Using GroupManager permissions");
			GroupManager mgr = (GroupManager) pm.getPlugin("GroupManager");
			pm.enablePlugin(mgr);
			WorldsHolder wd = mgr.getWorldsHolder();
			GroupManagerPermissions gp = new GroupManagerPermissions(wd);
			handler = gp;
		} else if (pm.getPlugin("Permissions") != null) {
			log.log(Level.INFO, "Using Permissions' permissions");
			Permissions p = (Permissions) pm.getPlugin("Permissions");
			pm.enablePlugin(p);
			NijikokunPermissions np = new NijikokunPermissions();
			np.setHandler(p.getHandler());
			handler = np;
        } else if (pm.getPlugin("PermissionsEx") != null) {
            log.log(Level.INFO, "Using PermissionsEx' permissions");
            PermissionsEx p = (PermissionsEx) pm.getPlugin("PermissionsEx");
            pm.enablePlugin(p);
            PermissionManager m = PermissionsEx.getPermissionManager();
            PermissionsExPermissions pex = new PermissionsExPermissions();
            pex.setHandler(m);
            handler = pex;
		} else {
			handler = getHandler(false);
		}
		
		return handler;
	}

	public static PermissionHandler getHandler(boolean defaultPermission) {
		String verb = defaultPermission ? "allow" : "deny";
		log.log(Level.WARNING, "Setting default permissions to " + verb + " all actions");
		return new DefaultPermissions(defaultPermission);
	}
}
