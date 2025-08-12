package com.frenchToucan.gphotoshidelens

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class PhotosHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.google.android.apps.photos") return

        // TODO: Find the actual class name that contains the bottom action row when viewing a photo.
        val className = "com.google.android.apps.photos.some.ui.ClassWithLensButton"

        XposedHelpers.findAndHookMethod(
            className,
            lpparam.classLoader,
            "onCreate", Bundle::class.java,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val activity = param.thisObject as? Activity ?: return

                    // TODO: Replace with the actual ID of the action button container
                    val containerId = activity.resources.getIdentifier("action_buttons_container", "id", "com.google.android.apps.photos")
                    val root = activity.findViewById<ViewGroup>(containerId)
                    root?.let {
                        // TODO: Replace with the actual ID of the Lens button
                        val lensButtonId = activity.resources.getIdentifier("lens_button", "id", "com.google.android.apps.photos")
                        val lensButton = it.findViewById<View>(lensButtonId)
                        lensButton?.visibility = View.GONE

                        // TODO: Replace with the actual IDs for share, edit, and trash
                        val shareId = activity.resources.getIdentifier("share_button", "id", "com.google.android.apps.photos")
                        val editId = activity.resources.getIdentifier("edit_button", "id", "com.google.android.apps.photos")
                        val trashId = activity.resources.getIdentifier("trash_button", "id", "com.google.android.apps.photos")
                        val buttons = listOf(
                            it.findViewById<View>(shareId),
                            it.findViewById<View>(editId),
                            it.findViewById<View>(trashId)
                        ).filterNotNull()

                        // Evenly space the three buttons (works if container is a LinearLayout)
                        if (it is LinearLayout) {
                            it.orientation = LinearLayout.HORIZONTAL
                            it.removeAllViews()
                            buttons.forEach { btn ->
                                val params = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
                                it.addView(btn, params)
                            }
                        }
                    }
                }
            }
        )
        Log.i("GPhotosHideLens", "Hooked Google Photos onCreate for hiding Lens button")
    }
}