const mongoose=require('mongoose');
const schema=mongoose.schema;

/**
 * Queue MONGODB Model
 */
const QueueScheme = new mongoose.Schema({
    type:{
        type:String,
        required:true
    },
    queue:{
        type:Number,
        required:true
    },
    shed:{
        type:String,
        required:true
    }
});
const Queue = mongoose.model("Queue",QueueScheme);
module.exports=Queue;
