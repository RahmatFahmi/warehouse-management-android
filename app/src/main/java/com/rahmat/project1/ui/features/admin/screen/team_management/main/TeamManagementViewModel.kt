package com.rahmat.project1.ui.features.admin.screen.team_management.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahmat.project1.R
import com.rahmat.project1.data.usecase.GetStaffStreamUseCase
import com.rahmat.project1.domain.model.WarehouseStaff
import com.rahmat.project1.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamManagementViewModel @Inject constructor(
    private val getStaffStreamUseCase: GetStaffStreamUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit) // Otomatis trigger sekali saat ViewModel pertama kali dibuat
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val staffState: StateFlow<UiState<List<WarehouseStaff>>> = refreshTrigger
        .flatMapLatest {
            flow {
                emitAll(getStaffStreamUseCase())
            }
                .map<List<WarehouseStaff>, UiState<List<WarehouseStaff>>> { list ->
                    if (list.isEmpty()) UiState.Empty else UiState.Success(list)
                }
                .onStart {
                    emit(UiState.Loading)
                }
                .catch { e ->
                    if (e is kotlinx.coroutines.CancellationException) throw e
                    Log.e("TeamManagementViewModel", "Gagal memuat data tim: ${e.message}", e)

                    val msg = e.message ?: ""
                    val userFriendlyMessage = when {
                        e is HttpRequestException ||
                                msg.contains("httprequestexception", ignoreCase = true) ||
                                msg.contains("network", ignoreCase = true) ||
                                msg.contains("timeout", ignoreCase = true) ||
                                msg.contains("host", ignoreCase = true) ||
                                msg.contains("connect", ignoreCase = true) ||
                                msg.contains("unknownhost", ignoreCase = true) ->
                            context.getString(R.string.team_management_error_network)

                        e is RestException ||
                                msg.contains("jwt", ignoreCase = true) ||
                                msg.contains("expired", ignoreCase = true) ||
                                msg.contains("unauthorized", ignoreCase = true) ->
                            context.getString(R.string.team_management_error_session_expired)

                        else ->
                            context.getString(R.string.team_management_error_fetch_stream)
                    }

                    emit(UiState.Error(userFriendlyMessage))
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    fun refresh() {
        viewModelScope.launch {
            refreshTrigger.emit(Unit)
        }
    }
}