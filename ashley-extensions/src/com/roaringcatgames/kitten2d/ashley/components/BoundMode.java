package com.roaringcatgames.kitten2d.ashley.components;

/**
 * Created by barry on 4/16/16 @ 3:41 PM.
 */
/**
 * Created by barry on 1/5/16 @ 7:27 PM.
 */
public enum BoundMode {

    /***
     * When the same outer Edge must be on or adjacent to the Container bounds.
     *
     *          ************
     *          *          *
     *          *****      *
     *          *   *      *
     *          * o *      *
     *          *   *      *
     *          ************
     */
    CONTAINED,
    /***
     * When the center of the entity must be within the Container Bounds.
     *
     *          ************
     *          *          *
     *        *****        *
     *        * * *        *
     *        * o *        *
     *        * * *        *
     *        **************
     */
    CENTER,
    /***
     * When the opposite outer Edge must be on or adjacent to the Container bounds.
     *
     *          ************
     *          *          *
     *      *****          *
     *      *   *          *
     *      * o *          *
     *      *   *          *
     *      ****************
     */
    EDGE
}