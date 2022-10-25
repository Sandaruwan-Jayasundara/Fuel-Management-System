const mongoose=require('mongoose');
const schema=mongoose.schema;

/**
 * Diesel MONGODB Model
 */
const deselSchema = new mongoose.Schema({
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
const Desel = mongoose.model("Desel",deselSchema);
module.exports=Desel;
