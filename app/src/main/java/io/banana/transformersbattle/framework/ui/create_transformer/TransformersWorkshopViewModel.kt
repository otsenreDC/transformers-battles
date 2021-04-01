package io.banana.transformersbattle.framework.ui.create_transformer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.banana.transformersbattle.domain.flaws.Flaw
import io.banana.transformersbattle.domain.models.*
import io.banana.transformersbattle.domain.use_cases.CreateTransformersUseCase
import io.banana.transformersbattle.domain.use_cases.EditTransformersUseCase
import io.banana.transformersbattle.domain.view_models.ICreateTransformerViewModel
import kotlinx.coroutines.launch

class TransformersWorkshopViewModel(
    createUseCase: CreateTransformersUseCase,
    editUseCase: EditTransformersUseCase
) : ViewModel(), ICreateTransformerViewModel {

    override val createTransformersUseCase: CreateTransformersUseCase = createUseCase
    override val editTransformersUseCase: EditTransformersUseCase = editUseCase

    var id: String? = null
    var team: Teams? = null
    var name: String = ""
    var strength: Int = 0
    var intelligence: Int = 0
    var speed: Int = 0
    var endurance: Int = 0
    var rank: Int = 0
    var courage: Int = 0
    var firepower: Int = 0
    var skill: Int = 0

    val onTransformerCreated: MutableLiveData<Transformer> = MutableLiveData()
    val onLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val onErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onLoading(isLoading: Boolean) {
        onLoadingLiveData.postValue(isLoading)
    }

    override fun onDone(data: Transformer) {
        onTransformerCreated.postValue(data)
    }

    override fun onError(flaw: Flaw?) {
        onErrorLiveData.postValue("An error occurs while creating the Transformer.")
    }

    fun startForEdition(transformer: Transformer) {
        id = transformer.id
        team = transformer.team
        name = transformer.name
        strength = transformer.strength
        intelligence = transformer.intelligence
        speed = transformer.speed
        endurance = transformer.endurance
        rank = transformer.rank
        courage = transformer.courage
        firepower = transformer.firepower
        skill = transformer.skill
    }

    fun save() {
        viewModelScope.launch {
            if (validateData()) {
                when (val result = callUseCase()) {
                    is Success -> {
                        onTransformerCreated.postValue(result.data)
                    }
                    is Failure -> {
                        onErrorLiveData.postValue("Error creating transformer.")
                    }
                }
            } else {
                onErrorLiveData.postValue("All fields are required.")
            }
        }
    }

    fun autobotSelected() {
        this.team = Teams.Autobots
    }

    fun decepticonSelected() {
        this.team = Teams.Decepticons
    }

    private suspend fun validateData(): Boolean =
        isValidaData(
            team = team,
            name = name,
            strength = strength,
            intelligence = intelligence,
            speed = speed,
            endurance = endurance,
            rank = rank,
            courage = courage,
            firepower = firepower,
            skill = skill
        )

    private suspend fun callUseCase(): Result<Transformer> {
        val id = this.id
        return if (id == null) {
            createTransformersUseCase.execute(
                team = team!!.designator,
                name = name,
                strength = strength,
                intelligence = intelligence,
                speed = speed,
                endurance = endurance,
                rank = rank,
                courage = courage,
                firepower = firepower,
                skill = skill
            )
        } else {
            editTransformersUseCase.execute(
                id = id,
                team = team!!.designator,
                name = name,
                strength = strength,
                intelligence = intelligence,
                speed = speed,
                endurance = endurance,
                rank = rank,
                courage = courage,
                firepower = firepower,
                skill = skill
            )
        }
    }
}