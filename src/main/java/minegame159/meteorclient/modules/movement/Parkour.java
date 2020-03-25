package minegame159.meteorclient.modules.movement;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import minegame159.meteorclient.events.TickEvent;
import minegame159.meteorclient.modules.Category;
import minegame159.meteorclient.modules.Module;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;

import java.util.stream.Stream;

public class Parkour extends Module {
    public static Parkour INSTANCE;

    public Parkour() {
        super(Category.Movement, "parkour", "Automatically jumps at the edges of blocks.");
    }

    @EventHandler
    private Listener<TickEvent> onTick = new Listener<>(event -> {
        if(!mc.player.onGround || mc.options.keyJump.isPressed()) return;

        if(mc.player.isSneaking() || mc.options.keySneak.isPressed()) return;

        Box box = mc.player.getBoundingBox();
        Box adjustedBox = box.offset(0, -0.5, 0).expand(-0.001, 0, -0.001);

        Stream<VoxelShape> blockCollisions = mc.world.method_20812(mc.player, adjustedBox);

        if(blockCollisions.findAny().isPresent()) return;

        mc.player.jump();
    });
}