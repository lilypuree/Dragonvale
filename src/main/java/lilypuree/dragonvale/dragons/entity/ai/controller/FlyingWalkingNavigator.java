package lilypuree.dragonvale.dragons.entity.ai.controller;

import lilypuree.dragonvale.dragons.entity.DragonEntityBase;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class FlyingWalkingNavigator extends PathNavigator {
    PathNavigator groundNavigator;
    PathNavigator flyingNavigator;
    DragonEntityBase entity;

    public FlyingWalkingNavigator(DragonEntityBase entity, World worldIn, PathNavigator ground, PathNavigator flying) {
        super(entity, worldIn);
        this.groundNavigator = ground;
        this.flyingNavigator = flying;
        this.entity = entity;
    }

    @Override
    public void resetRangeMultiplier() {
        this.flyingNavigator.resetRangeMultiplier();
        this.groundNavigator.resetRangeMultiplier();
    }

    @Override
    public void setRangeMultiplier(float range) {
        flyingNavigator.setRangeMultiplier(range);
        groundNavigator.setRangeMultiplier(range);
    }

    @Override
    public BlockPos getTargetPos() {
        if (entity.isFlying()) {
            return flyingNavigator.getTargetPos();
        } else {
            return groundNavigator.getTargetPos();
        }
    }

    @Override
    public void setSpeed(double speed) {
        flyingNavigator.setSpeed(speed);
        groundNavigator.setSpeed(speed);
    }

    @Override
    public boolean canUpdatePathOnTimeout() {
        if (entity.isFlying()) {
            return flyingNavigator.canUpdatePathOnTimeout();
        } else {
            return groundNavigator.canUpdatePathOnTimeout();
        }
    }

    @Override
    public void updatePath() {
        if (entity.isFlying()) {
            flyingNavigator.updatePath();
        } else {
            groundNavigator.updatePath();
        }
    }

    @Nullable
    @Override
    public Path func_225463_a(Stream<BlockPos> stream, int i) {
        if (entity.isFlying()) {
            return flyingNavigator.func_225463_a(stream, i);
        } else {
            return groundNavigator.func_225463_a(stream, i);
        }
    }

    @Nullable
    @Override
    public Path getPathToPos(BlockPos pos, int i) {
        if (entity.isFlying()) {
            return flyingNavigator.getPathToPos(pos, i);
        } else {
            return groundNavigator.getPathToPos(pos, i);
        }
    }

    @Nullable
    @Override
    public Path getPathToEntity(Entity entityIn, int i) {
        if (entity.isFlying()) {
            return flyingNavigator.getPathToEntity(entityIn, i);
        } else {
            return groundNavigator.getPathToEntity(entityIn, i);
        }
    }

    @Override
    public boolean setPath(@Nullable Path path, double speedModifier) {
        if (entity.isFlying()) {
            return flyingNavigator.setPath(path, speedModifier);
        } else {
            return groundNavigator.setPath(path, speedModifier);
        }
    }

    @Nullable
    @Override
    public Path getPath() {
        if (entity.isFlying()) {
            return flyingNavigator.getPath();
        } else {
            return groundNavigator.getPath();
        }
    }

    @Override
    public void tick() {
        if (entity.isFlying()) {
            flyingNavigator.tick();
        } else {
            groundNavigator.tick();
        }
    }

    @Override
    public boolean noPath() {
        if (entity.isFlying()) {
            return flyingNavigator.noPath();
        } else {
            return groundNavigator.noPath();
        }
    }

    @Override
    public void clearPath() {
      if (entity.isFlying()) {
            flyingNavigator.clearPath();
        } else {
            groundNavigator.clearPath();
        }
    }

    @Override
    public NodeProcessor getNodeProcessor() {
        if (entity.isFlying()) {
            return flyingNavigator.getNodeProcessor();
        } else {
            return groundNavigator.getNodeProcessor();
        }
    }

    @Override
    public void setCanSwim(boolean canSwim) {
        flyingNavigator.setCanSwim(canSwim);
        groundNavigator.setCanSwim(canSwim);
    }

    @Override
    public boolean getCanSwim() {
        if (entity.isFlying()) {
            return flyingNavigator.getCanSwim();
        } else {
            return groundNavigator.getCanSwim();
        }
    }

    //recompute path
    @Override
    public void func_220970_c(BlockPos pos) {
        if (entity.isFlying()) {
            groundNavigator.clearPath();
            flyingNavigator.func_220970_c(pos);
        } else {
            flyingNavigator.clearPath();
            groundNavigator.func_220970_c(pos);
        }
    }

    @Override
    protected PathFinder getPathFinder(int i) {
        return null;
    }

    @Override
    protected Vec3d getEntityPosition() {
        return null;
    }

    @Override
    protected boolean canNavigate() {
        return false;
    }

    @Override
    protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
        return false;
    }
}
