package com.example.nycschools_compose.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nycschools_compose.R
import com.example.nycschools_compose.model.NYCSATScores
import com.example.nycschools_compose.model.NYCSchool
import com.example.nycschools_compose.ui.theme.NYCSchoolsComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NYCSchoolsComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel:NYCViewModel = viewModel()
                    NavView(viewModel)
                }
            }
        }
    }
}

@Composable
fun NavView(viewModel:NYCViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "schoolList"){
        composable("schoolList"){
            viewModel.getSchools()
            SchoolList(
                viewModel.schoolList,
                ToScores = { school ->
                    viewModel.getScores(school.schoolId)
                    navController.navigate("schoolScores")
                }
            )
        }
        composable("schoolScores"){
            SchoolSATScores(viewModel.satScores)
        }
    }
}

@Composable
fun SchoolList(list: LiveData<List<NYCSchool>>, ToScores: (school:NYCSchool) -> Unit) {
    val currentList = list.observeAsState()
    currentList.value?.let {
        LazyColumn(modifier = Modifier.fillMaxWidth()){
            items(it){
                SchoolCard(school = it, ToScores = ToScores)
            }
        }
    }
}

@Composable
fun SchoolCard(school:NYCSchool, ToScores: (school:NYCSchool) -> Unit){
    val errorString = stringResource(id = R.string.not_listed)
    Card(elevation = 2.dp, modifier = Modifier.padding(bottom = 3.dp)){
        ConstraintLayout(modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp)
            .clickable {
                ToScores(school)
            }){
            val (schoolName, schoolNumber, schoolEmail) = createRefs()
            Text(modifier = Modifier.constrainAs(schoolName){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }, text = school.schoolName, fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = school.email ?: errorString, fontSize = 12.sp, color = Color.Blue, modifier = Modifier.constrainAs(schoolEmail) {
                top.linkTo(schoolName.bottom, margin = 2.dp)
                start.linkTo(schoolName.start)
                end.linkTo(schoolNumber.start)
            })
            Text(text = school.phone ?: errorString, fontSize = 12.sp, modifier = Modifier.constrainAs(schoolNumber){
                top.linkTo(schoolEmail.top)
                start.linkTo(schoolEmail.end, margin = 5.dp)
            })
        }
    }
}

@Composable
fun SchoolSATScores(scores:LiveData<NYCSATScores>){
    val currentScores = scores.observeAsState()
    val errorString = stringResource(id = R.string.not_available)
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (schoolName, numTesters, readingScore, mathScore, writingScore) = createRefs()
        Text(text = currentScores.value?.schoolName ?: stringResource(R.string.no_school), textAlign = TextAlign.Center , fontSize = 24.sp, modifier = Modifier.constrainAs(schoolName){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        })
        Text(text = stringResource(id = R.string.num_test_takers, currentScores.value?.numTestTakers ?: errorString), fontSize = 14.sp, modifier = Modifier.constrainAs(numTesters){
            top.linkTo(schoolName.bottom, margin = 20.dp)
            start.linkTo(parent.start, margin = 10.dp)
        })
        Text(text = stringResource(id = R.string.avg_critical_reading_score, currentScores.value?.avgCriticalReadingScore ?:errorString), fontSize = 14.sp, modifier = Modifier.constrainAs(readingScore){
            top.linkTo(numTesters.bottom, margin = 10.dp)
            start.linkTo(numTesters.start)
        })
        Text(text = stringResource(id = R.string.avg_math_score, currentScores.value?.avgMathScore ?: errorString), fontSize = 14.sp, modifier = Modifier.constrainAs(mathScore){
            top.linkTo(readingScore.bottom, margin = 10.dp)
            start.linkTo(readingScore.start)
        })
        Text(text = stringResource(id = R.string.avg_writing_score, currentScores.value?.avgWritingScore ?: errorString), fontSize = 14.sp, modifier = Modifier.constrainAs(writingScore){
            top.linkTo(mathScore.bottom, margin = 10.dp)
            start.linkTo(mathScore.start)
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NYCSchoolsComposeTheme {
        //val school = NYCSchool("1","TestSchool","school@gmail.com","6789329841")

    }
}