const mongoose=require("mongoose");

//MongoDB URL 
const MONGODB_URL='ADD YOUR MONGODB URL(REMOVE FOR SECURITY PURPOSE)';

//MongoDB Server
const connectDB =async () =>{
    await mongoose.connect(MONGODB_URL,{
        useCreateIndex:true,
        useUnifiedTopology:true,
        useNewUrlParser:true,
        useFindAndModify:false
    });
    console.log('Database Synchronized!!');
}
module.exports=connectDB;

