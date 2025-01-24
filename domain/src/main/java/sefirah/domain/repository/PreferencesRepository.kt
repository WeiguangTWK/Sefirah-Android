package sefirah.domain.repository

import kotlinx.coroutines.flow.Flow
import sefirah.domain.model.PreferencesSettings

interface PreferencesRepository {
    fun preferenceSettings(): Flow<PreferencesSettings>

    suspend fun readAppEntry(): Flow<Boolean>
    suspend fun saveAppEntry()

    suspend fun saveSynStatus(syncStatus: Boolean)
    fun readSyncStatus(): Flow<Boolean>

    suspend fun saveLastConnected(hostAddress: String)
    fun readLastConnected(): Flow<String?>

    suspend fun saveAutoDiscoverySettings(discoverySettings: Boolean)

    suspend fun updateStorageLocation(uri: String)
    suspend fun getStorageLocation(): Flow<String>


    suspend fun savePermissionRequested(permission: String)
    fun hasRequestedPermission(permission: String): Flow<Boolean>

    suspend fun saveMediaSessionSettings(showMediaSession: Boolean)
    fun readMediaSessionSettings(): Flow<Boolean>

    suspend fun saveClipboardSyncSettings(clipboardSync: Boolean)
    fun readClipboardSyncSettings(): Flow<Boolean>

    suspend fun saveImageClipboardSettings(copyImagesToClipboard: Boolean)
    fun readImageClipboardSettings(): Flow<Boolean>

//    suspend fun saveReadSensitiveNotificationsSettings(readSensitiveNotifications: Boolean)
//    fun readReadSensitiveNotificationsSettings(): Flow<Boolean>

    suspend fun saveNotificationSyncSettings(notificationSync: Boolean)
    fun readNotificationSyncSettings(): Flow<Boolean>
}

