package lilypuree.dragonvale.dragons.entity.ai.controller;

import lilypuree.dragonvale.dragons.entity.DragonEntityBase;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.MovementController;

public class FlyingWalkingMoveController extends MovementController {

    private DragonEntityBase dragonEntity;
    private MovementController groundController;
    private MovementController flightController;


    public FlyingWalkingMoveController(DragonEntityBase entity, MovementController groundMoveController, MovementController flyingMoveController) {
        super(entity);
        this.dragonEntity = entity;
    }




}
