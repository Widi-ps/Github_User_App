package com.example.githubuserapp3.data.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDatastore(private val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var mInstance: UserDatastore? = null

        fun getInstance(context: Context): UserDatastore =
            mInstance?: synchronized(this) {
                val newInstance = mInstance?: UserDatastore(context).also { mInstance = it }
                newInstance
            }
    }

    private val Context.userPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DatastoreUtil.DATA_STORE_NAME
    )

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        context.userPreferenceDataStore.edit {
            it[DatastoreUtil.DATA_STORE_PREF_THEME_KEY] = isDarkModeActive
        }
    }

    fun getThemeSetting(): Flow<Boolean> =
        context.userPreferenceDataStore.data.map {
            it[DatastoreUtil.DATA_STORE_PREF_THEME_KEY] ?: false
        }
}