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
    const {Transaction, Vehicle, Trip, Toll} = db.entities


    /* Transaction Endpoints */

    /* Get a Transaction */
    router.get('/transactions/:id', async (req, res) => {
        const id = req.params.id
        if(!id || !Number.isInteger(id)) return res.status(422).json({errors: 'id in parameters is invalid.'})
        try{
            let transaction = await Transaction.findById()
            res.json(transaction)
        }catch(err){
            res.status(400).end()
        }
    })

    /* Get a collection of Transactions */
    router.get('/transactions', async (req, res) => {
        try{
            let transactions = await Transaction.findAll()
            res.json(transactions)
        }catch(err){
            res.status(400).end()
        }
    })

    /* Create a new Transaction */
    router.post('/transactions', [
        check('vehicle_plate').exists().isString(),
        check('toll_id').exists().isNumeric(),
        check('direction').optional().isString(),
        check('timestamp').exists().isString()
    ], async (req, res) => {

        /* Validation */
        const errors = validationResult(req)
        if(! errors.isEmpty()) return res.status(422).json({ errors: errors.array() })

        /* Processing */
        try{

            const body = req.body
            const t = await db.transaction({
                isolationLevel: Sequelize.Transaction.ISOLATION_LEVELS.SERIALIZABLE
            })

            const vehicle = await Vehicle.findOne({ where: { plate: body.vehicle_plate} })
            if(!vehicle) throw Error('A vehicle with that plate does not exist.')

            const test_trips = await Trip.findAll()
            const trip = await Trip.create({
                state: 'STARTED'
            }, { transaction: t})
            trip.setFK_vehicle(vehicle.id)

            const toll = await Toll.findById(body.toll_id)
            if(!toll) throw Error('The Toll Booth you are trying to reference does not exist.')

            const obj = {
                toll_id: toll.id,
                trip_id: trip.id,
                timestamp: body.timestamp,
                direction: body.direction
            }

            const transaction = await Transaction.create(obj, {
                transaction: t,
                include: [
                    { association: Transaction.Toll },
                    { association: Transaction.Trip }
                ]
            })
    
            await t.commit()
            
            res.status(201).json(transaction)
        }catch(err){
            try{await t.rollback()}catch(err1){
                res.status(400).json({ errors: err.message})
            }
        }
    })

    /* Close a Trip */
    router.post('/transactions/:id/close', [
        check('vehicle_plate').isString(),
        check('toll_id').isNumeric(),
        check('direction').optional().isString(),
        check('timestamp').isString()
    ], async (req, res) => {

        try{
            let transaction = await Transaction.findAll()
            res.json(tolls)
        }catch(err){
            res.status(400).end()
        }
    })

    return router
}