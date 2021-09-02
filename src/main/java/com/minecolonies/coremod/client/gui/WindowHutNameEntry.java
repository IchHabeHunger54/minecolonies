package com.minecolonies.coremod.client.gui;

import com.ldtteam.blockui.controls.Button;
import com.ldtteam.blockui.controls.ButtonHandler;
import com.ldtteam.blockui.controls.TextField;
import com.ldtteam.blockui.views.BOWindow;
import com.ldtteam.structurize.util.LanguageHandler;
import com.minecolonies.api.colony.buildings.views.IBuildingView;
import com.minecolonies.api.util.constant.Constants;
import com.minecolonies.coremod.colony.buildings.AbstractBuilding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import static com.minecolonies.api.util.constant.WindowConstants.*;

/**
 * BOWindow for a hut name entry.
 */
public class WindowHutNameEntry extends BOWindow implements ButtonHandler
{
    /**
     * The max length of the name.
     */
    private static final int MAX_NAME_LENGTH = 15;

    /**
     * Resource suffix of GUI xml file.
     */
    private static final String HUT_NAME_RESOURCE_SUFFIX = ":gui/windowhutnameentry.xml";

    /**
     * The building associated to the GUI.
     */
    private final IBuildingView building;

    /**
     * Constructor for a hut rename entry window.
     *
     * @param b {@link AbstractBuilding}
     */
    public WindowHutNameEntry(final IBuildingView b)
    {
        super(new ResourceLocation(Constants.MOD_ID + HUT_NAME_RESOURCE_SUFFIX));
        this.building = b;
    }

    @Override
    public void onOpened()
    {
        findPaneOfTypeByID(INPUT_NAME, TextField.class).setText(new TranslatableComponent(building.getCustomName().toLowerCase(Locale.ROOT)).getString());
    }

    @Override
    public void onButtonClicked(@NotNull final Button button)
    {
        if (button.getID().equals(BUTTON_DONE))
        {
            String name = findPaneOfTypeByID(INPUT_NAME, TextField.class).getText();

            if (name.length() > MAX_NAME_LENGTH)
            {
                name = name.substring(0, MAX_NAME_LENGTH);
                LanguageHandler.sendPlayerMessage(Minecraft.getInstance().player, "com.minecolonies.coremod.gui.name.toolong", name);
            }

            building.setCustomName(name);
        }
        else if (!button.getID().equals(BUTTON_CANCEL))
        {
            return;
        }

        if (building != null)
        {
            building.openGui(false);
        }
    }
}
