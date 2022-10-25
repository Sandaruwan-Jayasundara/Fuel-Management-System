const mongoose=require('mongoose');
const schema=mongoose.schema;

/**
 * Shed Time MONGODB Model
 */
const TimeSchema = new mongoose.Schema({
    arrival:{
        type:String,
        required:false
    },
    departure:{
        type:String,
        required:false
    },
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
    }
});
const Time = mongoose.model("OpenTime",TimeSchema);
module.exports=Time;
