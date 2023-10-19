package com.stabxking.guns.client.render.gun.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stabxking.guns.client.GunModel;
import com.stabxking.guns.client.SpecialModels;
import com.stabxking.guns.client.render.gun.IOverrideModel;
import com.stabxking.guns.client.util.RenderUtil;
import com.stabxking.guns.common.Gun;
import com.stabxking.guns.init.ModSyncedDataKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.WeakHashMap;

/**
 * Author: MrCrayfish
 */
public class MiniGunModel implements IOverrideModel
{
    private WeakHashMap<LivingEntity, Rotations> rotationMap = new WeakHashMap<>();

    @Override
    public void tick(Player player)
    {
        this.rotationMap.putIfAbsent(player, new Rotations());
        Rotations rotations = this.rotationMap.get(player);
        rotations.prevRotation = rotations.rotation;

        boolean shooting = ModSyncedDataKeys.SHOOTING.getValue(player);
        ItemStack heldItem = player.getMainHandItem();
        if(!Gun.hasAmmo(heldItem) && !player.isCreative())
        {
            shooting = false;
        }

        if(shooting)
        {
            rotations.rotation += 20;
        }
        else
        {
            rotations.rotation += 1;
        }
    }

    @Override
    public void render(float partialTicks, ItemDisplayContext display, ItemStack stack, ItemStack parent, @Nullable LivingEntity entity, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light, int overlay)
    {
        Rotations rotations = entity != null ? this.rotationMap.computeIfAbsent(entity, uuid -> new Rotations()) : Rotations.ZERO;
        Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.NONE, false, poseStack, renderTypeBuffer, light, overlay, GunModel.wrap(SpecialModels.MINI_GUN_BASE.getModel()));
        poseStack.pushPose();
        RenderUtil.rotateZ(poseStack, 0.0F, -0.375F, rotations.prevRotation + (rotations.rotation - rotations.prevRotation) * partialTicks);
        Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.NONE, false, poseStack, renderTypeBuffer, light, overlay, GunModel.wrap(SpecialModels.MINI_GUN_BARRELS.getModel()));
        poseStack.popPose();
    }

    private static class Rotations
    {
        private static final Rotations ZERO = new Rotations();

        private int rotation;
        private int prevRotation;
    }

    @SubscribeEvent
    public void onClientDisconnect(ClientPlayerNetworkEvent.LoggingOut event)
    {
        this.rotationMap.clear();
    }
}
