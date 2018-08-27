'use strict'

const { check, validationResult } = require('express-validator/check')
const Sequelize = require('sequelize')

/**
 * 
 * @param {express} express instance
 * @param {sequelize} sequelize instance
 * @returns {express.Router} an instance of express.Router
 */
module.exports = function(express, db){
    var router = express.Router()
    const {Vehicle} = db.entities

    /* Vehicles Endpoints */
    
    router.get('/vehicles/:id', async (req, res) => {
        let vehicle = await Vehicle.findById(req.params.id)
        res.json(vehicle)
    })

    router.get('/vehicles', async (req, res) => {
        try{
            let vehicles = await Vehicle.findAll()
            res.json(vehicles)
        }catch(err){
            res.status(400).json({ errors: err.original.message})
        }
    })

    router.post('/vehicles', [
        check('plate').exists().isString(),
        check('category').exists().isInt(),
        check('nif').exists().isInt()
    ], async (req, res) => {

        const errors = validationResult(req)
        if(! errors.isEmpty()) return res.status(422).json({ errors: errors.array() })
        
        try{
            const newV = {
                plate: req.body.plate,
                category: req.body.category
            }
            const vehicle = await Vehicle.create(newV)
            vehicle.setFK_nif(req.body.nif)

            res.status(201).location('/vehicles/'+ vehicle.id).json(vehicle)
        }catch(err){
            res.status(400).json({ errors: err.original.message})
        }
    })

    return router
}