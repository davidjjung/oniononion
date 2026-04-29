package com.davigj.onion_onion.fabric;

//? fabric {
/*import com.davigj.onion_onion.core.PlatformMethods;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FabricPlatformMethodsImpl implements PlatformMethods {

    @Override
    public boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }

    @Override
    public String loader() {
        return "fabric";
    }

    public boolean isFakePlayer(Player player) {
        return player instanceof FakePlayer;
    }

    public void registerCompostable(Item item, float v) {
        CompostingChanceRegistry.INSTANCE.add(item, v);
    }

    public ItemStack getRemainder(ItemStack serving) {
        return serving.getRecipeRemainder();
    }

}
*///?}