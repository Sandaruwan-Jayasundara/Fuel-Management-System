const express=require('express');
const router=express.Router();

// Create Patrol API
const patrol=require('../routes/PatrolController');
router.use("/patrol",patrol);

// Create Diesel API
const diesel=require('../routes/DieselController');
router.use("/diesel",diesel);

// Create Time API
const Time=require('../routes/AvailableTime');
router.use("/time",Time);

// Create Queue API
const Queue=require('../routes/Queue');
router.use("/queue",Queue);

// Create Queue Time API
const User=require('../routes/UserQueue');
router.use("/user",User);

module.exports = router;