package space.novium.world.entity;

public class Player extends Entity {
    public Player(){
        super(
                new Entity.Properties()
                        .setHitBox(0.1f, 0.0f, 0.6f, 0.8f)
                        .setSpeed(0.2f)
        );
    }
}
