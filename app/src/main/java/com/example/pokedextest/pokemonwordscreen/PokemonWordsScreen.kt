@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)

package com.example.pokedextest.pokemonwordscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedextest.R
import com.example.pokedextest.data.models.Category
import com.example.pokedextest.data.models.Word


@Composable
fun PokemonWordsScreen(
    viewModel: PokemonWordsScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val wordList by viewModel.wordsMapped.collectAsState()
    var isDataLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(wordList) {
        isDataLoaded = true
        Log.d("Gowno", "PokemonWordsScreen: ${wordList.size} ")
    }

 Surface {
     Column(modifier = Modifier.fillMaxSize()) {
         Text(
             text = "ADD WORD TO DATABASE",
             modifier = Modifier.fillMaxWidth(),
             textAlign = TextAlign.Center,
             fontSize = 30.sp
         )
         Spacer(modifier = Modifier.height(5.dp))
         AddWordToWordList(onWordAdded = {
                 polish, english, spanish, difficulty ->
             if (polish.isNotEmpty() &&
                 english.isNotEmpty() &&
                 spanish.isNotEmpty()) {
                 viewModel.insertWord(polish,english,spanish, difficulty)
             } else
             {
                 Toast.makeText(context, "Fill all the words", Toast.LENGTH_SHORT).show()
             }
         },
             viewModel = viewModel)

         if (isDataLoaded) {
             CategorizedLazyColum(
                 categories = wordList.map {
                     Log.d("gowno2", "PokemonWordsScreen: ${wordList.size}")
                     Category(
                         name = it.key,
                         items = it.value
                     )
                 },
                 viewModel = viewModel,
                 onDelete = {
                     viewModel.deleteWord(it)
                     viewModel.createSortedListOfWords()
                 }

             )
         } else {
             Log.d("gowno4", "PokemonWordsScreen: japeirdole")
             Text(text = "gowno")
             // You can show a loading indicator or placeholder here
             // while waiting for the data to load
         }
     }
 }

}

@Composable
fun AddWordToWordList(
    onWordAdded: (String, String, String, Int) -> Unit,
    viewModel: PokemonWordsScreenViewModel
) {
    var polish by remember {
        mutableStateOf("")
    }

    var english by remember { mutableStateOf("") }
    var spanish by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier

            .padding(16.dp)
    ) {
        TextField(
            value = polish,
            onValueChange = { polish = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true,
            textStyle = TextStyle.Default.copy(color = Color.Black),
            placeholder = { Text("Polish") }
        )

        TextField(
            value = english,
            onValueChange = { english = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true,
            textStyle = TextStyle.Default.copy(color = Color.Black),
            placeholder = { Text("English") }
        )

        TextField(
            value = spanish,
            onValueChange = { spanish = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true,
            textStyle = TextStyle.Default.copy(color = Color.Black),
            placeholder = { Text("Spanish") }
        )

        TextField(
            value = difficulty.toString(),
            onValueChange = {
                difficulty = it.toIntOrNull() ?: 0
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            textStyle = TextStyle.Default.copy(color = Color.Black),
            placeholder = { Text("Difficulty") }
        )


        Button(
            onClick = {
                onWordAdded(polish, english, spanish, difficulty)
                polish = ""
                english = ""
                spanish = ""
                difficulty = 0
                viewModel.createSortedListOfWords()

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add Word")
        }
    }
}

@Composable
fun CategoryHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(
    word: Word,
    modifier: Modifier = Modifier,
    viewModel: PokemonWordsScreenViewModel,
    onDelete: ((Word) -> Unit) ?= null
) {
    val dismissState = androidx.compose.material3.rememberDismissState(confirmValueChange = {
        if (it == DismissValue.DismissedToStart) {
            onDelete?.invoke(word)
        }
        true
    })



    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
                     val color by animateColorAsState(targetValue = Color.Red)
            Row(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .align(CenterVertically),
                horizontalArrangement = Arrangement.End){
                Icon(painter = painterResource(
                    id = R.drawable.baseline_delete_24),
                    contentDescription = "delete icon",
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(35.dp)
                )

            }
        },
        dismissContent = {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = word.polish,
                fontSize = 14.sp,
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)

            )
        }
    })




}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategorizedLazyColum(
    categories: List<Category>,
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: PokemonWordsScreenViewModel,
    onDelete: ((Word) -> Unit) ?= null
) {
    LazyColumn(modifier) {
        categories.forEach { category ->
            stickyHeader {
                CategoryHeader(text = category.name)
            }
            items(items = category.items, key = {it.idWord}) { word ->
                CategoryItem(
                    word = word,
                    viewModel = viewModel,
                    onDelete = onDelete
                )

            }
        }
    }
}

