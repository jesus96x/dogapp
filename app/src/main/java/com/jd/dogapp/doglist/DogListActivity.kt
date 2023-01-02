package com.jd.dogapp.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.jd.dogapp.dogdetails.DogDetailsActivity.Companion.DOG_KEY
import com.jd.dogapp.dogdetails.DogDetailsComposeActivity
import com.jd.dogapp.dogdetails.ui.theme.DogappTheme
import com.jd.dogapp.models.Dog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogListActivity : ComponentActivity() {

    //private val viewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //val status = viewModel.status

            DogappTheme() {
                //val dogList = viewModel.dogList
                DogListScreen(
                    { onNavigationIconClick() },
                    ::openDogDetailsActivity,
                    //{ resetApiResponseStatus() }
                )
            }
        }
    }

    /*private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }*/

    private fun openDogDetailsActivity(dog: Dog)
    {
        val intent = Intent(this, DogDetailsComposeActivity::class.java)
        intent.putExtra(DOG_KEY, dog)
        startActivity(intent)
    }

    private fun onNavigationIconClick()
    {
        finish()
    }

}

/*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressBar = binding.progressCircular
        val recycler = binding.dogList
        recycler.layoutManager = GridLayoutManager(this, 3)
        val adapter = DogAdapter()
        adapter.setOnItemClickListener {
            val intent = Intent(this, DogDetailsComposeActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }

        recycler.adapter = adapter

        dogListViewModel.dogList.observe(this){
            dogList -> adapter.submitList(dogList)
        }

        dogListViewModel.status.observe(this) {
            status ->

            when(status)
            {
                is ApiResponseStatus.Error -> {
                    Toast.makeText(this, status.msg, Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
                is ApiResponseStatus.Loading -> progressBar.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> progressBar.visibility = View.GONE
            }

        }

    }*/