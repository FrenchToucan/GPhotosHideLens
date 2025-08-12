# GPhotosHideLens

Hide the Lens button in Google Photos and evenly space the Share, Edit, and Trash buttons using LSPosed/Xposed.  
Supports Magisk and KernelSU via LSPosed.

## How it Works

- Hooks the Google Photos app when a photo is viewed.
- Hides the "Lens" button.
- Evenly spaces the remaining action buttons.

## Installation

1. Install [LSPosed](https://github.com/LSPosed/LSPosed) via Magisk or KernelSU.
2. Build and install this APK.
3. Enable the module for Google Photos in LSPosed.
4. Restart Google Photos.

## Updating for New Google Photos Versions

You may need to update the class and resource IDs in `PhotosHook.kt` after each app update.  
Use APKTool/JADX to inspect the APK and logcat to debug.

## License

MIT
