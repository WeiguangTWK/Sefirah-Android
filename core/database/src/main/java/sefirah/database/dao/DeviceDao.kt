package sefirah.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import sefirah.database.model.LocalDeviceEntity
import sefirah.database.model.RemoteDeviceEntity

@Dao
interface DeviceDao {
    @Query("SELECT * FROM RemoteDeviceEntity")
    suspend fun getAllDevices(): List<RemoteDeviceEntity>

    @Query("SELECT * FROM RemoteDeviceEntity")
    fun getAllDevicesFlow(): Flow<List<RemoteDeviceEntity>>

    @Query("SELECT * FROM RemoteDeviceEntity WHERE ipAddress = :ipAddress")
    fun getDeviceFlow(ipAddress: String): Flow<RemoteDeviceEntity?>

    @Query("SELECT * FROM RemoteDeviceEntity WHERE ipAddress = :ipAddress")
    fun getDevice(ipAddress: String): RemoteDeviceEntity?
    
    @Query("SELECT * FROM RemoteDeviceEntity ORDER BY lastConnected DESC LIMIT 1")
    fun getLastConnectedDevice(): RemoteDeviceEntity?

    @Query("SELECT * FROM RemoteDeviceEntity ORDER BY lastConnected DESC LIMIT 1")
    fun getLastConnectedDeviceFlow(): Flow<RemoteDeviceEntity?>

    @Query("SELECT ipAddress FROM RemoteDeviceEntity WHERE deviceName = :deviceName")
    suspend fun getHostAddress(deviceName: String): String?

    @Query("DELETE FROM LocalDeviceEntity WHERE deviceName = :deviceName")
    suspend fun removeDevice(deviceName: String)

    @Update
    suspend fun updateDevice(device: RemoteDeviceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDevice(device: RemoteDeviceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocalDevice(device: LocalDeviceEntity)

    @Update
    suspend fun updateLocalDevice(device: LocalDeviceEntity)

    @Query("SELECT * FROM LocalDeviceEntity LIMIT 1")
    suspend fun getLocalDevice(): LocalDeviceEntity
}