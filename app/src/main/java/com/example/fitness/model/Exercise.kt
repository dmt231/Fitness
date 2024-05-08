package com.example.fitness.model

class Exercise (
    id: String?,
    description: String?,
    equipment: String?,
    instruction: String?,
    name: String?,
    part: String?,
    type: String?,
    imgCovered: String?,
    srcVideo: String?
) : java.io.Serializable {
    private var id: String? = null
    private var description: String? = null
    private var equipment: String? = null
    private var instruction: String? = null
    private var name: String? = null
    private var part: String? = null
    private var type: String? = null
    private var imgCovered: String? = null
    private var srcVideo: String? = null
    private var isSelected : Boolean = false

    init {
        this.id = id
        this.description = description
        this.equipment = equipment
        this.instruction = instruction
        this.name = name
        this.part = part
        this.type = type
        this.imgCovered = imgCovered
        this.srcVideo = srcVideo
    }
     fun getDescription() : String?{
        return description;
    }
     fun getId() : String?{
        return id;
    }
     fun getEquipment() : String?{
        return equipment;
    }
     fun getInstruction() : String?{
        return instruction;
    }
    fun getName() : String?{
        return name;
    }
     fun getPart() : String?{
        return part;
    }
     fun getType() : String?{
        return type;
    }
     fun getImage() : String?{
        return imgCovered;
    }
     fun getSrcVideo() : String?{
        return srcVideo;
    }
    fun setChecked(status : Boolean){
        this.isSelected = status
    }
    fun getStatusChecked(): Boolean{
        return isSelected
    }
}