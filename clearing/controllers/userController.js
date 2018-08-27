'use strict'

const Sequelize = require('sequelize')
const { check, validationResult } = require('express-validator/check')

/**
 * 
 * @param {*} express instance
 * @param {*} sequelize instance
 * @returns an instance of express.Router
 */
module.exports = function(express, db){
    var router = express.Router()
    const {User} = db.entities

    /* User Endpoints */

    router.get('/users/:id', async (req, res) => {
        try{
            let user = await User.findById(req.params.id)
            const output = User.outputModel(user)
            res.json(output)
        }catch(err){
            res.status(400).json({ errors: err.original.message})
        }
    })

    router.get('/users', async (req, res) => {
        try{
            let users = await User.findAll()
            res.json(users)
        }catch(err){
            res.status(400).json({ errors: err.original.message})
        }
    })

/*     router.get('/users/byname/:name', async (req, res) => {
        try{
            let user = await user.find({ where: req.params.name})
            res.json(user)
        }catch(err){
            res.status(400).json({ errors: err.original.message})
        }
    }) */

    router.post('/users', [
        check('nif').exists().isNumeric(),
        check('password').exists().isString(),
        check('name').optional().isString(),
        check('contact').optional().isNumeric()
    ], async (req, res) => {
        const errors = validationResult(req)
        if(! errors.isEmpty()) return res.status(422).json({ errors: errors.array() })
        try{
            const user = await User.create(req.body)
            res.status(201).location('/users/'+ user.id).json(user)
        }catch(err){
            res.status(400).json({ errors: err.original.message})
        }
    })

    return router
}