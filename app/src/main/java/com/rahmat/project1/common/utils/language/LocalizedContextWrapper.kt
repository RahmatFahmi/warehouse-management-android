package com.rahmat.project1.common.utils.language

import android.content.Context
import android.content.ContextWrapper
import android.content.res.AssetManager
import android.content.res.Resources

/**
 * ContextWrapper kustom untuk mempertahankan rujukan ActivityContext asli (dibutuhkan oleh Hilt / HiltViewModelFactory)
 * sambil memberikan Resources yang telah di-lokalisasi sesuai bahasa terpilih.
 */
class LocalizedContextWrapper(
    base: Context,
    private val localizedContext: Context
) : ContextWrapper(base) {

    override fun getResources(): Resources {
        return localizedContext.resources
    }

    override fun getAssets(): AssetManager {
        return localizedContext.assets
    }
}
