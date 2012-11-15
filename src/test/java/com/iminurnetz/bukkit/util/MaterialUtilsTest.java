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

package com.iminurnetz.bukkit.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.iminurnetz.bukkit.util.MaterialUtils;

public class MaterialUtilsTest extends TestCase {

	public void testSimpleRetrieval() {
		assertEquals(Material.AIR, MaterialUtils.getMaterialByName("AIR"));
		assertEquals(Material.STONE, MaterialUtils.getMaterialByName("stone"));
		assertEquals(Material.COOKED_FISH, MaterialUtils.getMaterialByName("Cooked Fish"));
		assertEquals(Material.JACK_O_LANTERN, MaterialUtils.getMaterialByName("Jack-o'-Lantern"));
	}
	
	public void testNameFormatting() {
		List <String> materials = MaterialUtils.getFormattedNameList();
		for (String s : materials) {
			Material m = MaterialUtils.getMaterialByName(s);
			assertNotNull(m);
		}
	}
	
	public void testGetMatchingMaterials() {
		List<Material> materials = Arrays.asList(Material.AIR, Material.APPLE, Material.ARROW, Material.ANVIL);
		Collections.sort(materials);
		assertEquals(materials, getSorted(MaterialUtils.getMatchingMaterials("a")));
		materials = Arrays.asList(Material.GOLD_SPADE, Material.GOLD_ORE, Material.GOLD_PICKAXE, Material.GOLD_AXE, Material.GOLD_HOE, Material.GOLD_CHESTPLATE, Material.GOLDEN_APPLE);
		Collections.sort(materials);
		assertEquals(materials, getSorted(MaterialUtils.getMatchingMaterials("Go*e")));	
	}
	
	public void testGetList() {
		List<Material> materials = Arrays.asList(Material.GOLD_ORE);
		assertEquals(materials, MaterialUtils.getList("gold_ore"));
		assertEquals(materials, MaterialUtils.getList("gold ore"));
	}

	public void testIsDamageable() {
		for (int id = 1; id <= 2257; id++) {
			if (!isDamageable(id))
				continue;
			Material m = Material.getMaterial(id);
			if (m != null)
				assertTrue(m.name(), MaterialUtils.isDamageable(m));
		}
	}
	
	public void testShortCuts() {
		assertTrue("grass is grass", MaterialUtils.isSameMaterial(Material.GRASS, Material.GRASS));
		assertTrue("Grass is dirt", MaterialUtils.isDirt(Material.GRASS));
	}
	
	public void notestReplaceDropped() {
		for (Material m : Material.values()) {
			System.out.print(MaterialUtils.getFormattedName(m) + " => ");
			List<ItemStack> items = MaterialUtils.getDroppedMaterial(m, (byte) 7);
			for (ItemStack i : items) {
				System.out.print(i.getAmount() + " x " + MaterialUtils.getFormattedName(i.getType()));
			}
			
			System.out.print("\n");
		}
	}
	private List<Material> getSorted(List<Material> list) {
		ArrayList<Material> result = new ArrayList<Material>();
		result.addAll(list);
		Collections.sort(result);
		return result;
	}
	
	private static boolean isDamageable(int id) {
		if ((id >= 256) && (id <= 259)) {
			return true;
		}
		if ((id >= 267) && (id <= 279)) {
			return true;
		}
		if ((id >= 283) && (id <= 286)) {
			return true;
		}
		if ((id >= 290) && (id <= 294)) {
			return true;
		}
		if ((id >= 298) && (id <= 317)) {
			return true;
		}

		return id == 346;
	}
}
