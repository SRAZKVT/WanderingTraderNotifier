package wanderingtradernotifier.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.network.packet.s2c.play.MobSpawnS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(WanderingTraderEntity.class)
public abstract class WanderingTraderEntityMixin extends MerchantEntity {
    public WanderingTraderEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void readFromPacket(MobSpawnS2CPacket packet) {
        super.readFromPacket(packet);
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(new LiteralText(String.format(
                "New wandering trader spawned (%f, %f, %f)",
                packet.getX(),
                packet.getY(),
                packet.getZ()
        )), false);
    }

    @Override
    public void tick() {
        super.tick();
        this.setGlowing(true);
    }
}
