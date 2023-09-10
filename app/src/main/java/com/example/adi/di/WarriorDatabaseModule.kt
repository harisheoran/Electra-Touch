package com.example.adi.di

import android.content.Context
import com.example.adi.database.FavouriteDao
import com.example.adi.database.WarriorDao
import com.example.adi.database.WarriorDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WarriorDatabaseModule {

    // Provides Warrior Database
    @Singleton
    @Provides
    fun provideWarriorDatabase(@ApplicationContext context: Context): WarriorDataBase {
        return WarriorDataBase.getDatabase(context)
    }


    // Provides Warrior Dao(which is used in caching of home screen)
    @Singleton
    @Provides
    fun provideWarriorDao(dataBase: WarriorDataBase): WarriorDao {
        return dataBase.warriorDao()
    }

    @Singleton
    @Provides
    fun provideFavouriteDao(dataBase: WarriorDataBase): FavouriteDao {
        return dataBase.favouriteDao()
    }
}