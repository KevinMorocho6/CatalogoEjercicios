package com.example.catalogoejercicios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catalogoejercicios.ui.theme.CatalogoEjerciciosTheme

private val AzulPrincipal = Color(0xFF0D63C7)
private val FondoPantalla = Color(0xFFF6F8FB)
private val FondoImagen = Color(0xFFEFF4FA)
private val BordeTarjeta = Color(0xFFE2E8F0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatalogoEjerciciosTheme {
                CatalogoEjerciciosScreen(
                    ejercicios = DatosEjercicios.listaEjercicios
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoEjerciciosScreen(
    ejercicios: List<Ejercicio>,
    modifier: Modifier = Modifier
) {
    var ejercicioSeleccionado by remember { mutableStateOf<Ejercicio?>(null) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = FondoPantalla,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AzulPrincipal,
                    titleContentColor = Color.White
                ),
                title = {
                    Text(
                        text = "Catálogo de Ejercicios",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 19.sp
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Consulta información de ejercicios físicos",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF334155)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 14.dp,
                    end = 14.dp,
                    top = 2.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(ejercicios, key = { it.nombre }) { ejercicio ->
                    EjercicioItemCard(
                        ejercicio = ejercicio,
                        onVerDetalles = { ejercicioSeleccionado = ejercicio }
                    )
                }
            }
        }

        ejercicioSeleccionado?.let { ejercicio ->
            DetalleEjercicioDialog(
                ejercicio = ejercicio,
                onDismiss = { ejercicioSeleccionado = null }
            )
        }
    }
}

@Composable
fun EjercicioItemCard(
    ejercicio: Ejercicio,
    onVerDetalles: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onVerDetalles),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BordeTarjeta),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(width = 76.dp, height = 72.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(FondoImagen)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = ejercicio.imagen),
                    contentDescription = ejercicio.nombre,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = ejercicio.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ejercicio.grupoMuscular,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF334155)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onVerDetalles,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulPrincipal,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Ver detalles",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun DetalleEjercicioDialog(
    ejercicio: Ejercicio,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = {
            Text(
                text = ejercicio.nombre,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(FondoImagen)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = ejercicio.imagen),
                        contentDescription = ejercicio.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
                Text(
                    text = "Grupo muscular: ${ejercicio.grupoMuscular}",
                    fontWeight = FontWeight.SemiBold,
                    color = AzulPrincipal
                )
                Text(
                    text = "${ejercicio.series} series × ${ejercicio.repeticiones} repeticiones",
                    fontWeight = FontWeight.Medium
                )
                Text(text = ejercicio.descripcion)
                Text(
                    text = "Indicaciones:",
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = ejercicio.indicaciones)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar", color = AzulPrincipal, fontWeight = FontWeight.SemiBold)
            }
        }
    )
}

@Preview(
    showBackground = true,
    widthDp = 411,
    heightDp = 1150
)
@Composable
fun CatalogoEjerciciosTodosPreview() {
    CatalogoEjerciciosTheme {
        CatalogoEjerciciosScreen(
            ejercicios = DatosEjercicios.listaEjercicios
        )
    }
}