package com.example.myfirstapp.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentChartBinding
import com.example.myfirstapp.databinding.FragmentCourseBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class ChartFragment : Fragment() {
    private lateinit var binding : FragmentChartBinding
    private lateinit var pieChart : PieChart
    lateinit var viewModel : ChartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this, ChartViewModelFactory(requireContext())).get(ChartViewModel::class.java)
        pieChart = binding.pieChart
        setUpPieChart()
        loadDataInPieChart()
    }





    private fun setUpPieChart() {
        pieChart.setDrawHoleEnabled(true)
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText = "Spending by Category"
        pieChart.setCenterTextSize(24f)
        pieChart.description.setEnabled(false)

        val legend = pieChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.setEnabled(true)

    }

    private fun loadDataInPieChart() {
        val purchaseTypes = resources.getStringArray(R.array.types_for_ratio)
        val entries = mutableListOf<PieEntry>()
        entries.add(PieEntry(viewModel.sortByTypeRatio(purchaseTypes[0]), purchaseTypes[0]))
        entries.add(PieEntry(viewModel.sortByTypeRatio(purchaseTypes[1]), purchaseTypes[1]))
        entries.add(PieEntry(viewModel.sortByTypeRatio(purchaseTypes[2]), purchaseTypes[2]))
        entries.add(PieEntry(viewModel.sortByTypeRatio(purchaseTypes[3]), purchaseTypes[3]))
        entries.add(PieEntry(viewModel.sortByTypeRatio(purchaseTypes[4]), purchaseTypes[4]))




        val colors = ArrayList<Int>()



        ColorTemplate.MATERIAL_COLORS.forEach {
            colors.add(it)
        }
        ColorTemplate.VORDIPLOM_COLORS.forEach {
            colors.add(it)
        }

        val dataSet = PieDataSet(entries, "Expense Category")
        dataSet.setColors(colors)

        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)

        pieChart.setData(data)
        pieChart.invalidate()


    }
}