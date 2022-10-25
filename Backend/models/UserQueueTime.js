const mongoose=require('mongoose');
const schema=mongoose.schema;

/**
 * Use Time MONGODB Model
 */
const QueueScheme = new mongoose.Schema({
    phone:{
        type:String,
        required:true
    },
    arrival:{
        type:String,
        required:true
    },
    departure:{
        type:String,
        required:true
    },
    fuel:{
        type:String,
        required:true
    }
});
const Queue = mongoose.model("UserQueueTime",QueueScheme);
module.exports=Queue;
