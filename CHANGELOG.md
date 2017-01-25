# Change Log
All notable changes to this project will be documented in this file.

## [0.4.5]

### Changes

- Adding ```ClickableComponent``` and ```ClickableSystem```
  - Provides a clickable event system for entities for firing and handling actions
- Adding ```IActionResolver``` interface


## [0.4.4]

### Changes

- Implements MOVETO follower mode for the ```FollowerSystem```
- Implements MOVETOSTICKY follower mode for the ```FollowerSystem```
- Allow followers to ignore rotation of target

### Fixes

- Render text to guiCam from World units
  - using proper world to pixel and back translations for ```TextRenderingSystem```.

## [0.4.0]

### Changes

- Adding QueuedIteratingSystem base class.
- Adding base implementation of a Physics System via box2d.
  - adds ```Box2DPhysicsSystem``` and ```Box2DPhysicsDebugSystem```
  - Example usage in ```example/src/com/roaringcatgames/kitten2d/ashley/example/screens/PhsyicsScreen```

## [0.3.0]

### Changes

- Changes made during this time were not properly tracked. Will go back to list them at a later time.

## [0.2.1-SNAPSHOT]
### BREAKING CHANGES
- Modified the IGameProcessor to require ```getPreferenceManager``` implementation that returns and instance of the new ```IPreferenceManager``` interface.
    - An easy implmentation is to just add the following to your IGameProcessor:
    ```Java
        private IPreferenceManager prefManager =
            new K2PreferenceManager("name_of_game_prefs");

        @Override
        public IPreferenceManager getPreferenceManager() {
            return prefManager;
        }
    ```

## [0.2.0-SNAPSHOT]
### BREAKING CHANGES
- Modified RenderingSystem to take in a camera to allow the Screen/Game to determine the camera approach rather than having it embeded into the RenderingSystem.
    - You MUST provide a camera to the RenderingSystem when creating a new one