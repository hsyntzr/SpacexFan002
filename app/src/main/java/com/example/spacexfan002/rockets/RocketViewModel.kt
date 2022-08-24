package com.example.spacexfan002.rockets


import android.app.Application
import androidx.lifecycle.*
import com.example.spacexfan002.data.SpaceXModel
import com.example.spacexfan002.favorite.favdata.FavoriteDatabase
import com.example.spacexfan002.favorite.favdata.FavoriteRepository
import com.example.spacexfan002.favorite.favdata.Favorites
import com.example.spacexfan002.retrofit.RetroInstance
import com.example.spacexfan002.retrofit.RetroService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RocketViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteRepository
    private var firestore: FirebaseFirestore = Firebase.firestore
    val  allListLivedata = MutableLiveData<List<Favorites>>()

    init {
        val favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
        repository = FavoriteRepository(favoriteDao)
    }

    fun makeAPICall() {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService = retroInstance.create(RetroService::class.java)
        val call = retroService.getSpaceXList()
        call.enqueue(object : Callback<List<SpaceXModel>> {
            override fun onResponse(
                call: Call<List<SpaceXModel>>,
                response: Response<List<SpaceXModel>>
            ) {
                addList(response.body() as List<SpaceXModel>)
            }

            override fun onFailure(call: Call<List<SpaceXModel>>, t: Throwable) {

            }
        })
    }

    fun addList(list: List<SpaceXModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addList(list)

            checkStorage()
        }
    }

    fun listenAllList(lifecycle: Lifecycle, scope: LifecycleCoroutineScope) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllList()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .onEach {
                    allListLivedata.postValue(it)
                }
                .launchIn(scope)
        }
    }

    fun updateFavorite(favorites: Favorites) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavorite(favorites)
        }
    }

    private fun checkStorage() {
        firestore.collection("Favorites").addSnapshotListener { value, _ ->
            if (value != null) {
                if (!value.isEmpty) {
                    val documents = value.documents
                    viewModelScope.launch(Dispatchers.IO) {
                        for (document in documents) {
                            val id = document.get("id") as String
                            val favorite = repository.getFavorite(id)
                            favorite.favorite = true
                            repository.updateFavorite(favorite)
                        }
                    }
                }
            }
        }
    }

}