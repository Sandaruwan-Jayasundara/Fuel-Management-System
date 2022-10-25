const mongoose=require('mongoose');
const schema=mongoose.schema;

/**
 * Patrol MONGODB Model
 */
const patrolSchema = new mongoose.Schema({
    StationNumber:{
        type:String,
        required:true
    },
    StationName:{
        type:String,
        required:true
    },
    StationLocation:{
        type:String,
        required:true
    },
    liters:{
        type:String,
        required:true
    },
    arrival:{
        type:String,
        required:true
    },
    departure:{
        type:String,
        required:false
    },
    queue:{
        type:String,
        required:true
    },
});
const Patrol = mongoose.model("Patrol",patrolSchema);
module.exports=Patrol;
