package com.pettheanimals;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.util.Text;

import java.lang.reflect.Method;
import java.util.function.Predicate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@PluginDescriptor(
    name = "Pet the Animals",
    description = "Adds a right-click 'Pet' option to NPCs and makes your character react.",
    tags = {"pet", "animals", "fun", "emote"}
)
public class PetTheAnimalsPlugin extends Plugin
{
    private static final String PET_OPTION = "Pet";
    private static final int OVERHEAD_TEXT_DURATION_MS = 2000;
    private java.util.concurrent.ScheduledFuture<?> clearOverheadTask;

    @Inject
    private Client client;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Inject
    private ConfigManager configManager;

    @Inject
    private MenuManager menuManager;

    @Inject
    private PetTheAnimalsConfig config;

    @Override
    protected void startUp() throws Exception
    {
        log.info("Pet the Animals started");
    }

    @Override
    protected void shutDown() throws Exception
    {
        if (clearOverheadTask != null) {
            clearOverheadTask.cancel(true);
        }
        scheduler.shutdownNow();
        log.info("Pet the Animals stopped");
    }

    private void registerPetMenu()
    {
        // Try newest to oldest APIs via reflection so we compile against any version
        // 1) addNpcMenuEntry(String, MenuAction, Predicate<NPC>)
        try
        {
            Method m = MenuManager.class.getMethod("addNpcMenuEntry", String.class, MenuAction.class, Predicate.class);
            Predicate<Object> anyNpc = o -> true;
            m.invoke(menuManager, PET_OPTION, MenuAction.RUNELITE, anyNpc);
            log.debug("Registered Pet option via addNpcMenuEntry");
            return;
        }
        catch (NoSuchMethodException ignored) {}
        catch (Exception e)
        {
            log.debug("addNpcMenuEntry failed:", e);
        }

        // 2) addNpcMenuItem(String, Predicate<NPC>)
        try
        {
            Method m = MenuManager.class.getMethod("addNpcMenuItem", String.class, Predicate.class);
            Predicate<Object> anyNpc = o -> true;
            m.invoke(menuManager, PET_OPTION, anyNpc);
            log.debug("Registered Pet option via addNpcMenuItem(String, Predicate)");
            return;
        }
        catch (NoSuchMethodException ignored) {}
        catch (Exception e)
        {
            log.debug("addNpcMenuItem(String, Predicate) failed:", e);
        }

        // 3) addNpcMenuItem(String)
        try
        {
            Method m = MenuManager.class.getMethod("addNpcMenuItem", String.class);
            m.invoke(menuManager, PET_OPTION);
            log.debug("Registered Pet option via addNpcMenuItem(String)");
            return;
        }
        catch (NoSuchMethodException ignored) {}
        catch (Exception e)
        {
            log.debug("addNpcMenuItem(String) failed:", e);
        }

        // 4) addPriorityEntry(String, MenuAction, int/Integer, int/Integer)
        try
        {
            // Try Integer params
            Method m = MenuManager.class.getMethod("addPriorityEntry", String.class, MenuAction.class, Integer.class, Integer.class);
            m.invoke(menuManager, PET_OPTION, MenuAction.RUNELITE, null, null);
            log.debug("Registered Pet option via addPriorityEntry (Integer)");
            return;
        }
        catch (NoSuchMethodException ignored) {}
        catch (Exception e)
        {
            log.debug("addPriorityEntry(Integer) failed:", e);
        }
        try
        {
            // Try primitive int params
            Method m = MenuManager.class.getMethod("addPriorityEntry", String.class, MenuAction.class, int.class, int.class);
            m.invoke(menuManager, PET_OPTION, MenuAction.RUNELITE, 0, 0);
            log.debug("Registered Pet option via addPriorityEntry (int)");
            return;
        }
        catch (NoSuchMethodException ignored) {}
        catch (Exception e)
        {
            log.warn("Failed to register Pet option via MenuManager; plugin will run without the custom menu.");
            log.debug("addPriorityEntry(int) failed:", e);
        }
    }

    private void unregisterPetMenu()
    {
        // Mirror the above removal variants
        try
        {
            Method m = MenuManager.class.getMethod("removeNpcMenuEntry", String.class, MenuAction.class, Predicate.class);
            Predicate<Object> anyNpc = o -> true;
            m.invoke(menuManager, PET_OPTION, MenuAction.RUNELITE, anyNpc);
            log.debug("Unregistered Pet option via removeNpcMenuEntry");
            return;
        }
        catch (NoSuchMethodException ignored) {}
        catch (Exception e)
        {
            log.debug("removeNpcMenuEntry failed:", e);
        }

        try
        {
            Method m = MenuManager.class.getMethod("removeNpcMenuItem", String.class, Predicate.class);
            Predicate<Object> anyNpc = o -> true;
            m.invoke(menuManager, PET_OPTION, anyNpc);
            log.debug("Unregistered Pet option via removeNpcMenuItem(String, Predicate)");
            return;
        }
        catch (NoSuchMethodException ignored) {}
        catch (Exception e)
        {
            log.debug("removeNpcMenuItem(String, Predicate) failed:", e);
        }

        try
        {
            Method m = MenuManager.class.getMethod("removeNpcMenuItem", String.class);
            m.invoke(menuManager, PET_OPTION);
            log.debug("Unregistered Pet option via removeNpcMenuItem(String)");
            return;
        }
        catch (NoSuchMethodException ignored) {}
        catch (Exception e)
        {
            log.debug("removeNpcMenuItem(String) failed:", e);
        }

        try
        {
            Method m = MenuManager.class.getMethod("removePriorityEntry", String.class, MenuAction.class, Integer.class, Integer.class);
            m.invoke(menuManager, PET_OPTION, MenuAction.RUNELITE, null, null);
            log.debug("Unregistered Pet option via removePriorityEntry (Integer)");
            return;
        }
        catch (NoSuchMethodException ignored) {}
        catch (Exception e)
        {
            log.debug("removePriorityEntry(Integer) failed:", e);
        }
        try
        {
            Method m = MenuManager.class.getMethod("removePriorityEntry", String.class, MenuAction.class, int.class, int.class);
            m.invoke(menuManager, PET_OPTION, MenuAction.RUNELITE, 0, 0);
            log.debug("Unregistered Pet option via removePriorityEntry (int)");
            return;
        }
        catch (NoSuchMethodException ignored) {}
        catch (Exception e)
        {
            log.debug("removePriorityEntry(int) failed:", e);
        }
    }

    private boolean isWhitelisted(String npcName)
    {
        if (npcName == null || npcName.isEmpty())
        {
            return false;
        }
        // Remove level suffixes and trim
        String name = npcName.replaceAll("\\s*\\(level-?\\d+\\)\\s*", "").trim();
        // Case-insensitive exact match against our whitelist
        for (String allowed : PetResponses.WHITELISTED_NPCS)
        {
            if (allowed.equalsIgnoreCase(name))
            {
                return true;
            }
        }
        return false;
    }

    private boolean hasPetEntry(String target)
    {
        // Try newer menu API first
        try
        {
            MenuEntry[] entries = client.getMenu().getMenuEntries();
            if (entries != null)
            {
                for (MenuEntry e : entries)
                {
                    if (e != null && PET_OPTION.equals(e.getOption()) && target.equals(e.getTarget()))
                    {
                        return true;
                    }
                }
            }
            return false;
        }
        catch (Throwable t)
        {
            // Fallback to older API
            try
            {
                MenuEntry[] entries = client.getMenuEntries();
                if (entries != null)
                {
                    for (MenuEntry e : entries)
                    {
                        if (e != null && PET_OPTION.equals(e.getOption()) && target.equals(e.getTarget()))
                        {
                            return true;
                        }
                    }
                }
            }
            catch (Throwable ignored) {}
        }
        return false;
    }

    private NPC getNpcByIndex(int index)
    {
        // Preferred: newer API provides a List<NPC>
        try
        {
            java.util.List<NPC> list = client.getNpcs();
            if (list != null)
            {
                // First try to match by runtime NPC index (most reliable)
                for (NPC n : list)
                {
                    if (n == null) { continue; }
                    try
                    {
                        if (n.getIndex() == index)
                        {
                            return n;
                        }
                    }
                    catch (Throwable ignored)
                    {
                        // getIndex() may not exist on very old builds; fall through
                    }
                }

                // Positional fallback if this build's identifier aligns with list order
                if (index >= 0 && index < list.size())
                {
                    return list.get(index);
                }
            }
        }
        catch (Throwable ignored)
        {
            // If getNpcs() is not available at runtime, fall back to very old API via reflection
        }

        // Reflection fallback for very old RuneLite builds that still had getCachedNPCs()
        try
        {
            java.lang.reflect.Method m = client.getClass().getMethod("getCachedNPCs");
            Object result = m.invoke(client);
            if (result instanceof NPC[])
            {
                NPC[] arr = (NPC[]) result;
                if (arr != null && index >= 0 && index < arr.length)
                {
                    return arr[index];
                }
            }
        }
        catch (Throwable ignored) {}

        return null;
    }

    private boolean withinTiles(WorldPoint a, WorldPoint b, int tiles)
    {
        if (a == null || b == null)
        {
            return false;
        }
        int dx = Math.abs(a.getX() - b.getX());
        int dy = Math.abs(a.getY() - b.getY());
        // Chebyshev distance on the same plane
        return Math.max(dx, dy) <= tiles && a.getPlane() == b.getPlane();
    }


    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event)
    {
        // Only add for NPC-related menu builds
        final MenuAction type = MenuAction.of(event.getType());
        final boolean isNpcMenu =
            type == MenuAction.NPC_FIRST_OPTION ||
            type == MenuAction.NPC_SECOND_OPTION ||
            type == MenuAction.NPC_THIRD_OPTION ||
            type == MenuAction.NPC_FOURTH_OPTION ||
            type == MenuAction.NPC_FIFTH_OPTION ||
            type == MenuAction.EXAMINE_NPC;

        if (!isNpcMenu)
        {
            return;
        }

        final String npcName = Text.removeTags(event.getTarget());
        if (!isWhitelisted(npcName))
        {
            return; // respect whitelist
        }

        // Avoid duplicates if another mechanism already added it
        if (hasPetEntry(event.getTarget()))
        {
            return;
        }

        // Insert near the top. Try new API first, then fall back to older client.createMenuEntry
        try
        {
            // Newer API path
            client.getMenu()
                .createMenuEntry(0)
                .setOption(PET_OPTION)
                .setTarget(event.getTarget())
                .setType(MenuAction.RUNELITE)
                .setIdentifier(event.getIdentifier())
                .setParam0(event.getActionParam0())
                .setParam1(event.getActionParam1());
        }
        catch (Throwable t)
        {
            // Older API path
            client.createMenuEntry(0)
                .setOption(PET_OPTION)
                .setTarget(event.getTarget())
                .setType(MenuAction.RUNELITE)
                .setIdentifier(event.getIdentifier())
                .setParam0(event.getActionParam0())
                .setParam1(event.getActionParam1());
        }
    }

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked event)
    {
        // Only handle our custom Pet option added via MenuManager (RUNELITE action)
        if (!PET_OPTION.equals(event.getMenuOption()))
        {
            return;
        }

        if (event.getMenuAction() != MenuAction.RUNELITE)
        {
            return;
        }

        final String target = event.getMenuTarget() != null ? Text.removeTags(event.getMenuTarget()) : "";
        final String rawName = target;
        if (rawName.isEmpty() || !isWhitelisted(rawName))
        {
            return;
        }

        // Require player to be within 2 tiles of the target NPC
        if (client.getLocalPlayer() != null)
        {
            NPC npc = getNpcByIndex(event.getId());
            if (npc != null)
            {
                WorldPoint me = client.getLocalPlayer().getWorldLocation();
                WorldPoint them = npc.getWorldLocation();
                if (!withinTiles(me, them, 2))
                {
                    client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "You need to be closer to do that.", null);
                    event.consume();
                    return;
                }
            }
        }

        final String line = PetResponses.buildLine(rawName);

        // Show overhead text and optional chat message
        if (config.enableOverheadText() && client.getLocalPlayer() != null)
        {
            client.getLocalPlayer().setOverheadText(line);
            if (clearOverheadTask != null && !clearOverheadTask.isDone()) {
                clearOverheadTask.cancel(true);
            }
            clearOverheadTask = scheduler.schedule(() -> {
                if (client.getLocalPlayer() != null) {
                    client.getLocalPlayer().setOverheadText(null);
                }
            }, OVERHEAD_TEXT_DURATION_MS, TimeUnit.MILLISECONDS);
        }

        if (config.enableChatMessage())
        {
            client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", line, null);
        }

        // Mark event handled to prevent fallthrough
        event.consume();
    }

    @Provides
    PetTheAnimalsConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(PetTheAnimalsConfig.class);
    }
}
