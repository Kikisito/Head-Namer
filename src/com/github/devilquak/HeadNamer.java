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

public class HeadNamer extends JavaPlugin
{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("hname"))
		{
			if(args.length == 0)
			{
				sender.sendMessage(ChatColor.GRAY + "Escribe " + ChatColor.AQUA + "/hname [nombre]" + ChatColor.GRAY + " o " + ChatColor.AQUA + "/hn [nombre]" + ChatColor.GRAY + " para renombrar la cabeza de un jugador.");
			}
			else if(args.length == 1)
			{
				if(sender instanceof Player)
				{
					Player player = (Player) sender;
					if(!player.hasPermission("headnamer.name")) player.sendMessage(ChatColor.GRAY + "No tienes permiso para hacer eso.");
					else
					{
						if(player.getItemInHand().getType() == Material.PLAYER_HEAD || player.getItemInHand().getType() == Material.CREEPER_HEAD || player.getItemInHand().getType() == Material.ZOMBIE_HEAD || player.getItemInHand().getType() == Material.SKELETON_SKULL && player.getInventory().getItemInMainHand().getDurability() == 3)
						{
							player.setItemInHand(setHead(args[0], player, false));
							player.sendMessage(ChatColor.AQUA + "Cabeza renombrada a " + args[0] + ChatColor.AQUA + ".");
						}
						else
						{
							if(!player.hasPermission("headnamer.spawnnewitem")) player.sendMessage(ChatColor.GRAY + "No estás sujetando ninguna cabeza válida.");
							else
							{
								PlayerInventory inv = player.getInventory();
								if (inv.firstEmpty() == -1) player.sendMessage(ChatColor.GRAY + "No tienes espacio en el inventario.");
								else
								{
									ItemStack[] invContents = inv.getContents();
									invContents[inv.firstEmpty()] = setHead(args[0], player, true);
									inv.setContents(invContents);
									player.sendMessage(ChatColor.AQUA + "Creando la cabeza de " + args[0] + ChatColor.AQUA + ".");
								}
							}
						}
					}
				} else sender.sendMessage(ChatColor.RED + "Este comando solo puede ser usado por un jugador.");
			} else sender.sendMessage(ChatColor.GRAY + "¡Demasiados argumentos! Usa " + ChatColor.AQUA + "/hname [nombre]" + ChatColor.GRAY + " o " + ChatColor.AQUA + "/hn [nombre]");
			return true;
		} return false;
	}

	public ItemStack setHead(String skullOwner, Player player, boolean newItem)
	{
		ItemStack is;
		if(newItem) is = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
		else is = new ItemStack(Material.PLAYER_HEAD, player.getInventory().getItemInMainHand().getAmount(), (short) 3);
		SkullMeta meta = (SkullMeta) is.getItemMeta();
		meta.setOwner(skullOwner);
		is.setItemMeta(meta);
		return is;
	}
}