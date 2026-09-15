package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.PosDao
import com.example.data.local.entity.CategoryEntity
import com.example.data.local.entity.ChangeRecordEntity
import com.example.data.local.entity.DebtEntity
import com.example.data.local.entity.ExpenseEntity
import com.example.data.local.entity.IncomeEntity
import com.example.data.local.entity.LossRecordEntity
import com.example.data.local.entity.NoteEntity
import com.example.data.local.entity.ProductEntity
import com.example.data.local.entity.PromoEntity
import com.example.data.local.entity.StockMovementEntity
import com.example.data.local.entity.TransactionEntity
import com.example.data.local.entity.TransactionItemEntity
import androidx.room.migration.Migration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        CategoryEntity::class,
        ProductEntity::class,
        TransactionEntity::class,
        TransactionItemEntity::class,
        ExpenseEntity::class,
        IncomeEntity::class,
        StockMovementEntity::class,
        DebtEntity::class,
        ChangeRecordEntity::class,
        LossRecordEntity::class,
        PromoEntity::class,
        NoteEntity::class
    ],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun posDao(): PosDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `promos` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `name` TEXT NOT NULL,
                        `isActive` INTEGER NOT NULL,
                        `discountType` TEXT NOT NULL,
                        `discountValue` INTEGER NOT NULL,
                        `maxUsage` INTEGER NOT NULL,
                        `requiredItemsJson` TEXT NOT NULL,
                        `createdAt` INTEGER NOT NULL,
                        `updatedAt` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_promos_isActive` ON `promos` (`isActive`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_promos_createdAt` ON `promos` (`createdAt`)")
                db.execSQL("ALTER TABLE `transactions` ADD COLUMN `promoId` INTEGER DEFAULT NULL")
                db.execSQL("ALTER TABLE `transactions` ADD COLUMN `promoName` TEXT DEFAULT NULL")
                db.execSQL("ALTER TABLE `transactions` ADD COLUMN `promoDiscount` INTEGER NOT NULL DEFAULT 0")
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE `promos` ADD COLUMN `freeProductId` INTEGER DEFAULT NULL")
                db.execSQL("ALTER TABLE `promos` ADD COLUMN `freeQuantity` INTEGER NOT NULL DEFAULT 1")
            }
        }

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `notes` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `title` TEXT NOT NULL,
                        `content` TEXT NOT NULL,
                        `category` TEXT NOT NULL,
                        `relatedEntityId` INTEGER DEFAULT NULL,
                        `createdAt` INTEGER NOT NULL,
                        `updatedAt` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_notes_createdAt` ON `notes` (`createdAt`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_notes_category` ON `notes` (`category`)")
            }
        }

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kelola_pos.db"
                )
                    .addMigrations(MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialData(database.posDao())
                    }
                }
            }
        }

        suspend fun populateInitialData(dao: PosDao) {
            // Seed Categories
            val catMakanan = dao.insertCategory(CategoryEntity(name = "Makanan"))
            val catMinuman = dao.insertCategory(CategoryEntity(name = "Minuman"))
            val catSnack = dao.insertCategory(CategoryEntity(name = "Snack"))
            val catLainnya = dao.insertCategory(CategoryEntity(name = "Lainnya"))

            // Seed Sample Products as requested in product requirements
            val products = listOf(
                ProductEntity(
                    name = "Es Teh",
                    categoryId = catMinuman,
                    costPrice = 2000L,
                    sellingPrice = 5000L,
                    stock = 20,
                    minimumStock = 5,
                    unit = "gelas"
                ),
                ProductEntity(
                    name = "Roti Cokelat",
                    categoryId = catSnack,
                    costPrice = 3000L,
                    sellingPrice = 5000L,
                    stock = 15,
                    minimumStock = 5,
                    unit = "bungkus"
                ),
                ProductEntity(
                    name = "Mie Goreng",
                    categoryId = catMakanan,
                    costPrice = 6000L,
                    sellingPrice = 10000L,
                    stock = 10,
                    minimumStock = 3,
                    unit = "porsi"
                ),
                ProductEntity(
                    name = "Air Mineral",
                    categoryId = catMinuman,
                    costPrice = 2500L,
                    sellingPrice = 4000L,
                    stock = 20,
                    minimumStock = 5,
                    unit = "botol"
                ),
                ProductEntity(
                    name = "Kripik Pedas",
                    categoryId = catSnack,
                    costPrice = 3500L,
                    sellingPrice = 6000L,
                    stock = 12,
                    minimumStock = 4,
                    unit = "bungkus"
                )
            )
            dao.insertProducts(products)
        }
    }
}
