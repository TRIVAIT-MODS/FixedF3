package org.trivait.fixedf3.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.trivait.fixedf3.FixedF3;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(DebugHud.class)
public abstract class F3HudMixin {

	private boolean hasCompass() {
		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayerEntity player = client.player;
		if (player == null) return false;

		ItemStack main = player.getMainHandStack();
		ItemStack off = player.getOffHandStack();

		return ((main.isOf(Items.COMPASS) || off.isOf(Items.COMPASS)) && FixedF3.CONFIG.f3OnCompass)
				|| !FixedF3.CONFIG.modEnabled;
	}

	@Inject(method = "drawText", at = @At("HEAD"))
	private void filterDrawText(DrawContext context, List<String> text, boolean left, CallbackInfo ci) {
		if (hasCompass()) return;

		List<String> filtered = text.stream()
				.filter(line -> !(line.startsWith("XYZ") ||
						line.startsWith("Block:") ||
						line.startsWith("Chunk:") ||
						line.startsWith("Facing:") ||
						line.startsWith("Client Light:") ||
						line.startsWith("CH ") ||
						line.startsWith("SH ") ||
						line.startsWith("Biome:") ||
						line.startsWith("Local Difficulty:") ||
						line.startsWith("Blending:") ||
						line.contains("FC:") ||
						line.contains("Targeted Block") ||
						line.contains("Targeted Fluid") ||
						line.contains("Targeted Entity") ||
						line.startsWith("#minecraft:") ||
						line.matches("^[a-z_]+:.*") ||
						line.contains(": true") ||
						line.contains(": false")))
				.collect(Collectors.toList());

		text.clear();
		text.addAll(filtered);
	}
}
