const  express=require("express");
var router = express.Router();
let Time=require("../models/Time");


/**
 * Add shed opening and closing time to the MONGODB
 * @param arrival Opening Time
 * @param departure Close Time
 * @param StationNumber Shed Key
 * @param StationName Shed Name
 * @param StationLocation Shed Location
 */
router.route("/").post((req,res)=>{
  
    console.log("LOG::TIME:: ADD START TO WORK");

    //get data from the frontend
    var arrival =req.body.arrival;
    var departure =req.body.departure;
    var StationNumber =req.body.StationNumber;
    var StationName =req.body.StationName;
    var StationLocation =req.body.StationLocation;

    //assign values for database
    const TimeAdd= new Time({ 
        arrival,
        departure,
        StationNumber,
        StationName,
        StationLocation
    });

    //save data to the database
    TimeAdd.save().then(()=>{
      console.log("LOG::TIME:: SUCCESS");
        res.json({
            code:200 ,
            body: "Success" 
        })
    }).catch((err) =>{
       console.log("LOG::TIME:: FAIL"+err);
        res.json({
            code:400 ,
            body: "Fail" 
        })
    });
});


/**
 * Get time data from the MONGODB database
 * @param StationNumber Shed Name
 */
router.route("/display").post((req,res)=>{

   console.log("LOG::TIME:: DISPLAY START TO WORK");
   
    //get values from the frontend
    var StationNumber =req.body.StationNumber;
  
    //find the data in the database using the shed key
    Time.find({StationNumber: StationNumber})
      .then((data) => {
        console.log("LOG::TIME:: SUCCESS");
        res.json({
            Arrival:data[0].arrival,
            departure:data[0].departure,
            StationNumber:data[0].StationNumber,
            StationName:data[0].StationName,
            StationLocation:data[0].StationLocation
        })
      })
      .catch((err) => {
        console.log("LOG::TIME:: FAIL" + err);
        console.log(err);
      });
  });

/**
 * Update MONGODB time data
 * @param arrival Shed Start Time
 * @param departure Shed Close Time
 * @param StationNumber Shed Key
 * @param StationName Shed Name
 * @param StationLocation Shed Location
 */
  router.route("/update").post((req,res)=>{

    console.log("LOG::TIME:: UPDATE START TO WORK");

    //Assign data to variables
    var arrival =req.body.arrival;
    var departure =req.body.departure;
    var StationNumber =req.body.StationNumber;
    var StationName =req.body.StationName;
    var StationLocation =req.body.StationLocation;

    //Find the data using the shed key
    Time.find({StationNumber: StationNumber})
      .then((data) => {
        console.log("LOG::TIME:: FIND KEY START TO WORK");
        const _id = data[0]._id;

     //Assign values for updating
     const timeData = {
        arrival,
        departure,
        StationNumber,
        StationName,
        StationLocation
      };

      //Update the data
      const update = Time
        .findByIdAndUpdate(_id, timeData)
        .then(() => {
          console.log("LOG::TIME::FIND AND UPDATE SUCCESS");
            res.json({
                code:200 ,
                body: "Success" 
            })
        }).catch((err) => {
          console.log("LOG::TIME::FIND AND UPDATE FAIL"+err);
          res.json({
            code:400 ,
            body: "Fail" 
        })
      });
    });
})

/**
 * Remove data from the mongoDB
 * @param StationNumber Shed Name
 */
  router.route("/remove").post((req,res)=>{

    console.log("LOG::TIME:: REMOVE START TO WORK");
    var StationNumber =req.body.StationNumber;

    //Find the data using te shed key
    Time.find({StationNumber: StationNumber})
      .then((data) => {
        console.log("LOG::TIME:: FIND KEY SUCCESS");
        const _id = data[0]._id;
    
    //Delete the shed data from the MONGODB
    Time
    .findByIdAndDelete(_id)
    .then(() => {
      console.log("LOG::TIME:: FIND AND REMOVE SUCCESS");
      res.json({
        code:200 ,
        body: "Success" 
    })
 }).catch((err) => {
     console.log("LOG::TIME:: FIND AND REMOVE FAIL"+err);
      res.json({
        code:400 ,
        body: "Fail" 
        })
      })
   })
})
module.exports=router;