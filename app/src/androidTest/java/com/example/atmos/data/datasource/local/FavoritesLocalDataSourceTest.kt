package com.example.atmos.data.datasource.local

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.atmos.data.database.AppDatabase
import com.example.atmos.data.database.dao.FavoriteDao
import com.example.atmos.data.database.entity.FavoriteEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.collection.IsIn
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class FavoritesLocalDataSourceTest {

    private lateinit var favoritesLocalDataSource: FavoritesLocalDataSource

    private lateinit var favoriteDao: FavoriteDao

    private lateinit var appDatabase: AppDatabase

    private lateinit var dummyData: List<FavoriteEntity>

    @Before
    fun setup() {
        val application = ApplicationProvider.getApplicationContext<Application>()

        appDatabase = Room.inMemoryDatabaseBuilder(
            application,
            AppDatabase::class.java
        ).build()

        favoriteDao = appDatabase.favoriteDao()
        favoritesLocalDataSource = FavoritesLocalDataSourceImpl(favoriteDao)

        dummyData = listOf(
            FavoriteEntity(id = 1, latitude = 34.02323, longitude = 12.32030),
            FavoriteEntity(id = 2, latitude = 30.02323, longitude = 31.32030),
            FavoriteEntity(id = 3, latitude = 4.02323, longitude = -10.32030),
            FavoriteEntity(id = 4, latitude = -4.02323, longitude = 6.32030),
            FavoriteEntity(id = 5, latitude = -15.02323, longitude = 20.32030),
        )
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun getAllFavorites_retrieveAllFavoriteTasks() = runTest {
        dummyData.forEach { favoritesLocalDataSource.insertFavorite(it) }

        val actualList = favoritesLocalDataSource.getAllFavorites().first()

        assertEquals(dummyData.size, actualList.size)
        assertEquals(dummyData, actualList)
    }

    @Test
    fun insertFavorite_newFavorite_getNewFavoriteList() = runTest {
        val expectedFavorite = dummyData[0]
        favoritesLocalDataSource.insertFavorite(expectedFavorite)

        val actualList = favoritesLocalDataSource.getAllFavorites().first()

        MatcherAssert.assertThat(expectedFavorite, IsIn(actualList))
    }

    @Test
    fun deleteFavorite_favorite_favoriteListWithoutDeletedOne() = runTest {
        val expectedFavorite = dummyData[0]

        favoritesLocalDataSource.insertFavorite(expectedFavorite)
        favoritesLocalDataSource.deleteFavorite(expectedFavorite)

        val actualList = favoritesLocalDataSource.getAllFavorites().first()

        MatcherAssert.assertThat(
            expectedFavorite,
            IsNot(IsIn(actualList))
        )
    }

    @Test
    fun getFavoriteByPoint() = runTest {
        val expectedFavorite = dummyData[0]

        favoritesLocalDataSource.insertFavorite(expectedFavorite)

        val actualFavorite = favoritesLocalDataSource.getFavoriteByPoint(
            expectedFavorite.latitude,
            expectedFavorite.longitude
        )

        assertNotNull(actualFavorite)
        MatcherAssert.assertThat(expectedFavorite, IsEqual(actualFavorite))
    }

}