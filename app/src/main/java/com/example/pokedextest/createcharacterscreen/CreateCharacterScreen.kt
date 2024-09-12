package com.example.pokedextest.createcharacterscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.navigation.NavController
import com.example.pokedextest.R
import com.example.pokedextest.data.models.Characters

@Composable
fun CreateCharacterScreen(
    navController: NavController,
    viewModel: CreateCharacterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val characterClasses = listOf(
        Characters.CharacterClass("Knight", 50, 190, 50),
        Characters.CharacterClass("Rogue", 40, 215, 60)
    )

    val avatars = listOf(
        Characters.Avatar("Avatar 1", R.drawable.player),
        Characters.Avatar("Avatar 2", R.drawable.avatar_three),
        
    )
    var playerName by remember { mutableStateOf("") }
    var selectedCharacterClass by remember { mutableStateOf<Characters.CharacterClass?>(null) }
    var selectedAvatar by remember { mutableStateOf<Characters.Avatar?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Character Class Selection
        
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Choose Your Class", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
        LazyRow {
            items(characterClasses) { characterClass ->
                CharacterClassCard(
                    characterClass = characterClass,
                    isSelected = characterClass == selectedCharacterClass,
                    onCharacterClassSelected = { selectedCharacterClass = characterClass }
                )
            }
        }


        Text(text = "Choose Your Avatar", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            items(avatars) { avatar ->
                AvatarListItem(
                    avatar = avatar,
                    isSelected = avatar == selectedAvatar,
                    onAvatarSelected = { selectedAvatar = avatar }
                )
            }
        }

        // Character Stats
        selectedCharacterClass?.let { characterClass ->
            Text(text = "Character Stats", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
            CharacterStats(characterClass)
        }

        Spacer(modifier = Modifier.weight(1f))
        TextField(
            value = playerName,
            onValueChange = { playerName = it },
            placeholder = { Text("Enter Your Name") },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        )
        // Create Character Button
        Button(
            onClick = {
                if (
                    playerName.isNotEmpty() &&
                    selectedAvatar != null &&
                    selectedCharacterClass != null
                    ) {
                    viewModel.savePlayer(
                        viewModel.createPlayer(
                            playerName,
                            avatar = selectedAvatar!!.imageResId,
                            atk = selectedCharacterClass!!.attack,
                            def = selectedCharacterClass!!.defense,
                            vit = selectedCharacterClass!!.vitality,
                            gold = 10000,
                            exp = 1000,
                            weaponEquipped = R.drawable.add_24px
                        )
                    )
                    viewModel.saveOnBoardingProcess("menu")
                } else {
                    Toast.makeText(context, "Enter all data", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Create Character")
        }
    }
}

@Composable
fun CharacterClassCard(
    characterClass: Characters.CharacterClass,
    isSelected: Boolean,
    onCharacterClassSelected: (Characters.CharacterClass) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onCharacterClassSelected(characterClass) }
            .border(
                width = 2.dp,
                color = if (isSelected) Color.Green else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            ),
        elevation = if (isSelected) 8.dp else 2.dp
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = characterClass.name,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AvatarListItem(
    avatar: Characters.Avatar,
    isSelected: Boolean,
    onAvatarSelected: (Characters.Avatar) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onAvatarSelected(avatar) }
            .border(
                width = 2.dp,
                color = if (isSelected) Color.Green else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            ),
        elevation = if (isSelected) 8.dp else 2.dp
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = avatar.imageResId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun CharacterStats(characterClass: Characters.CharacterClass) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Vitality: ${characterClass.vitality}", fontSize = 18.sp)
        Text(text = "Attack: ${characterClass.attack}", fontSize = 18.sp)
        Text(text = "Defense: ${characterClass.defense}", fontSize = 18.sp)
    }
}