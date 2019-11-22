package com.furnesse.warzone.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.furnesse.warzone.CustomItem;
import com.furnesse.warzone.Lang;
import com.furnesse.warzone.Listeners;
import com.furnesse.warzone.WarzonePlugin;

public class WarzoneCMD implements CommandExecutor {

	WarzonePlugin plugin = WarzonePlugin.instance;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// TODO Auto-generated method stub
		if (sender instanceof Player) {
			Player player = (Player) sender;
			// Player commands

			if (args.length == 0) {
				if (player.hasPermission("warzone.help")) {
					for (String string : plugin.getConfigs().getLangConfig().getStringList("help")) {
						player.sendMessage(string);
					}
				} else {
					player.sendMessage(Lang.NO_PERMISSION);
				}
				return true;
			}

			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("help")) {
					if (player.hasPermission("warzone.help")) {
						for (String string : plugin.getConfigs().getLangConfig().getStringList("help")) {
							player.sendMessage(string);
						}

					} else {
						player.sendMessage(Lang.NO_PERMISSION);
					}
					return true;
				}

				if (args[0].equalsIgnoreCase("items")) {
					if (player.hasPermission("warzone.itemslist")) {
						List<String> cItems = new ArrayList<String>();
						for (CustomItem cItem : plugin.getCustomItems().getCustomItems()) {
							cItems.add(cItem.getName());
						}

						player.sendMessage(Lang.AVAILABLE_ITEMS.replaceAll("%items", cItems.toString()));

					} else {
						player.sendMessage(Lang.NO_PERMISSION);
					}
					return true;
				}

				if (args[0].equalsIgnoreCase("reload")) {
					if (player.hasPermission("warzone.reload")) {
						plugin.getConfigs().reloadConfigs(sender);
					} else {
						player.sendMessage(Lang.NO_PERMISSION);
					}
					return true;
				}

				if (args[0].equalsIgnoreCase("respawn")) {
					if (player.hasPermission("warzone.respawn")) {
						try {
							if (plugin.utils.getPlaceholders() != null) {
								if (!plugin.utils.getPlaceholders().isEmpty()) {
									plugin.utils.getPlaceholders()
											.forEach((location, material) -> location.getBlock().setType(material));
									player.sendMessage(Lang.PREFIX + "§a§lRespawned all warzone ores!");
								}
							}

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					} else {
						player.sendMessage(Lang.NO_PERMISSION);
					}
					return true;
				}
			}

			if (args.length == 3) {
				if (args[0].equalsIgnoreCase("exchange")) {
					if (player.hasPermission("warzone.exchange")) {
						try {
							Player target = Bukkit.getPlayer(args[2]);
							if(target == null) {
								sender.sendMessage(args[2] + " Is not online");
								return true;
							}
							
							String recipe = args[1];

							Listeners.exchange(player, target, recipe);

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					} else {
						player.sendMessage(Lang.NO_PERMISSION);
					}
					return true;
				}
			}

			if (args.length == 4) {
				if (args[0].equalsIgnoreCase("take")) {
					if (player.hasPermission("warzone.take")) {
						try {
							Player target = Bukkit.getPlayer(args[2]);
							ItemStack item = plugin.getCustomItems().getCustomItem(args[1]).getItemStack();
							int amount = Integer.valueOf(args[3]);

							if (item == null) {
								player.sendMessage(Lang.INVALID_ITEM);
								return true;
							}

							if (target == null) {
								player.sendMessage(Lang.PREFIX + " §c" + args[2] + " §7is not a valid player?");
								return true;
							}

							if (amount <= 0) {
								player.sendMessage(Lang.INVALID_AMOUNT);
								return true;
							}

							plugin.utils.takeItemFromInv(target, item, amount);

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					} else {
						player.sendMessage(Lang.NO_PERMISSION);
					}
					return true;
				}

				if (args[0].equalsIgnoreCase("tradein")) {
					if (player.hasPermission("warzone.tradein")) {
						try {
							Player target = Bukkit.getPlayer(args[2]);
							CustomItem cItem = plugin.getCustomItems().getCustomItem(args[1]);
							ItemStack item = cItem.getItemStack();
							int amount = Integer.valueOf(args[3]);

							if (item == null) {
								player.sendMessage(Lang.INVALID_ITEM);
								return true;
							}

							if (target == null) {
								player.sendMessage(Lang.PREFIX + " §c" + args[2] + " §7is not a valid player?");
								return true;
							}

							if (amount <= 0) {
								player.sendMessage(Lang.INVALID_AMOUNT);
								return true;
							}

							Listeners.tradeIn(sender, target, cItem, amount);

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					} else {
						player.sendMessage(Lang.NO_PERMISSION);
					}
					return true;
				}

				if (args[0].equalsIgnoreCase("give")) {
					if (player.hasPermission("warzone.give")) {
						try {
							Player target = Bukkit.getPlayer(args[2]);
							ItemStack item = plugin.getCustomItems().getCustomItem(args[1]).getItemStack();
							int amount = Integer.valueOf(args[3]);

							if (item == null) {
								player.sendMessage(Lang.INVALID_ITEM);
								return true;
							}

							if (target == null) {
								player.sendMessage(Lang.PREFIX + " §c" + args[2] + " §7is not a valid player?");
								return true;
							}

							if (amount <= 0) {
								player.sendMessage(Lang.INVALID_AMOUNT);
								return true;
							}

							plugin.utils.addItemToInv(target, item, amount);

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					} else {
						player.sendMessage(Lang.NO_PERMISSION);
					}
					return true;
				}
			}

		}

		// Console commands
		if (args.length == 0) {
			for (String string : plugin.getConfigs().getLangConfig().getStringList("help")) {
				sender.sendMessage(string);
			}
			return true;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("help")) {
				for (String string : plugin.getConfigs().getLangConfig().getStringList("help")) {
					sender.sendMessage(string);
				}
				return true;
			}

			if (args[0].equalsIgnoreCase("reload")) {
				plugin.getConfigs().reloadConfigs(sender);
				return true;
			}

			if (args[0].equalsIgnoreCase("items")) {
				List<String> cItems = new ArrayList<String>();
				for (CustomItem cItem : plugin.getCustomItems().getCustomItems()) {
					cItems.add(cItem.getName());
				}

				sender.sendMessage(Lang.AVAILABLE_ITEMS.replaceAll("%items%", cItems.toString()));
				return true;
			}
		}

		if (args.length == 3) {
			if (args[0].equalsIgnoreCase("exchange")) {
				try {
					Player target = Bukkit.getPlayer(args[2]);
					String recipe = args[1];

					Listeners.exchange(sender, target, recipe);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return true;
			}
		}

		if (args.length == 4) {
			if (args[0].equalsIgnoreCase("tradein")) {
				try {
					Player target = Bukkit.getPlayer(args[2]);
					CustomItem cItem = plugin.getCustomItems().getCustomItem(args[1]);

					int amount = Integer.valueOf(args[3]);

					if (cItem == null) {
						sender.sendMessage(Lang.INVALID_ITEM);
						return true;
					}

					if (target == null) {
						sender.sendMessage(Lang.PREFIX + " §c" + args[2] + " §7is not a valid player?");
						return true;
					}

					if (amount <= 0) {
						sender.sendMessage(Lang.INVALID_AMOUNT);
						return true;
					}

					Listeners.tradeIn(sender, target, cItem, amount);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return true;
			}

			if (args[0].equalsIgnoreCase("take")) {
				try {
					Player target = Bukkit.getPlayer(args[2]);
					ItemStack item = plugin.getCustomItems().getCustomItem(args[1]).getItemStack();
					int amount = Integer.valueOf(args[3]);

					if (item == null) {
						sender.sendMessage(Lang.INVALID_ITEM);
						return true;
					}

					if (target == null) {
						sender.sendMessage(Lang.PREFIX + " §c" + args[2] + " §7is not a valid player?");
						return true;
					}

					if (amount <= 0) {
						sender.sendMessage(Lang.INVALID_AMOUNT);
						return true;
					}

					plugin.utils.takeItemFromInv(target, item, amount);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return true;
			}

			if (args[0].equalsIgnoreCase("give")) {
				try {
					Player target = Bukkit.getPlayer(args[2]);
					ItemStack item = plugin.getCustomItems().getCustomItem(args[1]).getItemStack();
					int amount = Integer.valueOf(args[3]);

					if (item == null) {
						sender.sendMessage(Lang.INVALID_ITEM);
						return true;
					}

					if (target == null) {
						sender.sendMessage(Lang.PREFIX + " §c" + args[2] + " §7is not a valid player?");
						return true;
					}

					if (amount <= 0) {
						sender.sendMessage(Lang.INVALID_AMOUNT);
						return true;
					}

					plugin.utils.addItemToInv(target, item, amount);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return true;
			}
		}

		return false;
	}

}
