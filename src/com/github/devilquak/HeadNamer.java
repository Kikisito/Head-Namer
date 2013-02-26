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
				sender.sendMessage(ChatColor.AQUA + "Head Namer" + ChatColor.GRAY + " Version 1.1");
				sender.sendMessage(ChatColor.GRAY + "Coded by devilquak");
				sender.sendMessage(ChatColor.GRAY + "Type " + ChatColor.AQUA + "/hname [Name]" + ChatColor.GRAY + " or " + ChatColor.AQUA + "/hn [Name]" + ChatColor.GRAY + " to rename a player head.");
			}
			else if(args.length == 1)
			{
				if(sender instanceof Player)
				{
					Player player = (Player)sender;
					if(!player.hasPermission("headnamer.name")) player.sendMessage(ChatColor.GRAY + "You don't have permission to do that.");
					else
					{
						if(player.getItemInHand().getType() == Material.SKULL_ITEM&&player.getItemInHand().getDurability() == 3)
						{
							player.setItemInHand(setHead(args[0], player, false));
							player.sendMessage(ChatColor.AQUA + "Head owner changed to " + args[0] + ChatColor.AQUA + ".");
						}
						else
						{
							if(!player.hasPermission("headnamer.spawnnewitem")) player.sendMessage(ChatColor.GRAY + "You are not holding a player head, and do not have permission to spawn a new named one.");
							else
							{
								PlayerInventory inv = player.getInventory();
								if (inv.firstEmpty() == -1) player.sendMessage(ChatColor.GRAY + "You don't have enough room in your inventory for that.");
								else
								{
									ItemStack[] invContents = inv.getContents();
									invContents[inv.firstEmpty()] = setHead(args[0], player, true);
									inv.setContents(invContents);
									player.sendMessage(ChatColor.AQUA + "Spawning " + args[0] + ChatColor.AQUA + "'s head.");
								}
							}
						}
					}
				} else sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
			} else sender.sendMessage(ChatColor.GRAY + "Too many arguments! Use " + ChatColor.AQUA + "/hname [Name]" + ChatColor.GRAY + " or " + ChatColor.AQUA + "/hn [Name]");
			return true;
		} return false;
	}

	public ItemStack setHead(String skullOwner, Player player, boolean newItem)
	{
		ItemStack is;
		if(newItem) is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		else is = new ItemStack(Material.SKULL_ITEM, player.getItemInHand().getAmount(), (short) 3);
		SkullMeta meta = (SkullMeta) is.getItemMeta();
		meta.setOwner(skullOwner);
		is.setItemMeta(meta);
		return is;
	}
}