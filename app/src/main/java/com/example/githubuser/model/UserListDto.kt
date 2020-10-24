package com.example.githubuser.model

import android.util.Log
import androidx.annotation.Nullable
import retrofit2.Response

open class UserListDto() {

    companion object {

        private fun parseUserInfoData(userInfoItem: UserListResponse.UserInfo): UserItemDto {
            return UserItemDto(userInfoItem.login,
                userInfoItem.id,
                userInfoItem.node_id,
                userInfoItem.avatar_url,
                userInfoItem.gravatar_id,
                userInfoItem.url,
                userInfoItem.html_url,
                userInfoItem.followers_url,
                userInfoItem.following_url,
                userInfoItem.gists_url,
                userInfoItem.starred_url,
                userInfoItem.subscriptions_url,
                userInfoItem.organizations_url,
                userInfoItem.repos_url,
                userInfoItem.events_url,
                userInfoItem.received_events_url,
                userInfoItem.type,
                userInfoItem.site_admin,
                userInfoItem.note)
        }

        @JvmStatic
        fun create(response: Response<MutableList<UserListResponse.UserInfo>>): MutableList<UserItemDto> {
            var userDtoList : MutableList<UserItemDto> = ArrayList()
            var userInfoList: MutableList<UserListResponse.UserInfo>? = response.body()
//            for (i in 0 until userInfoList!!.size-1) {
//                Log.d("login", "Parsing ".plus(userInfoList[i].login))
//                userDtoList[i] = parseUserInfoData(userInfoList[i])
//            }
            for (userInfoItem in userInfoList!!.iterator()) {
                Log.d("login", "Parsing ".plus(userInfoItem.login))
                userDtoList.add(parseUserInfoData(userInfoItem))
            }
            return userDtoList
        }
    }


    open class UserItemDto(@Nullable var login: String = "",
                           @Nullable var id: Int = 0,
                           @Nullable var node_id: String = "",
                           @Nullable var avatar_url: String = "",
                           @Nullable var gravatar_id: String = "",
                           @Nullable var url: String = "",
                           @Nullable var html_url: String = "",
                           @Nullable var followers_url: String = "",
                           @Nullable var following_url: String = "",
                           @Nullable var gists_url: String = "",
                           @Nullable var starred_url: String = "",
                           @Nullable var subscriptions_url: String = "",
                           @Nullable var organizations_url: String = "",
                           @Nullable var repos_url: String = "",
                           @Nullable var events_url: String = "",
                           @Nullable var received_events_url: String = "",
                           @Nullable var type: String = "",
                           @Nullable var site_admin: Boolean = false,
                           @Nullable var note: String?) {

    }

}