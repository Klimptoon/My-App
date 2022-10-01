import android.content.Context
import com.example.myfirstapp.data.PurchaseDao
import com.example.myfirstapp.data.PurchaseDatabase
import com.example.myfirstapp.data.PurchaseRepository
import com.example.myfirstapp.presentation.Purchase

class PurchaseUsecase(context: Context) {



    private val dao = PurchaseDatabase.getInstance(context).getPurchaseDao()
    private val purchaseRepository = PurchaseRepository(dao)

    fun getData(type : String)  : List<Purchase>{
        return if(type == "Все") {
            getStartData()
        } else {
            val purchaseList = purchaseRepository.getData()
            val listOfType = mutableListOf<Purchase>()
            for (purchase in purchaseList) {
                if (purchase.type == type) {
                    listOfType.add(purchase)
                }
            }
            listOfType
        }
    }

    fun getStartData() : List<Purchase> {
        return purchaseRepository.getData()
    }

    fun addPurchase(purchase: Purchase) {
        purchaseRepository.addPurchase(purchase)
    }



}