package com.example.myfirstapp.presentation

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentChartBinding
import com.example.myfirstapp.databinding.FragmentCourseBinding
import com.example.myfirstapp.databinding.FragmentMainBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS


class ChartFragment : Fragment() {
    private lateinit var binding : FragmentChartBinding
    private lateinit var binding2 : FragmentMainBinding
    private lateinit var pieChart : PieChart
    lateinit var viewModel : ChartViewModel
    private var entries = mutableListOf<PieEntry>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChartBinding.inflate(inflater, container, false)
        binding2 = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this, ChartViewModelFactory(requireContext())).get(ChartViewModel::class.java)
        pieChart = binding.pieChart
        viewModel.startLiveData.observe(viewLifecycleOwner) {
            entries = it
            drawGraphic(entries)
        }
        binding.tv.text = binding2.textViewDate.text



        setUpPieChart()
        loadDataInPieChart()


    }




    private fun setUpPieChart() {
        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText = "Spending by Category"
        pieChart.setCenterTextSize(24f)
        pieChart.description.isEnabled = false

        val legend = pieChart.legend
        legend.textSize = 15f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)
        legend.isEnabled = true

    }

    private fun loadDataInPieChart() {


        viewModel.sortByTypes()

    }

    private fun drawGraphic(entries : MutableList<PieEntry>){
        val colors = ArrayList<Int>()


        ColorTemplate.MATERIAL_COLORS.forEach {
            colors.add(it)
        }
        val dataSet = PieDataSet(entries, "- Expense Сategories")
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)

        pieChart.data = data
        pieChart.invalidate()

        pieChart.animateY(1400, Easing.EaseInQuad)
    }
}