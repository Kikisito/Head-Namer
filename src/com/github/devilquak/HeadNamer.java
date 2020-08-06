package com.github.devilquak;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class HeadNamer extends JavaPlugin {


	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (args.length != 1) {
				return false;
			}

			Player player = (Player) sender;
			PlayerInventory inv = player.getInventory();
			
			if(inv.getItemInMainHand().getType() == Material.PLAYER_HEAD || inv.getItemInMainHand().getType() == Material.CREEPER_HEAD || inv.getItemInMainHand().getType() == Material.ZOMBIE_HEAD || inv.getItemInMainHand().getType() == Material.SKELETON_SKULL) {
				inv.setItemInMainHand(makeHead(args[0], inv.getItemInMainHand().getAmount()));
				player.sendMessage(ChatColor.AQUA + "Cabeza renombrada a " + args[0] + ChatColor.AQUA + ".");
			} else {
				if (!player.hasPermission("headnamer.spawnnewitem")) {
					player.sendMessage(ChatColor.GRAY + "No estás sujetando ninguna cabeza válida.");
				} else {
					if (inv.addItem(makeHead(args[0], 1)).isEmpty()) {
						player.sendMessage(ChatColor.AQUA + "Creada la cabeza de " + args[0] + ChatColor.AQUA + ".");
					} else {
						player.sendMessage(ChatColor.GRAY + "No tienes espacio en el inventario.");
					}
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Este comando solo puede ser usado por un jugador.");
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public ItemStack makeHead(String skullOwner, int amount) {
		ItemStack is = new ItemStack(Material.PLAYER_HEAD, amount);
		SkullMeta meta = (SkullMeta) is.getItemMeta();
		meta.setOwner(skullOwner);
		is.setItemMeta(meta);
		return is;
	}
}