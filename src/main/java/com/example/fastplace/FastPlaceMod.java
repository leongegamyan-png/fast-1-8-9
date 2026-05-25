package com.example.fastplace;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import fml.relauncher.ReflectionHelper;

@Mod(modid = FastPlaceMod.MODID, version = FastPlaceMod.VERSION, clientSideOnly = true)
public class FastPlaceMod {
    public static final String MODID = "fastplace";
    public static final String VERSION = "1.0";

    private final Minecraft mc = Minecraft.getMinecraft();

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // Регистрируем мод в шине событий Forge
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        // Проверяем, что идет игровой тик и игрок находится в мире
        if (event.phase == TickEvent.Phase.START && mc.theWorld != null) {
            try {
                // Обнуляем 4-тиковую задержку ПКМ каждый тик через рефлексию
                // "rightClickDelayTimer" — имя поля в среде разработки
                // "field_71467_ac" — обфусцированное имя поля в игре
                ReflectionHelper.setPrivateValue(
                    Minecraft.class, 
                    mc, 
                    0, 
                    "rightClickDelayTimer", "field_71467_ac"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
