package com.roaringcatgames.kitten2d.ashley.components;

/**
 * Created by barry on 4/26/16 @ 7:32 PM.
 */
public enum FollowMode {

    /***
     * The follower will stick with the target entity at the offset from the target's position.
     */
    STICKY,
    /***
     * The follower will move toward the target's position, plus the offest, at a given Velocity.
     */
    MOVETO
}
