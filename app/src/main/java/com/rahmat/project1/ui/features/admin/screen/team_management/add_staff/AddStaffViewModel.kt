package com.rahmat.project1.ui.features.admin.screen.team_management.add_staff

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahmat.project1.R
import com.rahmat.project1.data.usecase.CreateStaffUseCase
import com.rahmat.project1.data.usecase.GetAvailableWarehousesUseCase
import com.rahmat.project1.domain.model.WarehouseHub
import com.rahmat.project1.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddStaffViewModel @Inject constructor(
    private val createStaffUseCase: CreateStaffUseCase,
    private val getAvailableWarehousesUseCase: GetAvailableWarehousesUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _createState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val createState: StateFlow<UiState<Unit>> = _createState.asStateFlow()

    private val _warehouses = MutableStateFlow<List<WarehouseHub>>(emptyList())
    val warehouses = _warehouses.asStateFlow()

    init {
        getWarehouses()
    }

    fun addStaff(fullName: String, email: String, role: String, password: String, wareHouseId: String, isActive: Boolean) {
        viewModelScope.launch {
            _createState.value = UiState.Loading
            try {
                createStaffUseCase(fullName, email, role, password, wareHouseId, isActive)
                _createState.value = UiState.Success(Unit)
            } catch (e: Exception) {
                _createState.value = UiState.Error(e.message ?: context.getString(R.string.add_staff_error_failed))
            }
        }
    }

    fun getWarehouses() {
        viewModelScope.launch {
            try {
                val availableWarehouses = getAvailableWarehousesUseCase()
                _warehouses.value = availableWarehouses
            } catch (e: Exception) {
                _warehouses.value = emptyList()
            }
        }
    }

    fun resetCreateState() {
        _createState.value = UiState.Idle
    }
}