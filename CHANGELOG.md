# Change Log
All notable changes to this project will be documented in this file.

## [0.2.0-SNAPSHOT]
### BREAKING CHANGES
- Modified RenderingSystem to take in a camera to allow the Screen/Game to determine the camera approach rather than having it embeded into the RenderingSystem.
    - You MUST provide a camera to the RenderingSystem when creating a new one