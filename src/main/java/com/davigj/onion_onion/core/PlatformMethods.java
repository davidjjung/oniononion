package com.davigj.onion_onion.core;

//? fabric {
/*import com.davigj.onion_onion.fabric.FabricPlatformMethodsImpl;
*///?}
//? neoforge {
import com.davigj.onion_onion.neoforge.NeoforgePlatformMethodsImpl;
//?}
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface PlatformMethods {

    //? fabric {
	/*PlatformMethods INSTANCE = new FabricPlatformMethodsImpl();
    *///?}
    //? neoforge {
    PlatformMethods INSTANCE = new NeoforgePlatformMethodsImpl();
    //?}


    boolean isModLoaded(String modid);

    String loader();

    boolean isFakePlayer(Player player);

	void registerCompostable(Item item, float v);

	ItemStack getRemainder(ItemStack serving);

}
