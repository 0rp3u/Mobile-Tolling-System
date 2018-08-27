'use strict'

const Sequelize = require('sequelize')
const { check, validationResult } = require('express-validator/check')

/**
 * 
 * @param {express} express instance
 * @param {sequelize} sequelize instance
 * @returns {express.Router} an instance of express.Router
 */
module.exports = function(express, db){
    var router = express.Router()
    const {Toll} = db.entities

    /* User Endpoints */

    router.get('/tolls/:id', async (req, res) => {
        try{
            let toll = await Toll.findById(req.params.id)
            res.json(toll)
        }catch(err){
            res.status(400).json({ errors: err.message})
        }
        
    })

    router.get('/tolls', async (req, res) => {
        try{
            let tolls = await Toll.findAll()
            res.json(tolls)
        }catch(err){
            res.status(400).json({ errors: err.message})
        }
    })

    router.post('/tolls', [
        check('name').optional().isString(),
        check('toll_type').exists().isString(),
        check('geolocation_latitude').exists().isFloat(),
        check('geolocation_longitude').exists().isFloat(),
        check('azimuth').optional().isFloat(),
        check('region').optional().isString(),
        check('road').optional().isString()
    ], async (req, res) => {
        /* Validation */
        const errors = validationResult(req)
        if(! errors.isEmpty()) return res.status(422).json({ errors: errors.array() })

        /* Processing */
        try{
            const newObj = {
                name:                   req.body.name,
                toll_type:              req.body.toll_type,
                geolocation_latitude:   req.body.geolocation_latitude,
                geolocation_longitude:  req.body.geolocation_longitude,
                azimuth:                req.body.azimuth,
                region:                 req.body.region,
                road:                   req.body.road
            }
            const toll = await Toll.create(newObj)
            res.status(201).json(toll)
        }catch(err){
            try{await t.rollback()}catch(err1){
                res.status(400).json({ errors: err.message})
            }
        }
    })

    return router
}