////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package net.minecraft.pathfinding;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Sets;
//import java.util.Comparator;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//import javax.annotation.Nullable;
//import net.minecraft.entity.MobEntity;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.Region;
//
//public class TempPathFinder {
//    private final PathHeap path = new PathHeap();
//    private final Set<PathPoint> closedSet = Sets.newHashSet();
//    private final PathPoint[] pathOptions = new PathPoint[32];
//    private final int range;
//    private final NodeProcessor nodeProcessor;
//
//    public TempPathFinder(NodeProcessor nodeProcessor, int rangeIn) {
//        this.nodeProcessor = nodeProcessor;
//        this.range = rangeIn;
//    }
//
//    @Nullable
//    public Path getPath(Region region, MobEntity entity, Set<BlockPos> blocks, float followRange, int maxPathPointDistance, float rangeMultiplier) {
//        this.path.clearPath();
//        this.nodeProcessor.func_225578_a_(region, entity);
//        PathPoint startPoint = this.nodeProcessor.getStart();
//        Map<FlaggedPathPoint, BlockPos> pathPointMap = (Map)blocks.stream().collect(Collectors.toMap((blockPos) -> {
//            return this.nodeProcessor.func_224768_a((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
//        }, Function.identity()));
//        Path lvt_9_1_ = this.getPath(startPoint, pathPointMap, followRange, maxPathPointDistance, rangeMultiplier);
//        this.nodeProcessor.postProcess();
//        return lvt_9_1_;
//    }
//
//    @Nullable
//    private Path getPath(PathPoint startPoint, Map<FlaggedPathPoint, BlockPos> pathPointMap, float followRange, int maxPathPointDistance, float rangeMultipler) {
//        Set<FlaggedPathPoint> pathPointSet = pathPointMap.keySet();
//        startPoint.totalPathDistance = 0.0F;
//        startPoint.distanceToNext = this.func_224776_a(startPoint, pathPointSet);
//        startPoint.distanceToTarget = startPoint.distanceToNext;
//        this.path.clearPath();
//        this.closedSet.clear();
//        this.path.addPoint(startPoint);
//        int temp = 0;
//        int maxRange = (int)((float)this.range * rangeMultipler);
//
//        while(!this.path.isPathEmpty()) {
//            ++temp;
//            if (temp >= maxRange) {
//                break;
//            }
//
//            PathPoint nextPoint = this.path.dequeue();
//            nextPoint.visited = true;
//            pathPointSet.stream().filter((pathPoint) -> {
//                //manhattan distance
//                return nextPoint.func_224757_c(pathPoint) <= (float)maxPathPointDistance;
//            }).forEach(FlaggedPathPoint::func_224764_e);
//            if (pathPointSet.stream().anyMatch(FlaggedPathPoint::func_224762_f)) {
//                break;
//            }
//
//            if (nextPoint.distanceTo(startPoint) < followRange) {
//                int lvt_10_1_ = this.nodeProcessor.func_222859_a(this.pathOptions, nextPoint);
//
//                for(int i = 0; i < lvt_10_1_; ++i) {
//                    PathPoint pathOption = this.pathOptions[i];
//                    float distanceToOption = nextPoint.distanceTo(pathOption);
//                    pathOption.field_222861_j = nextPoint.field_222861_j + distanceToOption;
//                    float totalPathCost = nextPoint.totalPathDistance + distanceToOption + pathOption.costMalus;
//                    if (pathOption.field_222861_j < followRange && (!pathOption.isAssigned() || totalPathCost < pathOption.totalPathDistance)) {
//                        pathOption.previous = nextPoint;
//                        pathOption.totalPathDistance = totalPathCost;
//                        pathOption.distanceToNext = this.func_224776_a(pathOption, pathPointSet) * 1.5F;
//                        if (pathOption.isAssigned()) {
//                            this.path.changeDistance(pathOption, pathOption.totalPathDistance + pathOption.distanceToNext);
//                        } else {
//                            pathOption.distanceToTarget = pathOption.totalPathDistance + pathOption.distanceToNext;
//                            this.path.addPoint(pathOption);
//                        }
//                    }
//                }
//            }
//        }
//
//        Stream lvt_9_3_;
//        if (pathPointSet.stream().anyMatch(FlaggedPathPoint::func_224762_f)) {
//            lvt_9_3_ = pathPointSet.stream().filter(FlaggedPathPoint::func_224762_f).map((p_224778_2_) -> {
//                return this.func_224780_a(p_224778_2_.func_224763_d(), (BlockPos)pathPointMap.get(p_224778_2_), true);
//            }).sorted(Comparator.comparingInt(Path::getCurrentPathLength));
//        } else {
//            lvt_9_3_ = pathPointSet.stream().map((p_224777_2_) -> {
//                return this.func_224780_a(p_224777_2_.func_224763_d(), (BlockPos)pathPointMap.get(p_224777_2_), false);
//            }).sorted(Comparator.comparingDouble(Path::func_224769_l).thenComparingInt(Path::getCurrentPathLength));
//        }
//
//        Optional<Path> lvt_10_2_ = lvt_9_3_.findFirst();
//        if (!lvt_10_2_.isPresent()) {
//            return null;
//        } else {
//            Path lvt_11_2_ = (Path)lvt_10_2_.get();
//            return lvt_11_2_;
//        }
//    }
//
//    private float func_224776_a(PathPoint pathPoint, Set<FlaggedPathPoint> flaggedPathPoints) {
//        float lvt_3_1_ = 3.4028235E38F;
//
//        float distanceToNextFlag;
//        for(Iterator iterator = flaggedPathPoints.iterator(); iterator.hasNext(); lvt_3_1_ = Math.min(distanceToNextFlag, lvt_3_1_)) {
//            FlaggedPathPoint nextFlag = (FlaggedPathPoint)iterator.next();
//            distanceToNextFlag = pathPoint.distanceTo(nextFlag);
//            nextFlag.func_224761_a(distanceToNextFlag, pathPoint);
//        }
//
//        return lvt_3_1_;
//    }
//
//    private Path func_224780_a(PathPoint p_224780_1_, BlockPos p_224780_2_, boolean p_224780_3_) {
//        List<PathPoint> pathPointList = Lists.newArrayList();
//        PathPoint lvt_5_1_ = p_224780_1_;
//        pathPointList.add(0, p_224780_1_);
//
//        while(lvt_5_1_.previous != null) {
//            lvt_5_1_ = lvt_5_1_.previous;
//            pathPointList.add(0, lvt_5_1_);
//        }
//
//        return new Path(pathPointList, p_224780_2_, p_224780_3_);
//    }
//}
