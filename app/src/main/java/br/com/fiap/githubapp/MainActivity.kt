package br.com.fiap.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.fiap.githubapp.api.GithubService
import br.com.fiap.githubapp.model.Usuario
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btBuscar.setOnClickListener {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addNetworkInterceptor(StethoInterceptor())
                        .build())
                .build()

            val service = retrofit.create(GithubService::class.java)

            service.pequisar(inputUsuario.text.toString())
                .enqueue(object : Callback<Usuario> {
                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        Toast.makeText(
                            this@MainActivity, t.message, Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                        if (response.isSuccessful) {

                            val usuario = response.body()

                            tvNomeUsuario.text = usuario?.nome

                            Picasso.get()
                                .load(usuario?.avatarURL)
                                .into(ivUsuario)

                        } else {
                            Toast.makeText(
                                this@MainActivity, "Não foi possivel realizar a operação", Toast.LENGTH_LONG
                            ).show()

                        }


                    }
                })


        }


    }
}
