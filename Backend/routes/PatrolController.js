const  express=require("express");
var router = express.Router();
let Patrol=require("../models/Patrol");

/**
 * Insert Patrol details to the database :: MONGODB
 * @param liters Patrol liters
 * @param arrival Patrol Arrival time
 * @param departure Patrol Departure time
 * @param queue Patrol Queue size
 * @param StationNumber Shed Key
 * @param StationName Shed Name
 * @param StationLocation Shed Location
 */
router.route("/").post((req,res)=>{

    console.log("LOG::PATROL:: ADD START TO WORK");

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
    const patrolAdd= new Patrol({ 
        StationNumber,
        StationName,
        StationLocation,
        liters,
        arrival,
        departure,
        queue
    });

    //Save data to the database
    patrolAdd.save().then(()=>{
      console.log("LOG::PATROL:: SUCCESS");
        res.json({
            code:200 ,
            body: "Success" 
        })
    }).catch((err) =>{
       console.log("LOG::PATROL:: FAIL"+err);
        res.json({
            code:400 ,
            body: "Fail" 
        })
    });
});

/**
 * Update Patrol details to the database :: MONGODB
 * @param liters Patrol liters
 * @param arrival Patrol Arrival time
 * @param departure Patrol Departure time
 * @param queue Patrol Queue size
 * @param StationNumber Shed Key
 * @param StationName Shed Name
 * @param StationLocation Shed Location
 */
router.route("/update").post((req,res)=>{

    console.log("LOG::PATROL:: UPDATE START TO WORK");

    //Get data from the frontend
    var liters =req.body.liters;
    var arrival =req.body.arrival;
    var departure =req.body.departure;
    var queue =req.body.queue;
    var StationNumber =req.body.StationNumber;
    var StationName =req.body.StationName;
    var StationLocation =req.body.StationLocation;

    //Find the Station key
    Patrol.find({StationNumber: StationNumber})
      .then((data) => {
     const _id = data[0]._id;

      //assign values for database
     const patrolData = {
        liters,
        arrival,
        departure,
        queue,
        StationNumber,
        StationName,
        StationLocation
      };

     //Update Database details
      const update = Patrol
        .findByIdAndUpdate(_id, patrolData)
        .then(() => {
            res.json({
                code:200 ,
                body: "Success" 
            })
        })
        .catch((err) => {
          console.log(err);
          res.json({
            code:400 ,
            body: "Fail" 
        })
        });
    });
})

/**
 * Remove Patrol details from the database :: MONGODB
 * @param StationNumber Shed Key
 */
router.route("/display").post((req,res)=>{

    console.log("LOG::PATROL:: DISPLAY START TO WORK");

    //Get data from the frontend
    var StationNumber =req.body.StationNumber;
    
    Patrol.find({StationNumber: StationNumber})
      .then((data) => {
        console.log("LOG::PATROL:: FIND STATION DETAILS SUCCESS");
        if(data){
            res.json({
                liters:data[0].liters,
                arrival:data[0].arrival,
                departure:data[0].departure,
                queue:data[0].queue,
                StationNumber:data[0].StationNumber,
                StationName:data[0].StationName,
                StationLocation:data[0].StationLocation
            })
        }else{
            res.json({
                code:200 ,
                body: "Success" 
            })
        }
      })
      .catch((err) => {
        console.log("LOG::DIESEL::FAIL"+err);
        res.json({
            code:200 ,
            body: "Fail" 
        })
      });
  });

/**
 * Remove Patrol details from the database :: MONGODB
 * @param StationNumber Shed Key
 */
  router.route("/remove").post((req,res)=>{

    console.log("LOG::PATROL:: REMOVE START TO WORK");

    //Get data from the frontend
    var StationNumber =req.body.StationNumber;

    //Find the Station key
    Patrol.find({StationNumber: StationNumber})
      .then((data) => {
        const _id = data[0]._id;

   Patrol
    .findByIdAndDelete(_id)
    .then(() => {
      console.log('LOG::PATROL:: SUCCESS')
      res.json({
        code:200 ,
        body: "Success" 
    })

    }).catch((err) => {
      console.log('LOG::PATROL:: FAIL'+err)
      res.json({
        code:400 ,
        body: "Fail" 

        })
      })
   })
})
module.exports=router;