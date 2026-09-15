package com.example

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.entity.ProductEntity
import com.example.ui.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Kelola", appName)
  }

  @Test
  fun `cart decrease, cancel, and clear functionality works correctly`() {
    val application = ApplicationProvider.getApplicationContext<Application>()
    val viewModel = MainViewModel(application)
    org.robolectric.shadows.ShadowLooper.idleMainLooper()

    val dummyProduct = ProductEntity(
      id = 101L,
      name = "Teh Manis",
      categoryId = 1L,
      sellingPrice = 5000L,
      costPrice = 2000L,
      stock = 10,
      minimumStock = 2,
      unit = "gelas"
    )

    // 1. Add product to cart twice
    viewModel.addToCart(dummyProduct)
    viewModel.addToCart(dummyProduct)
    org.robolectric.shadows.ShadowLooper.idleMainLooper()
    assertEquals(2, viewModel.cart.value.items.firstOrNull { it.product.id == 101L }?.quantity ?: 0)

    // 2. Reduce purchased quantity (ngurangin stok yang dibeli)
    viewModel.decreaseCart(dummyProduct)
    org.robolectric.shadows.ShadowLooper.idleMainLooper()
    assertEquals(1, viewModel.cart.value.items.firstOrNull { it.product.id == 101L }?.quantity ?: 0)

    // 3. Cancel/remove purchased product completely (membatalkan stok yang dibeli)
    viewModel.removeFromCart(dummyProduct.id)
    org.robolectric.shadows.ShadowLooper.idleMainLooper()
    assertTrue(viewModel.cart.value.items.none { it.product.id == 101L })

    // 4. Test cancel when decreasing to 0
    viewModel.addToCart(dummyProduct)
    viewModel.decreaseCart(dummyProduct)
    org.robolectric.shadows.ShadowLooper.idleMainLooper()
    assertTrue(viewModel.cart.value.items.none { it.product.id == 101L })

    // 5. Test clear entire cart (tidak jadi beli)
    viewModel.addToCart(dummyProduct)
    viewModel.clearCart()
    org.robolectric.shadows.ShadowLooper.idleMainLooper()
    assertTrue(viewModel.cart.value.items.isEmpty())
  }
}
