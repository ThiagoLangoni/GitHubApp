package br.com.fiap.githubapp.api

import br.com.fiap.githubapp.model.Usuario
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("/users/{username}")
    fun pequisar(@Path("username") username: String): Call<Usuario>

}