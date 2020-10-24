package com.example.githubuser.home

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.model.UserListDto
import com.example.githubuser.model.UserListResponse
import com.example.githubuser.service.Service
import com.example.githubuser.service.ServiceClient
import retrofit2.Call
import retrofit2.Response

class HomeViewModel {

    private val _requestUserListState = MutableLiveData<UserListViewState>()
    val requestUserListState: LiveData<UserListViewState>
        get() = _requestUserListState


    fun getUserData(pageParam: Int, pageLimistParam: Int) {
        var serviceClient: Service = ServiceClient.create()

        var call: Call<MutableList<UserListResponse.UserInfo>> = serviceClient.getGitHubUsers(pageParam, pageLimistParam)

        call.enqueue(object: retrofit2.Callback<MutableList<UserListResponse.UserInfo>>{
            override fun onFailure(call: Call<MutableList<UserListResponse.UserInfo>>, t: Throwable) {
                Log.d("ERROR", "Failure in calling the github response")
                _requestUserListState.value = UserListViewStateFailure("Error occurred when trying to call the service.")
                t.printStackTrace()
            }

            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<MutableList<UserListResponse.UserInfo>>
                                    , response: Response<MutableList<UserListResponse.UserInfo>>
            ) {
                if(response.isSuccessful && response.body() != null) {
                    //When response is successful and not empty
                    Log.d("SUCCESS", "SIZE OF RESPONSE IS ".plus(response.body()!!.size))
                    var userDtoList : MutableList<UserListDto.UserItemDto> = UserListDto.create(response)
                    _requestUserListState.value = UserListViewStateSuccess(userDtoList)
//                    progressBar.visibility = View.GONE
//                    parseResult(response)
//                      try{
//                          var jsonArray: JSONArray = JSONArray(response.body())
//                          parseResult(jsonArray)
//                      } catch (e: JSONException) {
//                          e.printStackTrace()
//                      }
                }
            }

        })

    }

    private fun parseResult(response: Response<MutableList<UserListResponse.UserInfo>>){
        var userInfoList: MutableList<UserListResponse.UserInfo>? = response.body()
        for (i in 0 until userInfoList!!.size) {
            val itemObject : UserListResponse.UserInfo = userInfoList[i]
            Log.d("login", itemObject.login)
        }

    }

}