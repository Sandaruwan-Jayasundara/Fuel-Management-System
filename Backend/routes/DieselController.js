const  express=require("express");
var router = express.Router();
let Diesel=require("../models/Diesel");

/**
 * Insert diesel details to the database :: MONGODB
 * @param liters Diesel liters
 * @param arrival Diesel Arrival time
 * @param departure Diesel Departure time
 * @param queue Diesel Queue size
 * @param StationNumber Shed Key
 * @param StationName Shed Name
 * @param StationLocation Shed Location
 */
router.route("/").post((req,res)=>{
    console.log("LOG::DIESEL:: ADD START TO WORK");

    //Get data from the frontend
    var liters =req.body.liters;
    var arrival =req.body.arrival;
    var departure =req.body.departure;
    var queue =req.body.queue;
    var StationNumber =req.body.StationNumber;
    var StationName =req.body.StationName;
    var StationLocation =req.body.StationLocation;

    //Check Departure values for updating purpose
    if(departure == null){
        departure="N/A"
    }

    //assign values for database
    const dieselAdd= new Diesel({ 
        StationNumber,
        StationName,
        StationLocation,
        liters,
        arrival,
        departure,
        queue
    });

    //Save data to the database
    dieselAdd.save().then(()=>{
      console.log("LOG::DIESEL:: SUCCESS");
        res.json({
            code:200 ,
            body: "Success" 
        })
    }).catch((err) =>{
      console.log("LOG::DIESEL:: FAIL"+err);
        res.json({
            code:400 ,
            body: "Fail" 
        })
    });
});

/**
 * Update diesel details to the database :: MONGODB
 * @param liters Diesel liters
 * @param arrival Diesel Arrival time
 * @param departure Diesel Departure time
 * @param queue Diesel Queue size
 * @param StationNumber Shed Key
 * @param StationName Shed Name
 * @param StationLocation Shed Location
 */
router.route("/update").post((req,res)=>{

    console.log("LOG::DIESEL:: UPDATE START TO WORK");

   //Get data from the frontend
    var liters =req.body.liters;
    var arrival =req.body.arrival;
    var departure =req.body.departure;
    var queue =req.body.queue;
    var StationNumber =req.body.StationNumber;
    var StationName =req.body.StationName;
    var StationLocation =req.body.StationLocation;

   //Find the Station key
    Diesel.find({StationNumber: StationNumber})
      .then((data) => {
        console.log("LOG::DIESEL:: KEY FIND SUCCESS");
     const _id = data[0]._id;

     //assign values for database
     const deselData = {
        liters,
        arrival,
        departure,
        queue,
        StationNumber,
        StationName,
        StationLocation
      };
    
      //Update Database details
      const update = Diesel
        .findByIdAndUpdate(_id, deselData)
        .then(() => {
          console.log("LOG::DIESEL:: UPDATE DATA SUCCESS");
            res.json({
                code:200 ,
                body: "Success" 
            })
        })
        .catch((err) => {
          console.log("LOG::DIESEL:: FAIL"+err);
          res.json({
            code:400 ,
            body: "Fail" 
        })
        });
    });
})

/**
 * Remove diesel details from the database :: MONGODB
 * @param StationNumber Shed Key
 */
router.route("/remove").post((req,res)=>{

   console.log("LOG::DIESEL:: REMOVE START TO WORK");

    //Get data from the frontend
    var StationNumber =req.body.StationNumber;

   //Find the Station key
    Diesel.find({StationNumber: StationNumber})
      .then((data) => {
        console.log("LOG::DIESEL:: FIND KEY SUCCESS");
        const _id = data[0]._id;

  //find and delete the database record
  Diesel
    .findByIdAndDelete(_id)
    .then(() => {
      console.log("LOG::DIESEL:: FIND AND DELETE SUCCESS");
      res.json({
        code:200 ,
        body: "Success" 
    })

    }).catch((err) => {
      console.log("LOG::DIESEL:: FAIL"+err);
      res.json({
        code:400 ,
        body: "Fail" 

        })
      })
   })
})

/**
 * Remove diesel details from the database :: MONGODB
 * @param StationNumber Shed Key
 */
router.route("/display").post((req,res)=>{

    console.log("LOG::DIESEL:: DISPLAY START TO WORK");

    //Get data from the frontend
    var StationNumber =req.body.StationNumber;

    //Find the Diesel data
    Diesel.find({StationNumber: StationNumber})
      .then((data) => {
        console.log("LOG::DIESEL:: FIND STATION DETAILS SUCCESS");
        res.json({
            liters:data[0].liters,
            arrival:data[0].arrival,
            departure:data[0].departure,
            queue:data[0].queue,
            StationNumber:data[0].StationNumber,
            StationName:data[0].StationName,
            StationLocation:data[0].StationLocation
        })
      }).catch((err) => {
        console.log("LOG::DIESEL::FAIL"+err);
        res.json({
            code:200 ,
            body: "Fail" 
        })
      });
  });

module.exports=router;