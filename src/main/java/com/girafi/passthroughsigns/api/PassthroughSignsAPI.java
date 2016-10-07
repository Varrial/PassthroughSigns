package com.girafi.passthroughsigns.api;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public class PassthroughSignsAPI {
    public static IPassable passable;
    public static final Set<Block> BLOCK_PASSABLES = new HashSet<Block>();
    public static final Set<Class<? extends Entity>> ENTITY_PASSABLES = new HashSet<Class<? extends Entity>>();

    /**
     * Can be used to make a block/entity based on its registry name/class from string passable
     * Example of use of intermod communication : FMLInterModComms.sendMessage("passthroughsigns", "registerPassable", "block registry name or entity class from string");
     *
     * @param string the string of a blocks registry name or a entities class string
     */
    public static void setCanBePassed(String string) {
        if (EntityList.isStringValidEntityName(string)) {
            ENTITY_PASSABLES.add(EntityList.getClassFromID(EntityList.getIDFromString(string)));
        } else if (Block.REGISTRY.containsKey(new ResourceLocation(string))) {
            BLOCK_PASSABLES.add(Block.REGISTRY.getObject(new ResourceLocation(string)));
        }
    }

    /**
     * Convenience method for making a block passable
     *
     * @param block the block
     **/
    public static void setCanBePassed(Block block) {
        BLOCK_PASSABLES.add(block);
    }

    /**
     * Convenience method for making a entity passable
     *
     * @param entityClass the class containing the entity
     **/
    public static void setCanBePassed(Class<? extends Entity> entityClass) {
        ENTITY_PASSABLES.add(entityClass);
    }
}