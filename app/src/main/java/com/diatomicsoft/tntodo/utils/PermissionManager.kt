package com.diatomicsoft.tntodo.utils

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionManager(private val fragment: Fragment) {

    // Interface to handle permission results
    interface PermissionCallback {
        fun onGranted()
        fun onDenied(deniedPermissions: List<String>)
    }

    // Function to request permissions
    fun requestPermissions(permissions: Array<String>, requestCode: Int, callback: PermissionCallback) {
        val deniedPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(fragment.requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }

        if (deniedPermissions.isEmpty()) {
            // All permissions are granted
            callback.onGranted()
        } else {
            // Request the denied permissions
            fragment.requestPermissions(deniedPermissions.toTypedArray(), requestCode)
        }
    }

    // Function to handle the result of permission request
    fun handlePermissionResult(
        permissions: Array<String>,
        grantResults: IntArray,
        callback: PermissionCallback
    ) {
        val deniedPermissions = mutableListOf<String>()

        for (i in permissions.indices) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i])
            }
        }

        if (deniedPermissions.isEmpty()) {
            callback.onGranted()
        } else {
            callback.onDenied(deniedPermissions)
        }
    }

    // Utility to check if all permissions are granted
    fun hasPermissions(permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(fragment.requireContext(), it) == PackageManager.PERMISSION_GRANTED
        }
    }
}
