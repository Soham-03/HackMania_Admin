package com.example.hackmania_admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.ui.barchart.BarChart
import com.example.hackmania_admin.ui.theme.offWhite
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation

@Composable
fun AnalyticsScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(offWhite)
    )
    {
        Text(
            text = "Analysis",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(12.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            val barChartData = BarChartData(bars = listOf(
                BarChartData.Bar(
                    label = "Night Cream",
                    value = 100f,
                    color = Color.Red
                ),
                BarChartData.Bar(
                    label = "Facial Serum",
                    value = 80f,
                    color = Color.Yellow
                )
            ))
            com.github.tehras.charts.bar.BarChart(
                barChartData = barChartData, modifier = Modifier.fillMaxWidth().height(300.dp),
                animation = simpleChartAnimation(),
                barDrawer = SimpleBarDrawer(),
                xAxisDrawer = SimpleXAxisDrawer(),
                yAxisDrawer = SimpleYAxisDrawer(),
                labelDrawer = SimpleValueDrawer()
            )

        }
    }
}