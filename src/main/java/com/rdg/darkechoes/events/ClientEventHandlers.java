package com.rdg.darkechoes.events;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.helpers.AugmentEffectComponents;
import com.rdg.darkechoes.helpers.AugmentHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ExtractBlockOutlineRenderStateEvent;
import org.joml.Matrix4f;

import java.util.List;

@EventBusSubscriber(modid = DarkEchoes.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandlers {
    @SubscribeEvent
    public static void updateBlockBounds(ExtractBlockOutlineRenderStateEvent event) {
        Entity entity = event.getCamera().entity();
        Minecraft client = Minecraft.getInstance();
        Vec3 cameraPos = client.gameRenderer.getMainCamera().position();

        if (client.level == null || client.player == null || !AugmentHelper.has(client.player.getMainHandItem(), AugmentEffectComponents.LARGER_BLOCK_BREAK_RADIUS))
            return;

        BlockPos mainBlock = event.getBlockPos();
        BlockPos above = mainBlock.above();
        BlockPos below = mainBlock.below();
        BlockPos north = mainBlock.north();
        BlockPos south = mainBlock.south();
        BlockPos west = mainBlock.west();
        BlockPos east = mainBlock.east();
        assert entity != null;
        Direction side = entity.getNearestViewDirection();

        BlockPos tl = (side == Direction.NORTH || side == Direction.SOUTH) ? mainBlock.offset(1, 1, 0) : ((side == Direction.WEST || side == Direction.EAST) ? mainBlock.offset(0, 1, 1) : mainBlock.offset(1, 0, 1));
        BlockPos tr = (side == Direction.NORTH || side == Direction.SOUTH) ? mainBlock.offset(-1, 1, 0) : ((side == Direction.WEST || side == Direction.EAST) ? mainBlock.offset(0, 1, -1) : mainBlock.offset(1, 0, -1));
        BlockPos bl = (side == Direction.NORTH || side == Direction.SOUTH) ? mainBlock.offset(1, -1, 0) : ((side == Direction.WEST || side == Direction.EAST) ? mainBlock.offset(0, -1, 1) : mainBlock.offset(-1, 0, 1));
        BlockPos br = (side == Direction.NORTH || side == Direction.SOUTH) ? mainBlock.offset(-1, -1, 0) : ((side == Direction.WEST || side == Direction.EAST) ? mainBlock.offset(0, -1, -1) : mainBlock.offset(-1, 0, -1));

        List<BlockPos> blockPosListVert = List.of(tr, north, tl, west, east, br, south, bl, mainBlock);
        List<BlockPos> blockPosListNS = List.of(tr, above, tl, west, east, br, below, bl, mainBlock);
        List<BlockPos> blockPosListWE = List.of(tr, above, tl, north, south, br, below, bl, mainBlock);

        ItemStack mainHand = client.player.getMainHandItem();

        event.addCustomRenderer((_, buffer, poseStack, _, _) -> {
            if (!mainHand.isCorrectToolForDrops(client.level.getBlockState(mainBlock)) || client.player.isShiftKeyDown()) return false;
            if (side == Direction.UP || side == Direction.DOWN) {
                blockPosListVert.forEach(pos -> {
                    BlockState state = client.level.getBlockState(pos);
                    if (!mainHand.isCorrectToolForDrops(state)) return;
                    poseStack.pushPose();
                    poseStack.translate(pos.getX() - cameraPos.x, pos.getY() - cameraPos.y, pos.getZ() - cameraPos.z);
                    VoxelShape shape = client.level.getBlockState(pos).getShape(client.level, pos);
                    Matrix4f matrix = poseStack.last().pose();
                    VertexConsumer vc = buffer.getBuffer(RenderTypes.lines());
                    shape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) -> {
                        float x1 = (float) minX, y1 = (float) minY, z1 = (float) minZ;
                        float dx = (float) (maxX - minX), dy = (float) (maxY - minY), dz = (float) (maxZ - minZ);
                        float length = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
                        if (length < 1e-6f) return;
                        float nx = dx / length, ny = dy / length, nz = dz / length;
                        vc.addVertex(matrix, x1 + nx * 0.0f, y1 + ny * 0.0f, z1 + nz * 0.0f).setColor(0, 0, 0, 1f).setNormal(nx, ny, nz).setLineWidth(1);
                        vc.addVertex(matrix, x1 + nx * length, y1 + ny * length, z1 + nz * length).setColor(0, 0, 0, 1f).setNormal(nx, ny, nz).setLineWidth(1);
                    });
                    poseStack.popPose();
                });
                buffer.endBatch(RenderTypes.lines());
            } else if (side == Direction.NORTH || side == Direction.SOUTH) {
                blockPosListNS.forEach(pos -> {
                    BlockState state = client.level.getBlockState(pos);
                    if (!mainHand.isCorrectToolForDrops(state)) return;
                    poseStack.pushPose();
                    poseStack.translate(pos.getX() - cameraPos.x, pos.getY() - cameraPos.y, pos.getZ() - cameraPos.z);
                    VoxelShape shape = client.level.getBlockState(pos).getShape(client.level, pos);
                    Matrix4f matrix = poseStack.last().pose();
                    VertexConsumer vc = buffer.getBuffer(RenderTypes.lines());
                    shape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) -> {
                        float x1 = (float) minX, y1 = (float) minY, z1 = (float) minZ;
                        float dx = (float) (maxX - minX), dy = (float) (maxY - minY), dz = (float) (maxZ - minZ);
                        float length = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
                        if (length < 1e-6f) return;
                        float nx = dx / length, ny = dy / length, nz = dz / length;
                        vc.addVertex(matrix, x1 + nx * 0.0f, y1 + ny * 0.0f, z1 + nz * 0.0f).setColor(0, 0, 0, 1f).setNormal(nx, ny, nz).setLineWidth(1);
                        vc.addVertex(matrix, x1 + nx * length, y1 + ny * length, z1 + nz * length).setColor(0, 0, 0, 1f).setNormal(nx, ny, nz).setLineWidth(1);
                    });
                    poseStack.popPose();
                });
                buffer.endBatch(RenderTypes.lines());
            } else if (side == Direction.WEST || side == Direction.EAST) {
                blockPosListWE.forEach(pos -> {
                    BlockState state = client.level.getBlockState(pos);
                    if (!mainHand.isCorrectToolForDrops(state)) return;
                    poseStack.pushPose();
                    poseStack.translate(pos.getX() - cameraPos.x, pos.getY() - cameraPos.y, pos.getZ() - cameraPos.z);
                    VoxelShape shape = client.level.getBlockState(pos).getShape(client.level, pos);
                    Matrix4f matrix = poseStack.last().pose();
                    VertexConsumer vc = buffer.getBuffer(RenderTypes.lines());
                    shape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) -> {
                        float x1 = (float) minX, y1 = (float) minY, z1 = (float) minZ;
                        float dx = (float) (maxX - minX), dy = (float) (maxY - minY), dz = (float) (maxZ - minZ);
                        float length = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
                        if (length < 1e-6f) return;
                        float nx = dx / length, ny = dy / length, nz = dz / length;
                        vc.addVertex(matrix, x1 + nx * 0.0f, y1 + ny * 0.0f, z1 + nz * 0.0f).setColor(0, 0, 0, 1f).setNormal(nx, ny, nz).setLineWidth(1);
                        vc.addVertex(matrix, x1 + nx * length, y1 + ny * length, z1 + nz * length).setColor(0, 0, 0, 1f).setNormal(nx, ny, nz).setLineWidth(1);
                    });
                    poseStack.popPose();
                });
                buffer.endBatch(RenderTypes.lines());
            }
            return true;
        });
    }
}
