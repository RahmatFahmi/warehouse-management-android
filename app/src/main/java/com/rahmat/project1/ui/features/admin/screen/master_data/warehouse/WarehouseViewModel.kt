package com.rahmat.project1.ui.features.admin.screen.master_data.warehouse

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahmat.project1.data.usecase.CreateWarehouseUseCase
import com.rahmat.project1.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WarehouseViewModel @Inject constructor(
    private val createWarehouseUseCase: CreateWarehouseUseCase
) : ViewModel() {

    private val _createState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val createState: StateFlow<UiState<Unit>> = _createState.asStateFlow()

    fun createWarehouse(name: String, code: String, city: String, address: String, maxCapacity: Int) {
        if (name.isBlank() || code.isBlank() || city.isBlank() || address.isBlank()) {
            _createState.value = UiState.Error("Semua field harus diisi")
            return
        }

        viewModelScope.launch {
            _createState.value = UiState.Loading
            try {
                createWarehouseUseCase(name, code, city, address, maxCapacity)
                _createState.value = UiState.Success(Unit)
            } catch (e: Throwable) {
                if (e is kotlinx.coroutines.CancellationException) throw e
                
                Log.e("WarehouseViewModel", "Gagal membuat gudang: ${e.message}", e)
                
                val msg = e.message ?: ""
                val errorMsg = when {
                    msg.contains("unique_violation", ignoreCase = true) ||
                    msg.contains("23505", ignoreCase = true) ||
                    msg.contains("already exists", ignoreCase = true) ||
                    msg.contains("duplicate key", ignoreCase = true) ->
                        "Kode gudang sudah digunakan"

                    msg.contains("row-level security", ignoreCase = true) ||
                    msg.contains("42501", ignoreCase = true) ||
                    msg.contains("permission denied", ignoreCase = true) ->
                        "Akses ditolak oleh RLS (Row Level Security). Silakan periksa kebijakan tabel Supabase."

                    msg.contains("network", ignoreCase = true) ||
                    msg.contains("timeout", ignoreCase = true) ||
                    msg.contains("connect", ignoreCase = true) ||
                    msg.contains("unknownhost", ignoreCase = true) ->
                        "Koneksi internet bermasalah. Gagal menambah data."

                    msg.isNotBlank() ->
                        "Gagal menyimpan: $msg"

                    else ->
                        "Gagal menyimpan data ke server. Silakan coba lagi."
                }
                _createState.value = UiState.Error(errorMsg)
            }
        }
    }

    fun resetCreateState() {
        _createState.value = UiState.Idle
    }
}
