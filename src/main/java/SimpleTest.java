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
        Set<Integer> itemStacksIndex  = new LinkedHashSet<Integer>();
        Location location = e.getEntity().getLocation();
        if (e.getEntity() instanceof Player)
        {
            e.getEntity().sendMessage("傻瓜你死掉了");
            Inventory playerInventory = ((Player) e.getEntity()).getInventory();
            ItemStack[] contents = playerInventory.getContents();
            for (int i = 0; i < contents.length; i++)
            {
                if(contents[i]!=null)
                {
                    itemStacksIndex.add(i);
                }
            }
            if (itemStacksIndex.isEmpty())
                return;
            Set<Integer> size = new LinkedHashSet<Integer>();
            while (size.size() < random.nextInt(10)+1)
            {
                int randomNum = random.nextInt(41);
                if(itemStacksIndex.contains(randomNum))
                size.add(randomNum);
            }
            Iterator<Integer> iterator = size.iterator();
            while (iterator.hasNext())
            {
                temp = iterator.next();
                ((Player) e.getEntity()).getWorld().dropItemNaturally(location, contents[temp]);
                e.getEntity().sendMessage("丢失了: "+contents[temp].getType().name() + "  数量: "+ contents[temp].getAmount());
                contents[temp].setAmount(0);
            }
            size.clear();
            itemStacksIndex.clear();
        }

    }


}
