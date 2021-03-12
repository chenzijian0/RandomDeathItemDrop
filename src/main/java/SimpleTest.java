import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class SimpleTest extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        System.out.println("自定义的插件启动了...................");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public static void myPlugin(PlayerDeathEvent e)
    {
        int temp;
        Random random = new Random();
        int numberLost = random.nextInt(15) + 1;
        Set<Integer> itemStacksIndex = new LinkedHashSet<Integer>();
        Location location = e.getEntity().getLocation();
        Player player;
        if (e.getEntity() instanceof Player)
        {
            player = (Player) e.getEntity();
            player.sendMessage("嗷...你死掉了");
            player.sendMessage("扣除10级经验");
            if(player.getLevel() >=10)
            {
                player.setLevel(player.getLevel()-10);
            }
            else
            {
                player.setLevel(0);
            }
            Inventory playerInventory = player.getInventory();
            ItemStack[] contents = playerInventory.getContents();
            for (int i = 0; i < contents.length; i++)
            {
                if (contents[i] != null)
                {
                    itemStacksIndex.add(i);
                }
            }
            if (itemStacksIndex.isEmpty()) return;
            Set<Integer> size = new LinkedHashSet<Integer>();
            while (size.size() < numberLost && size.size() < itemStacksIndex.size())
            {
                int randomNum = random.nextInt(41);
                if (itemStacksIndex.contains(randomNum)) size.add(randomNum);
            }
            Iterator<Integer> iterator = size.iterator();
            while (iterator.hasNext())
            {
                temp = iterator.next();
                player.getWorld().dropItemNaturally(location, contents[temp]);
                player.sendMessage("丢失了: " + contents[temp].getType().name() + "  数量: " + contents[temp].getAmount());
                contents[temp].setAmount(0);
            }
            size.clear();
            itemStacksIndex.clear();

        }

    }


}
