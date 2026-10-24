package com.eric.week5.expenses.ui.screen

import android.R.attr.icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eric.week5.expenses.model.Expense
import com.eric.week5.expenses.viewmodel.ExpenseViewModel
import kotlin.math.round


@Composable
fun expenses (viewModel: ExpenseViewModel = viewModel()) {
    val expenses by viewModel.expenses.collectAsState()
    val totalExpense = expenses.sumOf { it.hargaProduk }
    val errorMessage by viewModel.errorMessageIsZero.collectAsState()

    var namaProduk by rememberSaveable { mutableStateOf("") }
    var hargaProduk by rememberSaveable { mutableStateOf("") }

    Box (
        Modifier.fillMaxSize()
            .background(Color(250,248,254))
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 80.dp)
                .padding(horizontal = 17.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text("Expenses",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
               )

            Text("Total Expense : Rp ${totalExpense}",
                modifier = Modifier.padding(top = 6.dp),
                fontSize = 17.sp,
                fontWeight = FontWeight.Light
            )

            OutlinedTextField(
                value = namaProduk,
                onValueChange = {
                    namaProduk = it
                },
                placeholder = {
                    Text("Expense Name",
                        fontSize = 13.sp,
                        color = Color(151,162,182),
                    )
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(250,248,254)),
                shape = RoundedCornerShape(6.dp),
                minLines = 1,
                maxLines = 2
            )

            Row(
                Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = hargaProduk,
                    onValueChange = {
                        hargaProduk = it
                    },
                    placeholder = {
                        Text("Amount of Expense",
                            fontSize = 13.sp,
                            color = Color(151,162,182),
                        )
                    },
                    modifier = Modifier.fillMaxWidth().weight(3f)
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(250,248,254)),
                    shape = RoundedCornerShape(6.dp)
                )

                FloatingActionButton(
                    onClick = {
                        val parsedAmount = hargaProduk.toIntOrNull()

                        if (parsedAmount != null) {
                            viewModel.addExpense(namaProduk, parsedAmount)
                            namaProduk = ""
                            hargaProduk = ""
                        }
                    },
                    containerColor = Color(230,228,234),
                    modifier = Modifier.padding(    top = 15.dp)
                        .padding(start = 10.dp)
                        .clip(CircleShape)
                        .size(45.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Expense",

                    )
                }

            }
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error
                )
            }
            LazyColumn {
                items(expenses) { expense->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color(220,226,249), shape = RoundedCornerShape(10.dp))
                            .padding(13.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column() {
                            Text("Name : ${expense.namaProduk}",
                                fontWeight = FontWeight.Bold)

                            Text(text = "Rp ${expense.hargaProduk}")
                        }

                        FloatingActionButton(
                            onClick = {
                                viewModel.deleteExpense(expense)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Add Expense",

                                )
                        }
                    }
                }
            }

        }

    }
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun viewexpenses(){
    expenses()
}