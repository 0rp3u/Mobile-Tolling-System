///*
// * Copyright 2017 Google Inc. All Rights Reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package pt.isel.ps.g30.tollingsystem.geofencing
//
//import android.content.Context
//import android.content.res.Resources
//
//import com.google.android.gms.common.api.ApiException
//import com.google.android.gms.location.GeofenceStatusCodes
//import pt.isel.ps.g30.tollingsystem.R
//
///**
// * Geofence error codes mapped to error messages.
// */
//internal object GeofenceErrorMessages {
//
//    /**
//     * Returns the error string for a geofencing exception.
//     */
//    fun getErrorString(context: Context, e: Exception): String {
//        return if (e is ApiException) {
//            getErrorString(context, (e as ApiException).getStatusCode())
//        } else {
//            context.resources.getString(R.string.unknown_geofence_error)
//        }
//    }
//
//    /**
//     * Returns the error string for a geofencing error code.
//     */
//    fun getErrorString(context: Context, errorCode: Int): String {
//        val mResources = context.resources
//        when (errorCode) {
//            GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE -> return mResources.getString(R.string.geofence_not_available)
//            GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES -> return mResources.getString(R.string.geofence_too_many_geofences)
//            GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS -> return mResources.getString(R.string.geofence_too_many_pending_intents)
//            else -> return mResources.getString(R.string.unknown_geofence_error)
//        }
//    }
//}
/**
 * Prevents instantiation.
 */
