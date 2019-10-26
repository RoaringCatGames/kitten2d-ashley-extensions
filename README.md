# Ashley ECS libGDX 2D Extensions

A set of Ashley ECS components and systems that are generic, and useful for 2D games built with libGDX.

## Features

- Provides the basic Components needed for all included Systems
- Included Components are going to be useful for many of your game-specific Systems as well
- Supports GWT, just include the ```-sources``` jar in your html project and add the node below to your ```GdxDefinition.gwt.xml``` and ```GdxDefinitionSuperdev.gwt.xml``` files

```xml
<inherits name='AshleyExtensions' />
```

## Included Systems

### Animation System

- Uses libGDX Animation class
- Supports different states using the StateComponent
- Supports switching from Looping to not Looping through the StateComponent
- Example usage:


    //TODO: Provide Example Usage

### Gravity System

- Takes in a Vector2 for world gravity
- Applies gravity to Entities with the VelocityComponent
- Ignores Entities with the KinematicComponent
-Example Usage:


    //TODO: Provide Example Usage

### Movement System

- Updates the TransformComponent.position vector based on the Entities VelocityComponent
- Example Usage:


    //TODO: Provide Example Usage

### Rendering System

- Renders the Entity's TextureComponent's TextureRegion
- Rendering properties are based on the TransformComponent
- Uses the TextureRegion's size to determine origin
- Based off of the Ashley Super Jumper example project
- TODO: Allow for "constrain to Bounds" or "Render Size" component
- Example Usage:


    //TODO: Provide Example Usage

### Bounds System

- Updates an Entity's BoundComponent position based on TransformComponent
- Example Usage:


    //TODO: Provide Example Usage

### Debug System

- Renders Center point of Entity based on Bounds
- Renders the BoundComponent bound for an Entity
- Should be added AFTER the RenderingSystem
- Defaults to Toggling on/off via TAB key
- Key(s) for toggling can be overridden in constructor
- Example Usage:


    //TODO: Provide example usage

### Health Rendering System

- Renders Health Bars for Entitys with HealthComponent
- Requires a BoundsComponent at the moment
- TODO: Add support for a WidthComponet or something else to control size
- Currently changes from GREEN, YELLOW, RED, with shifts at 75%, and 50% health
- TODO: Add support for custom color and shift percentage points
- Example Usage:


    //TODO: Provide Example Usage

### Rotation System

- Rotates entities at their given speed
- Supports a ```targetRotation``` so you can rotate to a specific rotation amount
  - Supports values larger than 360 degrees in targetRotation to allow for scenarios like "rotate 3 times" via the RotationComponent
  - Removes the RotationComponent from the entity once reaching the target Rotation
- Example Usage:


    //TODO: Provide Example Usage


## Example Application

An example application is included under the [/example](example) folder. You can run this example with the gradle command:

```
./gradlew example:run
```
Included in the example are 4 Screens:

### Screen 1

Access by hitting <kbd>1</kbd>.

This screen shows basic rotation, color tweening, text rendering with colors, and a particle emitter.

Hit <kbd>space</kbd> to toggle the particle emissions. 

### Screen 2

Access by hitting <kbd>2</kbd>

This screen shows several position and transform based tweening operations simultaneously.

### Screen 3

Access by hitting <kbd>3</kbd>

This screen shows the bounds system. You can hit <kbd>Tab</kbd> to render the bounds of various items, and the 
paths between their rotation origins. 

### Screen 4

Access by hitting <kbd>4</kbd>

This shows an example of the physics system, and a "player" you can control with <kbd>W</kbd><kbd>A</kbd><kbd>S</kbd><kbd>D</kbd>.
