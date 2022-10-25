const  express=require("express");
var router = express.Router();
let UserQueueTime=require("../models/UserQueueTime");

 /**
 * Insert user queue entered time to the database :: MONGODB
 * @param phone Shed Key
 * @param fuel Fuel Type
 */
router.route("/").post((req,res)=>{

  console.log("LOG:: QUEUE TIME :: ADD START TO WORK");

  //Get data from the frontend
  var phone =req.body.StationNumber;
  var fuel =req.body.Fuel;
  var departure = "N/A";

  //Get Current data and time
  let date = new Date();
  let hour = date.getHours();
  let minute = date.getMinutes();

  var arrival =  hour +":"+minute;
 
  //Assign values to database
  const UserQueueTimeAdd= new UserQueueTime({ 
        phone,
        arrival,
        departure,
        fuel
  });

  //Insert data to the database
  UserQueueTimeAdd.save().then(()=>{
    console.log("LOG::QUEUE TIME :: SUCCESS");
      res.json({
          code:200 ,
          body: "Success" 
      })
  }).catch((err) =>{
    console.log("LOG::QUEUE TIME :: FAIL"+err);
      res.json({
          code:400 ,
          body: "Fail" 
      })
  });
});

 /**
 * Display user queue time :: MONGODB
 * @param phone Shed Key
 */
router.route("/display").post((req,res)=>{

    console.log("LOG:: QUEUE TIME :: DISPLAY START TO WORK");

    //Get data from the frontend
    var phone =req.body.StationNumber;

    //Find Shed key
    UserQueueTime.find({phone: phone})
      .then((data) => {
        console.log("LOG::QUEUE TIME :: SUCCESS");
        if(data){
            res.json({
              phone:data[0].phone,
              arrival:data[0].arrival,
              departure:data[0].departure,
              fuel:data[0].fuel
            })
        }else{
          console.log("LOG::QUEUE TIME :: FAIL");
            res.json({
                code:400 ,
                body: "Success" 
            })
        }
      })
      .catch((err) => {
        console.log("LOG::QUEUE TIME :: FAIL"+err);
        res.json({
            code:400 ,
            body: "Fail" 
        })
      });
  });

 /**
 * Update user queue time :: MONGODB
 * @param phone Shed Key
 * @param Status button Status
 */
  router.route("/update").post((req,res)=>{

    console.log("LOG:: QUEUE TIME :: UPDATE START TO WORK");

    //Get data from the frontend
    var phone =req.body.StationNumber;
    var Status =req.body.Status;

    // Complete fuel process
    if(Status == "Complete"){
      
      //Add departure time and date
      let date = new Date();
      let hour = date.getHours();
      let minute = date.getMinutes();
      var departure =  hour +":"+minute;
      
      //Find Shed key from the database
      UserQueueTime.find({phone: phone})
        .then((data) => {
          console.log('LOG::QUEUE TIME:: FIND KEY SUCCESS');
          const _id = data[0]._id;
        
        //Update Queue time to the database
        const update = UserQueueTime
          .findByIdAndUpdate(_id, {
            departure:departure
          })
          .then(() => {
              console.log('LOG::QUEUE TIME::  FIND AND UPDATE SUCCESS');
              res.json({
                  code:200 ,
                  body: "Success" 
              })
          })
          .catch((err) => {
            console.log('LOG::QUEUE TIME:: FAIL'+err);
            res.json({
              code:400 ,
              body: "Fail" 
          })
          });
      });
    }else{
      console.log("LOG:: QUEUE TIME :: EXIT(REMOVE) WORK");

      //Find Shed Queue time from that database
      UserQueueTime.find({phone: phone})
        .then((data) => {
          const _id = data[0]._id;
    //find and delete the queue time details when the user exit from the queue
    UserQueueTime
      .findByIdAndDelete(_id)
      .then(() => {
        console.log('LOG::QUEUE TIME::  FIND AND DELETE SUCCESS');
        res.json({
          code:200 ,
          body: "Success" 
      })
      })
      .catch((err) => {
        console.log('LOG::QUEUE TIME::  FIND AND DELETE FAIL'+err);
        res.json({
          code:200 ,
          body: "Fail" 
          })
        })

     }).catch((err) => {
      console.log('LOG::QUEUE TIME:: FAIL'+err);
       res.json({
        code:200 ,
        body: "Fail" 
        })
      })
    }
})
module.exports=router;