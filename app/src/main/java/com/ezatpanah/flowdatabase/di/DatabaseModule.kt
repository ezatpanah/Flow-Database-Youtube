package com.ezatpanah.flowdatabase.di

import android.content.Context
import androidx.room.Room
import com.ezatpanah.flowdatabase.db.ContactsDB
import com.ezatpanah.flowdatabase.db.ContactsEntity
import com.ezatpanah.flowdatabase.utils.Constants.CONTACTS_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, ContactsDB::class.java, CONTACTS_DATABASE
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun provideDao(db:ContactsDB) = db.noteDao()


    @Provides
    fun provideEntity()=ContactsEntity()

}