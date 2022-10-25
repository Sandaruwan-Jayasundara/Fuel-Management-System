const express=require("express");
const bodyParser=require("body-parser");
const cors=require("cors");
const connectDB =require('./connection');
const app=express();
require("dotenv").config();

//Server port number
const PORT = process.env.PORT || 8070;

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(cors());

//Connect database
connectDB();

//Create main APi for the system
const API=require('./API/api');
app.use('/api',API);

//Server Status
app.listen(PORT,() =>{
    console.log('Service Synchronized');
});