package com.amoe.wbwtlry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class Wbwtlry extends JavaPlugin implements CommandExecutor {

    private final String[] worldSizes = {"小", "中", "大"};
    private final String[] evilTypes = {"腐化", "猩红"};

    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("wbwtlry")).setExecutor(this);
        Objects.requireNonNull(this.getCommand("lbltlry")).setExecutor(this);
        getLogger().info("wbwtlry 插件已启用");
    }

    @Override
    public void onDisable() {
        getLogger().info("wbwtlry 插件已卸用");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "该指令只能由玩家使用。");
            return true;
        }

        if (command.getName().equalsIgnoreCase("wbwtlry")) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.YELLOW + "用法: /wbwtlry <createnew | notify>");
                return true;
            }

            if (args[0].equalsIgnoreCase("createnew")) {
                Random rand = new Random();
                int seed = rand.nextInt(999999999);
                String worldSize = worldSizes[rand.nextInt(worldSizes.length)];
                String evilType = evilTypes[rand.nextInt(evilTypes.length)];

                player.sendMessage(ChatColor.AQUA + "[泰拉瑞亚世界生成]");
                player.sendMessage(ChatColor.GREEN + "种子: " + ChatColor.WHITE + seed);
                player.sendMessage(ChatColor.GREEN + "世界大小: " + ChatColor.WHITE + worldSize);
                player.sendMessage(ChatColor.GREEN + "邪恶种类: " + ChatColor.WHITE + evilType);
                return true;
            }

            if (args[0].equalsIgnoreCase("notify")) {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "用法: /wbwtlry notify <玩家ID>");
                    return true;
                }
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "玩家 " + args[1] + " 不在线。");
                    return true;
                }

                target.sendTitle("§c主播该玩泰拉瑞亚了", "", 10, 70, 20);
                player.sendMessage(ChatColor.GREEN + "已通知玩家 " + target.getName());
                return true;
            }

            player.sendMessage(ChatColor.RED + "未知子命令。");
            return true;
        }

        if (command.getName().equalsIgnoreCase("lbltlry")) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!online.equals(player)) {
                    online.sendTitle("§e家人们来不来泰拉瑞亚？", "", 10, 70, 20);
                }
            }
            player.sendMessage(ChatColor.GREEN + "已向全服其他玩家发送邀请。");
            return true;
        }

        return false;
    }
}
