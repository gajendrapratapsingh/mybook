package com.info.mybook.db

// DataStoreManager.kt
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
//
//@Singleton
//class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {
//
//    private val dataStore: DataStore<UserProto.User> = context.dataStore(
//        fileName = "user_data_store.pb",
//        serializer = UserProto.User.ADAPTER
//    )
//
//    suspend fun saveUser(user: UserProto.User) {
//        dataStore.updateData { currentUser ->
//            currentUser.toBuilder()
//                .mergeFrom(user)
//                .build()
//        }
//    }
//
//    fun getUser(): Flow<UserProto.User> {
//        return dataStore.data.map { it }
//    }
//
//    suspend fun clearUser() {
//        dataStore.updateData { it.toBuilder().clear().build() }
//    }
//}
