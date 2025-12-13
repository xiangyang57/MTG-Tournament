package com.example.mtgtourney.ui.common

import androidx.annotation.DrawableRes
import com.example.mtgtourney.R

object CommanderRes{

    private val commanderRes = hashMapOf<String, Int>()

    init {
        commanderRes.put("Captain N'ghathrod", R.drawable.nghathrod)
        commanderRes.put("Bello, Bard of the Brambles", R.drawable.bello)
        commanderRes.put("Y'shtola, Night's Blessed", R.drawable.yshtola)
        commanderRes.put("Terra, Herald of Hope", R.drawable.terra)
        commanderRes.put("Bruna, the Fading Light", R.drawable.bruna)
        commanderRes.put("Kozilek, the Great Distortion", R.drawable.kozilek)
        commanderRes.put("Hakbal of the Surging Soul", R.drawable.hakbal)
        commanderRes.put("The Ur-Dragon", R.drawable.urdragon)
        commanderRes.put("Omo, Queen of Vesuva", R.drawable.omo)
        commanderRes.put("Sliver Gravemother", R.drawable.sliver)
        commanderRes.put("Kuja, Genom Sorcerer", R.drawable.kuja)
        commanderRes.put("Sheoldred, the Apocalypse", R.drawable.sheoldred)
        commanderRes.put("Atraxa, Praetors' Voice", R.drawable.atraxa)
        commanderRes.put("Edgar Markov", R.drawable.edgar)
        commanderRes.put("Yuriko, the Tiger's Shadow", R.drawable.yuriko)
        commanderRes.put("Sauron, the Dark Lord", R.drawable.sauron)
        commanderRes.put("Mr. House, President and CEO", R.drawable.mrhouse)
        commanderRes.put("Hazel of the Rootbloom", R.drawable.hazel)
        commanderRes.put("Admiral Brass, Unsinkable", R.drawable.brass)
        commanderRes.put("Mirko, Obsessive Theorist", R.drawable.mirko)
        commanderRes.put("Ghyrson Starn, Kelermorph", R.drawable.ghyrson)
        commanderRes.put("Frodo and Sam", R.drawable.frodo)
        commanderRes.put("Dogmeat, Ever Loyal", R.drawable.dogmeat)
        commanderRes.put("Aragorn, the Woke", R.drawable.aragorn)
        commanderRes.put("Ulalek, Fused Atrocity", R.drawable.ulelek)
        commanderRes.put("Szarekh, the Silent King", R.drawable.szarekh)
        commanderRes.put("Inquisitor Greyfax", R.drawable.greyfax)
        commanderRes.put("Abaddon the Despoiler", R.drawable.abaddon)
        commanderRes.put("The Swarmlord", R.drawable.swarmlord)
        commanderRes.put("Vorinclex, Voice of Hunger", R.drawable.vorinclex)
        commanderRes.put("Krenko, Mob Boss", R.drawable.krenko)
        commanderRes.put("Wilhelt, the Rotcleaver", R.drawable.wilhelt)
    }

    @DrawableRes
    fun getCommanderRes(commander: String): Int {
        return commanderRes[commander] ?: 0
    }
}