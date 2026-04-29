package com.davigj.onion_onion.neoforge;

//? neoforge {
import com.davigj.onion_onion.core.PlatformMethods;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.util.FakePlayer;

public class NeoforgePlatformMethodsImpl implements PlatformMethods {

    @Override
    public boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    @Override
    public String loader() {
        return "neoforge";
    }

    @Override
    public boolean isFakePlayer(Player player) {
        return player instanceof FakePlayer;
    }

    @Override
    public void registerCompostable(Item item, float v) {
        //noop
    }

    @Override
    public ItemStack getRemainder(ItemStack serving) {
        return serving.getCraftingRemainingItem();
    }

}
//?}