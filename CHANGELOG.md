# Change Log
All notable changes to this project will be documented in this file.

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