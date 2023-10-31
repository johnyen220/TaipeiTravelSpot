package com.johnyen.taipeitravelspot.di

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(
    SingletonComponent::class,
    ActivityRetainedComponent::class,
    ActivityComponent::class,
    FragmentComponent::class
)
@Module
class AndroidModule {
    @Provides
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }
}