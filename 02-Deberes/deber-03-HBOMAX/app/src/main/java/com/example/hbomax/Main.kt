package com.example.hbomax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hbomax.adapter.ChildAdapter
import com.example.hbomax.adapter.ParentAdapter
import com.example.hbomax.databinding.ActivityMainBinding
import com.example.hbomax.utils.SampleData
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class Main : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val list = mutableListOf<CarouselItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar el ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar componentes
        initComponents()
        //initUI()
    }

    private fun initComponents() {
        binding.carousel.apply {
            list.add(CarouselItem("https://tododezombie.com/wp-content/uploads/i-am-legend-750x1080."))
            list.add(CarouselItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTz9W-ygfoyi_JKwGBj3tJiCZG3DMSSsJBKtA&s"))
            list.add(CarouselItem("https://i.pinimg.com/736x/5b/b8/b3/5bb8b3155711b590c18a61ca9df3ca0e.jpg"))
            list.add(CarouselItem("https://www.mubis.es/media/users/7286/261490/poster-hbo-max-de-la-segunda-temporada-de-doom-patrol-original.jpg"))
            list.add(CarouselItem("https://moviehole.net/img/Wonder-Woman-1984-Character-Poster.jpg"))
            list.add(CarouselItem("https://lh3.googleusercontent.com/proxy/yB3zzVor257n91wMLxNmCeEmcF66ZI7i-ad__WtzvbluuPslGVQOdmODSin2pMUqJef1bh74nIE5wskFyIcXrvSgwkw4aKT08EUa72rHiqCiF60cS_L7kp_MklBX4RglePm9eA"))
            list.add(CarouselItem("https://elcomercio.pe/resizer/94HTd3FmDayylM-URfvDIgCtxAY=/980x0/smart/filters:format(jpeg):quality(75)/cloudfront-us-east-1.images.arcpublishing.com/elcomercio/N2GKTUUFFJFMXGTYM4EJU5TIOE.jpg"))
            addData(list)
        }

        // Configurar el RecyclerView
        binding.rvParent.adapter = ParentAdapter(SampleData.collections)
    }
}

