package com.example.githubuser.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.model.UserListDto
import com.example.githubuser.model.UserListResponse
import com.example.githubuser.service.Service
import com.example.githubuser.service.ServiceClient
import com.example.githubuser.view.ListItemAdapater
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback as Callback1

class HomeActivity : AppCompatActivity() {

    lateinit var nestedScrollView: NestedScrollView;
    lateinit var recyclerview: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var listItemAdapter: ListItemAdapater
    var page: Int = 0
    var pageLimit: Int = 10

    private var homeViewModel: HomeViewModel = HomeViewModel()

    var userList: MutableList<UserListDto.UserItemDto> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpView()
//        getUserData(page, pageLimit)

        //Invoke the api calling method
        homeViewModel.getUserData(page, pageLimit)

        //Listen to live data
        homeViewModel.requestUserListState.observe(this,
            Observer<UserListViewState>() {
                when(it){
                     is UserListViewStateSuccess -> {
                         updateListView(it.userList)
                     }
                    is UserListViewStateFailure -> {

                    }
                }
            })


    }

    private fun setUpView() {
        nestedScrollView = findViewById(R.id.scroll_view)
        recyclerview = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progress_bar)

        progressBar.visibility = View.VISIBLE
        listItemAdapter = ListItemAdapater(userList, this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = listItemAdapter

    }

    private fun updateListView(userListParam: MutableList<UserListDto.UserItemDto>) {
        this.userList = userListParam
        progressBar.visibility = View.GONE
        listItemAdapter.addUpdateListData(this.userList)

    }

    fun getUserData(pageParam: Int, pageLimistParam: Int) {
        var serviceClient: Service = ServiceClient.create()

        var call: Call<MutableList<UserListResponse.UserInfo>> = serviceClient.getGitHubUsers(pageParam, pageLimistParam)

          call.enqueue(object: retrofit2.Callback<MutableList<UserListResponse.UserInfo>>{
              override fun onFailure(call: Call<MutableList<UserListResponse.UserInfo>>, t: Throwable) {
                  Log.d("ERROR", "Failure in calling the github response")
                  t.printStackTrace()
              }

              @RequiresApi(Build.VERSION_CODES.KITKAT)
              override fun onResponse(call: Call<MutableList<UserListResponse.UserInfo>>
                                      , response: Response<MutableList<UserListResponse.UserInfo>>) {
                  if(response.isSuccessful && response.body() != null) {
                      //When response is successful and not empty
                      Log.d("SUCCESS", "SIZE OF RESPONSE IS ".plus(response.body()!!.size))
                      progressBar.visibility = View.GONE
                      parseResult(response)
                  }
              }

          })

    }

//    private fun parseResult(jsonArray: JSONArray) {
//        for (i in 0 until jsonArray.length()) {
//            val itemJsonObject = jsonArray.getJSONObject(i)
//            Log.d("login", itemJsonObject.getString("login"))
//        }
//    }

    private fun parseResult(response: Response<MutableList<UserListResponse.UserInfo>>){
       var userInfoList: MutableList<UserListResponse.UserInfo>? = response.body()
        for (i in 0 until userInfoList!!.size) {
            val itemObject : UserListResponse.UserInfo = userInfoList[i]
            Log.d("login", itemObject.login)
        }

    }


}

sealed class UserListViewState
data class UserListViewStateSuccess(var userList: MutableList<UserListDto.UserItemDto>) : UserListViewState()
data class UserListViewStateFailure(var genericError: String) : UserListViewState()