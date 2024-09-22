import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen() {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculator") }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Display the result
                Text(
                    text = result,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    color = Color.Black,
                    fontSize = 32.sp
                )

                // Text field for the first number
                TextField(
                    value = number1,
                    onValueChange = { number1 = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )

                // Text field for the second number
                TextField(
                    value = number2,
                    onValueChange = { number2 = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )

                // Grid of buttons for operators and equal sign
                GridButton(
                    buttonLabels = listOf(
                        "+", "-", "*", "/",
                        "="
                    ),
                    onButtonClick = { label ->
                        when (label) {
                            "=" -> {
                                result = evaluateExpression(number1, number2, operator)
                            }
                            "+" -> operator = "+"
                            "-" -> operator = "-"
                            "*" -> operator = "*"
                            "/" -> operator = "/"
                            else -> {
                                // Handle any other button clicks if needed
                            }
                        }
                    }
                )
            }
        }
    )
}

@Composable
fun GridButton(buttonLabels: List<String>, onButtonClick: (String) -> Unit) {
    val columns = 4
    val rows = (buttonLabels.size + columns - 1) / columns

    Column {
        for (row in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (col in 0 until columns) {
                    val index = row * columns + col
                    if (index < buttonLabels.size) {
                        Button(
                            onClick = { onButtonClick(buttonLabels[index]) },
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                        ) {
                            Text(text = buttonLabels[index], fontSize = 24.sp)
                        }
                    }
                }
            }
        }
    }
}

fun evaluateExpression(number1: String, number2: String, operator: String?): String {
    return try {
        val num1 = number1.toDouble()
        val num2 = number2.toDouble()
        val result = when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN
            else -> Double.NaN
        }
        if (result.isNaN()) "Error" else result.toString()
    } catch (e: Exception) {
        "Error"
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen()
}
